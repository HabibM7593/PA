package com.example.benevent.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.benevent.API.NetworkClient;
import com.example.benevent.API.UserApi;
import com.example.benevent.Models.Signup;
import com.example.benevent.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignupActivity extends AppCompatActivity {

    public ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        backButton = findViewById(R.id.back_button_signup);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
                //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
    }

    public void Signup(View view) throws ParseException {
        EditText nameET = (EditText) findViewById(R.id.name_signup);
        EditText firstnameET = (EditText) findViewById(R.id.firstname_signup);
        EditText ageET = (EditText) findViewById(R.id.age_signup);
        EditText emailET = (EditText) findViewById(R.id.email_signup);
        EditText passwordET = (EditText) findViewById(R.id.password_signup);

        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");

        Date ageDate = formatter1.parse(ageET.getText().toString());
        String age = formatter2.format(ageDate);


        if(nameET.getText().toString().equals("")||firstnameET.getText().toString().equals("")||age.equals("")||emailET.getText().toString().equals("")||passwordET.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Faites attention à ne pas envoyer de champs vides", Toast.LENGTH_LONG).show();
        }else{
            signUser(new Signup(nameET.getText().toString(),firstnameET.getText().toString(),age,emailET.getText().toString(),passwordET.getText().toString()));
        }

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
                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
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
}