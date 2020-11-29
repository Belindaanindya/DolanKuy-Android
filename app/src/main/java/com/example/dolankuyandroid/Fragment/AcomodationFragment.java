package com.example.dolankuyandroid.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dolankuyandroid.API.APIRequestData;
import com.example.dolankuyandroid.API.RetroServer;
import com.example.dolankuyandroid.Adapter.AdapterDataListLocations;
import com.example.dolankuyandroid.Adapter.AdapterListAcomodation;
import com.example.dolankuyandroid.Model.DataModelDashboard;
import com.example.dolankuyandroid.Model.ResponseAcomodation;
import com.example.dolankuyandroid.Model.ResponseModelListLocations;
import com.example.dolankuyandroid.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcomodationFragment extends Fragment {

    private RecyclerView rvData;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private List<DataModelDashboard> acomodationsData = new ArrayList<>();
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_akomodasi, container, false);

        rvData = view.findViewById(R.id.recyclerAkomodasi);
        lmData = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);

        rvData.setLayoutManager(lmData);

        if(!getTag().isEmpty()) {

            showAcomodation(Integer.parseInt(getTag()));

        }
        return view;

    }



    private void showAcomodation(int id){
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseAcomodation> tampilData = ardData.ardAcomodation(id);

        tampilData.enqueue(new Callback<ResponseAcomodation>() {
            @Override
            public void onResponse(Call<ResponseAcomodation> call, Response<ResponseAcomodation> response) {
                acomodationsData = response.body().getCurrentLocation();
                adData = new AdapterListAcomodation(view.getContext(), acomodationsData);
                rvData.setAdapter(adData);
                adData.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseAcomodation> call, Throwable t) {
                Toast.makeText(view.getContext(), "gagal menghubungkan " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




    }

}
