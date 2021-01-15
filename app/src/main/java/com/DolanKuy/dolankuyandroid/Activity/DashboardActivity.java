package com.DolanKuy.dolankuyandroid.Activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.DolanKuy.dolankuyandroid.Fragment.CategoryFragment;
import com.DolanKuy.dolankuyandroid.Fragment.DashboardFragment;
import com.DolanKuy.dolankuyandroid.Fragment.ListLocationsFragment;
import com.DolanKuy.dolankuyandroid.Fragment.ProfileFragment;
import com.DolanKuy.dolankuyandroid.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
                        textView.setText("Beranda");
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
                        textView.setText("Daftar Wisata");
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
                        textView.setText("Profil");
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