package com.example.benevent.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.example.benevent.API.NetworkClient;
import com.example.benevent.API.UserApi;
import com.example.benevent.Models.Signup;
import com.example.benevent.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignupActivity extends AppCompatActivity {

    public Button chooseImageButton;
    public ImageView userImageIV;
    public Signup signup = new Signup();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        chooseImageButton = findViewById(R.id.upload_button_signup);
        userImageIV = findViewById(R.id.profil_picture_signup);

        chooseImageButton.setOnClickListener(view -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent,200);
        });
    }

    public void signup(View view) {
        EditText nameET = findViewById(R.id.ed_name_signup);
        EditText firstnameET = findViewById(R.id.ed_firstname_signup);
        EditText birthdateET = findViewById(R.id.ed_born_signup);
        EditText emailET = findViewById(R.id.ed_email_signup);
        EditText passwordET = findViewById(R.id.ed_password_signup);
        EditText phoneET = findViewById(R.id.ed_phone_signup);

        String name = nameET.getText().toString();
        String firstname = firstnameET.getText().toString();
        String birthdate = birthdateET.getText().toString();
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        String phone = phoneET.getText().toString();

        SimpleDateFormat dateFormatterFR = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateFormatterSQL = new SimpleDateFormat("yyyy-MM-dd");

        if (name.isEmpty() || firstname.isEmpty() || birthdate.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Faites attention à ne pas envoyer de champs vides", Toast.LENGTH_LONG).show();
        } else {
            if (!email.contains("@")) {
                Toast.makeText(getApplicationContext(), "Verifiez votre adresse mail !", Toast.LENGTH_LONG).show();
            } else if (phone.length() < 10 || (phone.length() > 10 && !phone.contains("+")) || (phone.length() < 12 && phone.contains("+"))) {
                Toast.makeText(getApplicationContext(), "Verifiez votre numero de telephone !", Toast.LENGTH_LONG).show();
            } else {
                try {
                    Date birthDate = dateFormatterFR.parse(birthdate);
                    String birthdateString = dateFormatterSQL.format(birthDate);
                    signup.setName(name);
                    signup.setFirstname(firstname);
                    signup.setBirthdate(birthdateString);
                    signup.setPhone(phone);
                    signup.setEmail(email);
                    signup.setPassword(md5(password));
                    signUser(signup);
                } catch (IllegalStateException | ParseException e) {
                    Toast.makeText(getApplicationContext(), "la date de naissance doit etre sous le format JJ/MM/AAAA", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
        startActivity(intent);
    }

    private void signUser(Signup signup) {
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        UserApi userApi = retrofit.create(UserApi.class);
        Call call = userApi.signUser(signup);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 201) {
                    Toast.makeText(getApplicationContext(), "L'utilisateur a bien été enregistré !", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "L'adresse mail est déjà utilisé !", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            if (requestCode == 200 && resultCode == RESULT_OK && data != null) {
                Uri selectedImage = data.getData();
                Cloudinary cloudinary = new Cloudinary("cloudinary://996546549428271:zTYYc7JGVOE4lpiWuyt5zSt_Ftc@beneventesgi");
                try {
                    FileInputStream inputStream = new FileInputStream(new File(getPath(selectedImage)));
                    Uploader uploader = cloudinary.uploader();
                    Map map = uploader.upload(inputStream, new HashMap());
                    try {
                        URL url = new URL((String) map.get("url"));
                        Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        userImageIV.setImageBitmap(bitmap);
                    } catch (IOException | NetworkOnMainThreadException e) {
                        e.printStackTrace();
                    }
                    signup.setProfilpicture((String) map.get("url"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "Aucune image n'a été séléctionnée", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Une erreur est survenue" + e, Toast.LENGTH_LONG) .show();
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.DATA };
        Cursor cursor = this.getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public String md5(String s) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuffer hexString = new StringBuffer();
            for (byte b : messageDigest) {
                hexString.append(Integer.toHexString(0xFF & b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}