package com.example.dolankuyandroid.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.dolankuyandroid.API.APIRequestData;
import com.example.dolankuyandroid.API.RetroServer;
import com.example.dolankuyandroid.Model.ResponseLogin;
import com.example.dolankuyandroid.Preferences.Preferences;
import com.example.dolankuyandroid.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Button bt_signIn;
    private TextView tv_register;
    private EditText et_email;
    private EditText et_password;
    private ResponseLogin responseLogin;
    private ProgressBar progressBar;
    private TextView tv_skip;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Double userLat = 0D;
    private Double userLong = 0D;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

//        if (Preferences.getLoggedInStatus(getBaseContext())){
//            startActivity(new Intent(getBaseContext(), HomeActivity.class));
//            finish();
//        } else {

            bt_signIn = findViewById(R.id.signIn_button);
            tv_register = findViewById(R.id.tv_register);
            tv_skip = findViewById(R.id.tv_skip);
            et_email = findViewById(R.id.signIn_email);
            et_password = findViewById(R.id.signIn_password);
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

            progressBar = findViewById(R.id.pb_signIn);

            tv_skip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getBaseContext(), DashboardActivity.class));
                    finish();
                }
            });

            bt_signIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSignIn();

                }
            });

            tv_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRegister();
                }
            });

        //}

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Preferences.getStatus(getBaseContext()).equals("true")){
            startActivity(new Intent(getBaseContext(), DashboardActivity.class));
            finish();
        }
    }

    public void onSignIn (){

        String email = et_email.getText().toString();
        String password = et_password.getText().toString();

        if(email.trim().equals("")) {

            et_email.setError("Email Cannot be Empty !");

        } else if (password.trim().equals("")) {

            et_password.setError("Password Cannot be Empty !");

        } else {


            //Preferences.setPasswordUser(getBaseContext(), password);
            progressBar.setVisibility(View.VISIBLE);

            APIRequestData ardSignIn = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponseLogin> loginCall = ardSignIn.ardLogin(email, password);

            loginCall.enqueue(new Callback<ResponseLogin>() {
                @Override
                public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {


                   if(response.isSuccessful()) {
                       responseLogin  = response.body();

                       Preferences.setKeyToken(getBaseContext(), responseLogin.getToken());
                       Preferences.setStatus(getBaseContext(), "true");
                       Toast.makeText(getBaseContext(), "login is successful", Toast.LENGTH_SHORT).show();


                       startActivity(new Intent(LoginActivity.this, DashboardActivity.class));

                       progressBar.setVisibility(View.INVISIBLE);

                       finish();

                   } else {

                       Toast.makeText(LoginActivity.this, "login is failed", Toast.LENGTH_SHORT).show();
                       progressBar.setVisibility(View.INVISIBLE);

                   }


                }

                @Override
                public void onFailure(Call<ResponseLogin> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                    progressBar.setVisibility(View.INVISIBLE);

                }
            });

        }

    }

    public void onRegister() {
        Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
        startActivity(intent);
        finish();
    }

}