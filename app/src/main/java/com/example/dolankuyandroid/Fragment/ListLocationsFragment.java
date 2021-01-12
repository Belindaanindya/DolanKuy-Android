package com.example.dolankuyandroid.Fragment;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.dolankuyandroid.API.APIRequestData;
import com.example.dolankuyandroid.API.RetroServer;
import com.example.dolankuyandroid.Adapter.AdapterDataListLocations;
import com.example.dolankuyandroid.Model.DataModelDashboard;
import com.example.dolankuyandroid.Model.ResponseModelListLocations;
import com.example.dolankuyandroid.Preferences.Preferences;
import com.example.dolankuyandroid.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListLocationsFragment extends Fragment {

    private RecyclerView rvData;
    private AdapterDataListLocations adData;
    private RecyclerView.LayoutManager lmData;
    private List<DataModelDashboard> listData = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private SearchView searchView;
    private View view;
    private String token="";
    private Double userLat = 0D;
    private Double userLong = 0D;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_list_wisata, container, false);

        searchView = view.findViewById(R.id.searchViewListWisata);
        rvData = view.findViewById(R.id.recylerViewWisata);
        swipeRefreshLayout = view.findViewById(R.id.swipe_wisata);
        lmData = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Preferences.getKeyLatitude(view.getContext()));


        if (!Preferences.getKeyLatitude(view.getContext()).equals("0")) {
            int index = stringBuilder.indexOf("&");
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append(Preferences.getKeyLatitude(view.getContext()));

            String lat = String.valueOf(stringBuilder.delete(index, stringBuilder.length()));
            String longi = String.valueOf(stringBuilder1.delete(0, index + 1));

            System.out.println("Latitude : " + lat);
            System.out.println("Longitude : " + longi);

            userLat = Double.valueOf(lat);
            userLong = Double.valueOf(longi);
        }

        token = Preferences.getKeyToken(view.getContext());

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                locationWisataDashboard();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adData.getFilter().filter(newText);


                return false;
            }
        });

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        locationWisataDashboard();
    }

    private void locationWisataDashboard(){
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModelListLocations> tampilData = ardData.ardLocationsWisata(
                "Bearer" + token,
                userLat,
                userLong
        );

        tampilData.enqueue(new Callback<ResponseModelListLocations>() {
            @Override
            public void onResponse(Call<ResponseModelListLocations> call, Response<ResponseModelListLocations> response) {
                if (response.isSuccessful()) {
                    listData = sortedList(response.body().getLocations());
                    adData = new AdapterDataListLocations(view.getContext(), listData);

                    rvData.setAdapter(adData);
                    adData.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseModelListLocations> call, Throwable t) {
                Toast.makeText(view.getContext(), "gagal menghubungkan " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private List<DataModelDashboard> sortedList (List<DataModelDashboard> list){

        DataModelDashboard temp = new DataModelDashboard();
        DataModelDashboard min = new DataModelDashboard();
        int minIndex = 0;

        for (int i = 0; i < list.size(); i++) {

            min = list.get(i);
            minIndex = i;

            for (int j = i+1; j < list.size(); j++) {

                if( list.get(j).getDistance() < min.getDistance()){

                    min = list.get(j);
                    minIndex = j;

                }

            }

            temp = list.get(i);
            //list.remove(i);
            list.set(i, min);
            list.set(minIndex, temp);

        }

        return list;
    }


}
