package com.DolanKuy.dolankuyandroid.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.DolanKuy.dolankuyandroid.Preferences.Preferences;
import com.DolanKuy.dolankuyandroid.R;

public class WelcomeActivity extends AppCompatActivity {
    Button signInBtn;
    Button signUpBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        signInBtn = findViewById(R.id.SignInButton);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signUpBtn = findViewById(R.id.btnSignUpSplashScreen);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (Preferences.getStatus(getBaseContext()).equals("true")){
            startActivity(new Intent(getBaseContext(), DashboardActivity.class));
            finish();
        }

    }
}
