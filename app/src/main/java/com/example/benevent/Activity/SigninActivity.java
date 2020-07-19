package com.example.benevent.Activity;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

        signupButton.setOnClickListener(view -> startActivity(new Intent(SigninActivity.this, SignupActivity.class)));

        SharedPreferences sharedPreferences = this.getSharedPreferences("login", MODE_PRIVATE);
        String password = sharedPreferences.getString("password", "");
        String email = sharedPreferences.getString("email", "");
        if (!password.equals("") || !email.equals("")) {
            loginUser(new Login(email,password));
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Attention");
        alertDialog.setMessage("Voulez-vous quitter l'application ?");
        alertDialog.setPositiveButton("OUI", (dialog, which) -> {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        alertDialog.setNegativeButton("NON", (dialog, which) -> dialog.dismiss());
        alertDialog.create().show();
    }

    public void signIn(View view) {
        EditText loginET = findViewById(R.id.log_ed);
        EditText passwordET = findViewById(R.id.pass_ed);
        loginUser(new Login(loginET.getText().toString(), md5(passwordET.getText().toString())));
    }

    private void loginUser(Login login) {
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        UserApi userApi = retrofit.create(UserApi.class);
        Call call = userApi.logUser(login);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.code() == 200) {
                    List<User> users = response.body();
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("login", MODE_PRIVATE);
                    SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
                    sharedPrefEditor.putInt("userid", users.get(0).getId());
                    sharedPrefEditor.putString("username", users.get(0).getFirstname() + " " + users.get(0).getName());
                    sharedPrefEditor.putString("email", users.get(0).getEmail());
                    sharedPrefEditor.putString("profilpicture", users.get(0).getProfilpicture());
                    sharedPrefEditor.putString("password", login.getPassword());
                    sharedPrefEditor.apply();
                    startActivity(new Intent(context, FragmentManagerActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Verifiez vos identifiants", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Verifiez votre connexion internet", Toast.LENGTH_LONG).show();
            }
        });
    }

    public String md5(String stringToCrypt) {
        String cryptedPassword = null;
        MessageDigest md5Crypter;
        try {
            md5Crypter = MessageDigest.getInstance("MD5");
            md5Crypter.update(stringToCrypt.getBytes(), 0, stringToCrypt.length());
            stringToCrypt = new BigInteger(1, md5Crypter.digest()).toString(16);
            while (stringToCrypt.length() < 32) {
                stringToCrypt = "0" + stringToCrypt;
            }
            cryptedPassword = stringToCrypt;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return cryptedPassword;
    }
}
