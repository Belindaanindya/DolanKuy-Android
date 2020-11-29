package com.example.dolankuyandroid.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dolankuyandroid.API.APIRequestData;
import com.example.dolankuyandroid.API.RetroServer;
import com.example.dolankuyandroid.Model.ResponseRegister;
import com.example.dolankuyandroid.Model.User;
import com.example.dolankuyandroid.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private Button bt_signUp;
    private TextView tv_login;
    private EditText et_Username;
    private EditText et_Email;
    private EditText et_Password;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        et_Username = findViewById(R.id.signUp_username);
        et_Email = findViewById(R.id.signUp_email);
        et_Password = findViewById(R.id.signUp_password);

        bt_signUp = findViewById(R.id.signUp_button);
        tv_login = findViewById(R.id.tv_login);

        progressBar = findViewById(R.id.pb_signUp);

        bt_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignUp();
            }
        });

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogin();
            }
        });

    }

    public void onSignUp (){

        String name = et_Username.getText().toString();
        String email = et_Email.getText().toString();
        String password = et_Password.getText().toString();

        if(name.trim().equals("")){

            et_Username.setError("Username Cannot be Empty !");

        } else if (email.trim().equals("")) {

            et_Email.setError("Email Cannot be Empty !");

        } else if (password.trim().equals("")) {

            et_Password.setError("Password Cannot be Empty !");

        } else {

            progressBar.setVisibility(View.VISIBLE);

            APIRequestData ardSignUp = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponseRegister> newUsers = ardSignUp.ardRegister(name, email, password);

            newUsers.enqueue(new Callback<ResponseRegister>() {
                @Override
                public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {

                    if (response.isSuccessful()) {

                        User user = response.body().getUser();
                        String token = response.body().getToken();

                        Toast.makeText(RegisterActivity.this, "registration is successful", Toast.LENGTH_SHORT).show();

                        onLogin();
                        finish();


                    } else {

                        Toast.makeText(RegisterActivity.this, "registration is failed", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);

                    }

                }

                @Override
                public void onFailure(Call<ResponseRegister> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });

        }
    }

    public void onLogin () {
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(intent);
        progressBar.setVisibility(View.INVISIBLE);
        finish();
    }

}