package com.example.benevent.Activity;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benevent.API.NetworkClient;
import com.example.benevent.API.UserApi;
import com.example.benevent.Models.Login;
import com.example.benevent.Models.User;
import com.example.benevent.R;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class SigninActivity extends AppCompatActivity {

    Context context = this ;
    public TextView signupTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        signupTV = findViewById(R.id.no_account_button);
        signupTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SigninActivity.this, SignupActivity.class);
                startActivity(intent);
                //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        SharedPreferences pref = this.getSharedPreferences("login", MODE_PRIVATE);
        String password = pref.getString("password", "");
        String email = pref.getString("email", "");
        if(!password.equals("")||!email.equals("")){
            Login autologin = new Login(email,password);
            loginUser(autologin);
        }
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Attention");
        builder.setMessage("Voulez-vous quitter l'application ?");

        builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
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


    public void Signin(View view){
        EditText log = (EditText) findViewById(R.id.log_ed);
        EditText password = (EditText) findViewById(R.id.pass_ed);

        loginUser(new Login(log.getText().toString(),md5(password.getText().toString())));

    }

    private void loginUser(Login login){
        Retrofit retrofit = NetworkClient.getRetrofitClient();

        UserApi userApi = retrofit.create(UserApi.class);

        Call call = userApi.logUser(login);
        Log.d("TAG", "loginUser: "+login.getPassword());
        /*
        This is the line which actually sends a network request. Calling enqueue() executes a call asynchronously. It has two callback listeners which will invoked on the main thread
        */
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                if (response.code() == 200) {
                    List<User> users = response.body();

                    SharedPreferences pref = getApplicationContext().getSharedPreferences("login", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();

                    editor.putInt("userid", users.get(0).getId());
                    editor.putString("username", users.get(0).getFirstname() + " " + users.get(0).getName());
                    editor.putString("email", users.get(0).getEmail()); // Saving string
                    editor.putString("profilpicture",users.get(0).getProfilpicture());
                    editor.putString("password",login.getPassword());

                    editor.apply();
                    Intent intent = new Intent(context, FragmentManagerActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Verifiez vos identifiants", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("FailSignin", "onFailure : " + t );
                Toast.makeText(getApplicationContext(), "Verifiez votre connexion internet", Toast.LENGTH_LONG).show();
            }
        });
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
