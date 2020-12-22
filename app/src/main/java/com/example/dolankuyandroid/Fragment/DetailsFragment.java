package com.example.dolankuyandroid.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dolankuyandroid.API.APIRequestData;
import com.example.dolankuyandroid.API.RetroServer;
import com.example.dolankuyandroid.Activity.DetailListLocationsActivity;
import com.example.dolankuyandroid.Model.DataModelDashboard;
import com.example.dolankuyandroid.Model.ResponseModelDetailListLocations;
import com.example.dolankuyandroid.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsFragment extends Fragment {

    private View view;
    private DataModelDashboard detailLocations;
    private TextView tvDescription;
    private TextView tvAddress;
    private TextView tvContact;
    private String id;

    public DetailsFragment(String id) {
        this.id = id;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.description_activity, container, false);

        tvDescription = view.findViewById(R.id.tv_description);
        tvContact = view.findViewById(R.id.tv_contact);
        tvAddress = view.findViewById(R.id.tv_address);

        detailListLocationWisata();


        return view;
    }

    private void detailListLocationWisata() {
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModelDetailListLocations> tampilData = ardData.getDetailLocations(Integer.parseInt(id));

        tampilData.enqueue(new Callback<ResponseModelDetailListLocations>() {
            @Override
            public void onResponse(Call<ResponseModelDetailListLocations> call, Response<ResponseModelDetailListLocations> response) {
                detailLocations = response.body().getDetail_location();

                tvDescription.setText(detailLocations.getDescription());
                tvContact.setText(detailLocations.getContact());
                tvAddress.setText(detailLocations.getAddress());


            }

            @Override
            public void onFailure(Call<ResponseModelDetailListLocations> call, Throwable t) {
                Toast.makeText(view.getContext(), "gagal menghubungkan " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
