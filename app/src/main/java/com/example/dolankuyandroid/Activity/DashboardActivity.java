package com.example.dolankuyandroid.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.dolankuyandroid.Fragment.CategoryFragment;
import com.example.dolankuyandroid.Fragment.DashboardFragment;
import com.example.dolankuyandroid.Fragment.ListLocationsFragment;
import com.example.dolankuyandroid.Fragment.ProfileFragment;
import com.example.dolankuyandroid.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

    TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_bottom);

        BottomNavigationView botNavView = findViewById(R.id.bottom_navigation);
        botNavView.setOnNavigationItemSelectedListener(navListener);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.botNav_container, new DashboardFragment());
        fragmentTransaction.commit();

        textView = findViewById(R.id.title);
        textView.setText("Dashboard");

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()){
                case R.id.home_botNav:
                    selectedFragment = new DashboardFragment();
                    overridePendingTransition(0, 0);
                    textView.setText("Dashboard");
                    break;
                case R.id.listWisata_botNav:
                    selectedFragment = new ListLocationsFragment();
                    overridePendingTransition(0,0);
                    textView.setText("List Wisata");
                    break;

                case R.id.akomodasi_botNav:
                    selectedFragment = new CategoryFragment();
                    overridePendingTransition(0,0);
                    textView.setText("Akomodasi");
                    break;

                case R.id.profile_botNav:
                    selectedFragment = new ProfileFragment();
                    overridePendingTransition(0,0);
                    textView.setText("Profile");
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.botNav_container,
                    selectedFragment).commit();
            return true;
        }
    };
}