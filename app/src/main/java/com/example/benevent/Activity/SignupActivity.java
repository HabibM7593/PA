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

    public Button loadpicture;
    public ImageView imageUser;
    public Signup signup = new Signup();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        loadpicture = findViewById(R.id.upload_button_signup);

        imageUser= (ImageView)findViewById(R.id.profil_picture_signup);

        loadpicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,200);
            }
        });

    }

    public void Signup(View view) throws ParseException {
        EditText nameET = (EditText) findViewById(R.id.ed_name_signup);
        EditText firstnameET = (EditText) findViewById(R.id.ed_firstname_signup);
        EditText ageET = (EditText) findViewById(R.id.ed_born_signup);
        EditText emailET = (EditText) findViewById(R.id.ed_email_signup);
        EditText passwordET = (EditText) findViewById(R.id.ed_password_signup);
        EditText phoneET = (EditText) findViewById(R.id.ed_phone_signup);

        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");

        if(nameET.getText().toString().equals("")||firstnameET.getText().toString().equals("")||ageET.getText().toString().equals("")||emailET.getText().toString().equals("")||passwordET.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Faites attention à ne pas envoyer de champs vides", Toast.LENGTH_LONG).show();
        }else{
            if(!emailET.getText().toString().contains("@")){
                Toast.makeText(getApplicationContext(), "Verifiez votre adresse mail !", Toast.LENGTH_LONG).show();
            }
            else if(phoneET.getText().toString().length()<10 || (phoneET.getText().toString().length()>10 && !phoneET.getText().toString().contains("+"))||(phoneET.getText().toString().length()<12 && phoneET.getText().toString().contains("+"))){
                Toast.makeText(getApplicationContext(), "Verifiez votre numero de telephone !", Toast.LENGTH_LONG).show();
            }
            else{
                try {
                    Date ageDate = formatter1.parse(ageET.getText().toString());
                    String age = formatter2.format(ageDate);
                    signup.setName(nameET.getText().toString());
                    signup.setFirstname(firstnameET.getText().toString());
                    signup.setAge(age);
                    signup.setPhone(phoneET.getText().toString());
                    signup.setEmail(emailET.getText().toString());
                    signup.setPassword(md5(passwordET.getText().toString()));
                    Log.d("TAG", "Signup: "+signup.getProfilpicture());
                    signUser(signup);
                }catch (IllegalStateException | ParseException e){
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
                Log.e("FailSignup", "onFailure : " + t);
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        try
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            if (requestCode == 200 && resultCode == RESULT_OK && null != data)
            {
                Uri selectedImage = data.getData();
                Cloudinary cloudinary = new Cloudinary("cloudinary://996546549428271:zTYYc7JGVOE4lpiWuyt5zSt_Ftc@beneventesgi");
                try {
                    FileInputStream is = new FileInputStream(new File(getPath(selectedImage)));
                    Uploader uploader = cloudinary.uploader();
                    Map map = uploader.upload(is, new HashMap());

                    try {

                        URL url = new URL((String) map.get("url"));
                        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        imageUser.setImageBitmap(bmp);
                    } catch (IOException | NetworkOnMainThreadException e) {
                        e.printStackTrace();
                    }
                    Log.d("TAG", "onActivityResult: "+(String) map.get("url"));
                    signup.setProfilpicture((String) map.get("url"));

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong"+e, Toast.LENGTH_LONG) .show();
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATA };
        Cursor cursor = this.getContentResolver().query(uri,
                projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));

            return hexString.toString();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


}