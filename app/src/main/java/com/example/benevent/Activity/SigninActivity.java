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


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class SigninActivity extends AppCompatActivity {

    Context context = this ;
    public TextView signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        signupButton = findViewById(R.id.no_account_button);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SigninActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences pref = this.getSharedPreferences("login", MODE_PRIVATE);
        String password = pref.getString("password", "");
        String email = pref.getString("email", "");

        if (!password.equals("") || !email.equals("")) {
            Login autoLogin = new Login(email,password);
            loginUser(autoLogin);
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Attention");
        builder.setMessage("Voulez-vous quitter l'application ?");

        builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
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


    public void signIn(View view) {
        EditText LoginEditText = (EditText) findViewById(R.id.log_ed);
        EditText passwordEditText = (EditText) findViewById(R.id.pass_ed);

        loginUser(new Login(LoginEditText.getText().toString(), md5(passwordEditText.getText().toString())));

    }

    private void loginUser(Login login) {
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        UserApi userApi = retrofit.create(UserApi.class);

        Call call = userApi.logUser(login);
        Log.d("TAG", "loginUser: " + login.getPassword());

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
                    editor.putString("profilpicture", users.get(0).getProfilpicture());
                    editor.putString("password", login.getPassword());
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

    public String md5(String crypt) {
        String cryptedPassword = null;
        MessageDigest mdEncrypte;

        try {
            mdEncrypte = MessageDigest.getInstance("MD5");
            mdEncrypte.update(crypt.getBytes(), 0, crypt.length());

            crypt = new BigInteger(1, mdEncrypte.digest()).toString(16);
            while (crypt.length() < 32) {
                crypt = "0" + crypt;
            }
            cryptedPassword = crypt;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return cryptedPassword;
    }
}
