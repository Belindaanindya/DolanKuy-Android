package com.example.dolankuyandroid.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dolankuyandroid.API.APIRequestData;
import com.example.dolankuyandroid.API.RetroServer;
import com.example.dolankuyandroid.Fragment.CategoryFragment;
import com.example.dolankuyandroid.Fragment.DashboardFragment;
import com.example.dolankuyandroid.Fragment.DetailsFragment;
import com.example.dolankuyandroid.Fragment.GaleryFragment;
import com.example.dolankuyandroid.Fragment.ListLocationsFragment;
import com.example.dolankuyandroid.Fragment.ProfileFragment;
import com.example.dolankuyandroid.Model.DataModelDashboard;
import com.example.dolankuyandroid.Model.ResponseModelDetailListLocations;
import com.example.dolankuyandroid.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailListLocationsActivity extends AppCompatActivity {
    private DataModelDashboard detailLocations;
    private String id;
    private int detailImage;

    private ImageView ivDetailImage;
    private TextView tvId;
    private TextView tvTitle;
    private TextView tvDistance;
    private Fragment selectedFragment = null;
    private int tmp = 0;
    private ImageButton btn_direction;
    private LinearLayout linearLayout;
    private FrameLayout frameLayout;
    private String distance="";
    //TextView tvDescription;
    //TextView tvAddress;
    //TextView tvContact;
    SupportMapFragment supportMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        detailLocations = new DataModelDashboard();
        Intent intent = getIntent();

        frameLayout = findViewById(R.id.fragment_details);
        btn_direction = findViewById(R.id.bt_direction);
        tvId = findViewById(R.id.tv_id_detail);
        id = intent.getStringExtra("id");
        distance = intent.getStringExtra("distance");
        tvTitle = findViewById(R.id.tv_title);
        tvDistance = findViewById(R.id.tv_distance);
        linearLayout = findViewById(R.id.gmaps_layout);
        btn_direction.setVisibility(View.GONE);
        //tvDescription = findViewById(R.id.tv_description);
        //tvContact = findViewById(R.id.tv_contact);
        //tvAddress = findViewById(R.id.tv_address);

        //Bundle bundle = getIntent().getExtras();
        ivDetailImage = findViewById(R.id.detail_image);

        //detailImage = bundle.getInt("imageDetail");
        //ivDetailImage.setImageResource(detailImage);

        detailListLocationWisata();



        BottomNavigationView botNavView = findViewById(R.id.navigation_details);
        botNavView.setOnNavigationItemSelectedListener(navListener);
        selectedFragment = new DetailsFragment(id);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_details, selectedFragment);
        fragmentTransaction.commit();



        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_maps);

        onDirection();


    }


    private void onDirection(){
        btn_direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(v.VISIBLE);
                //frameLayout.setVisibility(v.GONE);
            }
        });
    }



    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            switch (item.getItemId()){
                case R.id.deskripsi_botNav:
                    //if(tmp != 0) {
                        selectedFragment = new DetailsFragment(id);
                        overridePendingTransition(0, 0);
                        linearLayout.setVisibility(View.VISIBLE);
                        //frameLayout.setVisibility(View.VISIBLE);
                    //detailListLocationWisata();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_details,
                                selectedFragment).commit();

                        tmp = 0;
                        //break;
                    //}
                    break;

                case R.id.galery_botNav:
                    if(tmp != 1) {
                        selectedFragment = new GaleryFragment();
                        overridePendingTransition(0, 0);
                        linearLayout.setVisibility(View.GONE);
                        //frameLayout.setVisibility(View.VISIBLE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_details,
                                selectedFragment).commit();
                        tmp = 1;
                        break;
                    }
                    break;


            }


            return true;
        }
    };

    private void detailListLocationWisata(){
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModelDetailListLocations> tampilData = ardData.getDetailLocations(Integer.parseInt(id));

        tampilData.enqueue(new Callback<ResponseModelDetailListLocations>() {
            @Override
            public void onResponse(Call<ResponseModelDetailListLocations> call, Response<ResponseModelDetailListLocations> response) {
                detailLocations = response.body().getDetail_location();
                tvId.setText(String.valueOf(detailLocations.getId()));
                tvTitle.setText(detailLocations.getName());
                tvDistance.setText(distance);
                Picasso.get()
                        .load("http://192.168.1.7/DolanKuy/DolanKuy-backend/public/storage/dolankuy/"+ detailLocations.getImage())
                        .into(ivDetailImage);
//                tvDescription = view.findViewById(R.id.tv_description);
//                tvContact = view.findViewById(R.id.tv_contact);
//                tvAddress = view.findViewById(R.id.tv_address);

                supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        LatLng latLng = new LatLng(detailLocations.getLatitude(),
                                detailLocations.getLongitude());

                        MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                                .title(detailLocations.getName());

                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                        googleMap.addMarker(markerOptions);

                    }
                });

            }

            @Override
            public void onFailure(Call<ResponseModelDetailListLocations> call, Throwable t) {
                Toast.makeText(DetailListLocationsActivity.this, "gagal menghubungkan " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}