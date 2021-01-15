package com.DolanKuy.dolankuyandroid.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.DolanKuy.dolankuyandroid.API.APIRequestData;
import com.DolanKuy.dolankuyandroid.API.RetroServer;
import com.DolanKuy.dolankuyandroid.Model.DataCategory;
import com.DolanKuy.dolankuyandroid.Model.ResponseCategory;
import com.DolanKuy.dolankuyandroid.Preferences.Preferences;
import com.DolanKuy.dolankuyandroid.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {

    private View view;
    private String idKuliner;
    private String idTempatIbadah;
    private String idMarket;
    private String idSPBU;
    private CardView cardKuliner;
    private CardView cardIbadah;
    private CardView cardMarket;
    private CardView cardSPBU;
    private double userLat = 0D;
    private double userLong = 0D;

    private List<DataCategory> categoryListData =  new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_kategori_akomodasi, container, false);

        cardKuliner = view.findViewById(R.id.cardKuliner);
        cardSPBU = view.findViewById(R.id.cardSPBU);
        cardMarket = view.findViewById(R.id.cardMarket);
        cardIbadah = view.findViewById(R.id.cardIbadah);

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

        showData();

        cardKuliner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new AcomodationFragment(), idKuliner);
                fragmentTransaction.commit();
            }
        });

        cardSPBU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new AcomodationFragment(), idSPBU);
                fragmentTransaction.commit();
            }
        });

        cardMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new AcomodationFragment(), idMarket);
                fragmentTransaction.commit();
            }
        });

        cardIbadah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new AcomodationFragment(), idTempatIbadah);
                fragmentTransaction.commit();
            }
        });

        return view;
    }


    public void showData() {

        APIRequestData apiRequestData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseCategory> responseCategoryCall = apiRequestData.ardCategory();

        responseCategoryCall.enqueue(new Callback<ResponseCategory>() {
            @Override
            public void onResponse(Call<ResponseCategory> call, Response<ResponseCategory> response) {

                if (response.isSuccessful()) {

                    //Toast.makeText(CategoryActivity.this, "Berhasil Mendapat Respon", Toast.LENGTH_SHORT).show();

                    categoryListData = response.body().getCategory();
                    for (int i = 0; i < categoryListData.size(); i++) {
                        if (categoryListData.get(i).getName().equalsIgnoreCase("spbu")){
                            idSPBU = Integer.toString(categoryListData.get(i).getId());
                        } else if (categoryListData.get(i).getName().equalsIgnoreCase("kuliner")){
                            idKuliner = Integer.toString(categoryListData.get(i).getId());
                        } else if (categoryListData.get(i).getName().equalsIgnoreCase("tempat ibadah")){
                            idTempatIbadah = Integer.toString(categoryListData.get(i).getId());
                        } else if (categoryListData.get(i).getName().equalsIgnoreCase("market")){
                            idMarket = Integer.toString(categoryListData.get(i).getId());
                        }
                    }

                } else {

                    Toast.makeText(view.getContext(), "Gagal Mendapat Respon", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<ResponseCategory> call, Throwable t) {

                Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}
