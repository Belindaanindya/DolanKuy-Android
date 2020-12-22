package com.example.dolankuyandroid.Fragment;

import android.graphics.Bitmap;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.dolankuyandroid.API.APIRequestData;
import com.example.dolankuyandroid.API.RetroServer;
import com.example.dolankuyandroid.Adapter.AdapterDataDashboard;
import com.example.dolankuyandroid.Model.DataModelDashboard;
import com.example.dolankuyandroid.Model.DataModelGallery;
import com.example.dolankuyandroid.Model.ResponseModelDashboard;
import com.example.dolankuyandroid.Model.ResponseModelListLocations;
import com.example.dolankuyandroid.Model.ResponseUser;
import com.example.dolankuyandroid.Model.User;
import com.example.dolankuyandroid.Preferences.Preferences;
import com.example.dolankuyandroid.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {
    private RecyclerView rvData;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private List<DataModelDashboard> listData = new ArrayList<>();
    private List<DataModelGallery> galleryList = new ArrayList<>();
    //private User credentials;
    private View view;
    private TextView tv_welcome;
    private CarouselView carouselView;
    private int[] sampleImages = {R.drawable.singapore, R.drawable.avatar, R.drawable.sate};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activivty_dashboard, container, false);

        rvData = view.findViewById(R.id.recycleViewData);
        lmData = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        GridLayoutManager glManager = new GridLayoutManager(view.getContext(),2,GridLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);
        rvData.setLayoutManager(glManager);
        carouselView =  view.findViewById(R.id.carouselView);

        //getDetailUser();

        locationWisataDashboard();

        carousel();
        //Preferences.setStatus(view.getContext(), "true");
        return view;
    }

    private void carousel() {
        carouselView.setPageCount(galleryList.size());
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                Picasso.get().load("http://192.168.1.10/DolanKuy-backend/DolanKuy-backend/public/storage/dolankuy/"+ galleryList.get(position).getFilename())
                        .into(imageView);
            }
        });
    }

//    private void getDetailUser() {
//
//        APIRequestData apiRequestData = RetroServer.konekRetrofit().create(APIRequestData.class);
//        Call<ResponseUser> responseUserCall = apiRequestData.ardUser(
//                "Bearer" + Preferences.getKeyToken(view.getContext())
//        );
//
//        responseUserCall.enqueue(new Callback<ResponseUser>() {
//            @Override
//            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
//
//                if(response.isSuccessful()) {
//
//                    credentials = response.body().getUsers();
//
//                    Preferences.setStatus(view.getContext(), "true");
//
//                    Toast.makeText(view.getContext(), "Token is valid", Toast.LENGTH_SHORT).show();
//
//                } else {
//
//                    Toast.makeText(view.getContext(), "Token is Invalid", Toast.LENGTH_SHORT).show();
//                    Preferences.setStatus(view.getContext(), "false");
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseUser> call, Throwable t) {
//                Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

    private void locationWisataDashboard(){

        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModelDashboard> showData = ardData.ardLocationsWisataDashboard();

        showData.enqueue(new Callback<ResponseModelDashboard>() {
            @Override
            public void onResponse(Call<ResponseModelDashboard> call, Response<ResponseModelDashboard> response) {
                if (response.isSuccessful()) {
                    listData = response.body().getLocations();
                    galleryList = response.body().getGalery();
                    adData = new AdapterDataDashboard(view.getContext(), listData);
                    rvData.setAdapter(adData);
                    adData.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseModelDashboard> call, Throwable t) {
                Toast.makeText(view.getContext(), "gagal menghubungkan " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
