package com.example.benevent.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.example.benevent.API.NetworkClient;
import com.example.benevent.API.UserApi;
import com.example.benevent.Activity.SigninActivity;
import com.example.benevent.Models.User;
import com.example.benevent.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class ProfilFragment extends Fragment {
    public TextView nameprofilTV;
    public TextView firstnameprofilTV;
    public ImageView pictureprofilTV;
    public TextView phoneprofilTV;
    Retrofit retrofit = NetworkClient.getRetrofitClient();
    User currentUser = new User();

    public ProfilFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        UserApi userApi = retrofit.create(UserApi.class);
        Button modifButton = view.findViewById(R.id.button_submit);
        Button supprButton = view.findViewById(R.id.button_delete);
        Button uploadimgButton = view.findViewById(R.id.button_upload);
        SharedPreferences pref = Objects.requireNonNull(this.getActivity()).getSharedPreferences("login", MODE_PRIVATE);
        int iduser = pref.getInt("userid", 0);

        uploadimgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,200);
            }
        });

        Call call = userApi.getUser(iduser);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.code()==200){
                    currentUser = response.body().get(0);
                    nameprofilTV = view.findViewById(R.id.profil_name);
                    pictureprofilTV = view.findViewById(R.id.profil_picture);
                    firstnameprofilTV = view.findViewById(R.id.profil_firstname);
                    phoneprofilTV = view.findViewById(R.id.profil_phone);

                    nameprofilTV.setText(currentUser.getName());
                    firstnameprofilTV.setText(currentUser.getFirstname());
                    phoneprofilTV.setText(currentUser.getPhone());
                    try {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                .permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        URL url = new URL(currentUser.getProfilpicture());
                        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        pictureprofilTV.setImageBitmap(bmp);
                    } catch (IOException | NetworkOnMainThreadException ignored) {
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });

        modifButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Call call = userApi.updateUser( iduser,new User(nameprofilTV.getText().toString(),firstnameprofilTV.getText().toString(),phoneprofilTV.getText().toString(),currentUser.getProfilpicture()));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Vos modifications ont bien ete pris en compte", Toast.LENGTH_LONG).show();
                        SharedPreferences pref = getActivity().getSharedPreferences("login", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("username", currentUser.getFirstname() + " " + currentUser.getName());
                        editor.putString("profilpicture",currentUser.getProfilpicture());
                        editor.apply();
                        getActivity().invalidateOptionsMenu();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Erreur", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        supprButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Attention");
                builder.setMessage("Voulez-vous vraiment supprimer votre compte ?");

                builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        Call call = userApi.deleteUser(iduser);
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Votre compte vient d'etre supprimé ", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getActivity().getApplicationContext(), SigninActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
                    }
                });

                builder.setNegativeButton("NON", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        try
        {
            if (requestCode == 200 && resultCode == RESULT_OK && null != data)
            {
                Uri selectedImage = data.getData();
                Cloudinary cloudinary = new Cloudinary("cloudinary://996546549428271:zTYYc7JGVOE4lpiWuyt5zSt_Ftc@beneventesgi");
                try {
                    FileInputStream is = new FileInputStream(new File(getPath(selectedImage)));
                    Uploader uploader = cloudinary.uploader();
                    Map map = uploader.upload(is, new HashMap());

                    try {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                .permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        URL url = new URL((String) map.get("url"));
                        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        pictureprofilTV.setImageBitmap(bmp);
                    } catch (IOException | NetworkOnMainThreadException e) {
                        e.printStackTrace();
                    }
                    currentUser.setProfilpicture((String) map.get("url"));

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "You haven't picked Image",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e)
        {
            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG) .show();
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATA };
        Cursor cursor = Objects.requireNonNull(getActivity()).getContentResolver().query(uri,
                projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
