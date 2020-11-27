package com.example.dolankuyandroid.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dolankuyandroid.API.APIRequestData;
import com.example.dolankuyandroid.API.RetroServer;
import com.example.dolankuyandroid.Model.DataModelDashboard;
import com.example.dolankuyandroid.Model.ResponseModelDetailListLocations;
import com.example.dolankuyandroid.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailListLocationsActivity extends AppCompatActivity {
    private DataModelDashboard detailLocations;
    private String id;
    private int detailImage;

    ImageView ivDetailImage;
    TextView tvId;
    TextView tvTitle;
    TextView tvDistance;
    TextView tvDescription;
    TextView tvAddress;
    TextView tvContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        tvId = findViewById(R.id.tv_id_detail);
        id = intent.getStringExtra("id");
        tvTitle = findViewById(R.id.tv_title);
        tvDescription = findViewById(R.id.tv_description);
        tvContact = findViewById(R.id.tv_contact);
        tvAddress = findViewById(R.id.tv_address);
        tvDistance = findViewById(R.id.tv_distance);

        Bundle bundle = getIntent().getExtras();
        ivDetailImage = findViewById(R.id.detail_image);

        detailImage = bundle.getInt("imageDetail");
        ivDetailImage.setImageResource(detailImage);

        detailListLocationWisata();
    }

    private void detailListLocationWisata(){
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModelDetailListLocations> tampilData = ardData.getDetailLocations(Integer.parseInt(id));

        tampilData.enqueue(new Callback<ResponseModelDetailListLocations>() {
            @Override
            public void onResponse(Call<ResponseModelDetailListLocations> call, Response<ResponseModelDetailListLocations> response) {
                detailLocations = response.body().getDetail_location();
                tvId.setText(String.valueOf(detailLocations.getId()));
                tvTitle.setText(detailLocations.getName());
                tvDescription.setText(detailLocations.getDescription());
                tvContact.setText(detailLocations.getContact());
                tvAddress.setText(detailLocations.getAddress());
                tvDistance.setText(String.valueOf(detailLocations.getDistance()));
            }

            @Override
            public void onFailure(Call<ResponseModelDetailListLocations> call, Throwable t) {
                Toast.makeText(DetailListLocationsActivity.this, "gagal menghubungkan " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}