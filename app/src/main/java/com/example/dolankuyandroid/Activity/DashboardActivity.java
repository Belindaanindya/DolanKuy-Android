package com.example.dolankuyandroid.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.dolankuyandroid.Fragment.AcomodationFragment;
import com.example.dolankuyandroid.Fragment.CategoryFragment;
import com.example.dolankuyandroid.Fragment.DashboardFragment;
import com.example.dolankuyandroid.Fragment.ListLocationsFragment;
import com.example.dolankuyandroid.Fragment.ProfileFragment;
import com.example.dolankuyandroid.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DashboardActivity extends AppCompatActivity {

    private TextView textView;
    private Fragment selectedFragment = null;
    private int tmp = 3;
    private Double userLat = 0D;
    private Double userLong = 0D;
    String a;
    String b;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_bar);

        BottomNavigationView botNavView = findViewById(R.id.bottom_navigation);
        botNavView.setOnNavigationItemSelectedListener(navListener);

        selectedFragment = new ProfileFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, selectedFragment);
        fragmentTransaction.commit();
        botNavView.setSelectedItemId(R.id.profile_botNav);

        textView = findViewById(R.id.title);
        textView.setText("My Profile");

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            switch (item.getItemId()){
                case R.id.home_botNav:
                    if(tmp != 0) {
                        selectedFragment = new DashboardFragment();
                        overridePendingTransition(0, 0);
                        textView.setText("Dashboard");
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,
                                selectedFragment).commit();
                        tmp = 0;
                        break;
                    }
                    break;

                case R.id.listWisata_botNav:
                    if(tmp != 1) {
                        selectedFragment = new ListLocationsFragment();
                        overridePendingTransition(0, 0);
                        textView.setText("List Wisata");
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,
                                selectedFragment).commit();
                        tmp = 1;
                        break;
                    }
                    break;

                case R.id.akomodasi_botNav:
                    if(tmp != 2) {
                        selectedFragment = new CategoryFragment();
                        overridePendingTransition(0, 0);
                        textView.setText("Akomodasi");
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,
                                selectedFragment).commit();
                        tmp = 2;
                        break;
                    }
                    break;

                case R.id.profile_botNav:
                    if(tmp != 3) {
                        selectedFragment = new ProfileFragment();
                        overridePendingTransition(0, 0);
                        textView.setText("My Profile");
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,
                                selectedFragment).commit();
                        tmp = 3;
                        break;
                    }
                    break;
            }

            
            return true;
        }
    };
}