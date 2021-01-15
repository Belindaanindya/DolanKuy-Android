package com.DolanKuy.dolankuyandroid.Fragment;

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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.DolanKuy.dolankuyandroid.API.APIRequestData;
import com.DolanKuy.dolankuyandroid.API.RetroServer;
import com.DolanKuy.dolankuyandroid.Adapter.AdapterDataDashboard;
import com.DolanKuy.dolankuyandroid.Model.DataModelDashboard;
import com.DolanKuy.dolankuyandroid.Model.DataModelGallery;
import com.DolanKuy.dolankuyandroid.Model.ResponseModelDashboard;
import com.DolanKuy.dolankuyandroid.Preferences.Preferences;
import com.DolanKuy.dolankuyandroid.R;
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
    private int[] sampleImages = {R.drawable.arjuno, R.drawable.butak, R.drawable.mtpundak, R.drawable.gunungsemeru, R.drawable.ranukumbolo};
    private SwipeRefreshLayout swipeRefreshLayout;
    private String token="";
    private Double userLat = 0D;
    private Double userLong = 0D;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activivty_dashboard, container, false);

        rvData = view.findViewById(R.id.recycleViewData);
        lmData = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        //swipeRefreshLayout = view.findViewById(R.id.swipe_dashboard);
        GridLayoutManager glManager = new GridLayoutManager(view.getContext(),2,GridLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);
        rvData.setLayoutManager(glManager);

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
        carouselView =  view.findViewById(R.id.carouselView);

        //getDetailUser();

        locationWisataDashboard();
        carousel();
        //Preferences.setStatus(view.getContext(), "true");
        return view;
    }



    private void carousel() {
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(sampleImages[position]);
            }
        });
    }

    private void locationWisataDashboard(){

        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModelDashboard> showData = ardData.ardLocationsWisataDashboard(
                "Bearer" + token,
                userLat,
                userLong
        );

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
