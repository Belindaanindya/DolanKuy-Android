package com.example.dolankuyandroid.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.dolankuyandroid.API.APIRequestData;
import com.example.dolankuyandroid.API.RetroServer;
import com.example.dolankuyandroid.Activity.LoginActivity;
import com.example.dolankuyandroid.Activity.ProfileActivity;
import com.example.dolankuyandroid.Model.ResponseLogout;
import com.example.dolankuyandroid.Preferences.Preferences;
import com.example.dolankuyandroid.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private View view;
    private Button btn_logout;
    private Button btn_editProfile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_profile, container, false);

        btn_logout = view.findViewById(R.id.bt_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogout();
            }
        });

        btn_editProfile = view.findViewById(R.id.bt_editProfile);
        btn_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v = inflater.inflate(R.layout.navigation_bottom, container, false);
                TextView textView = v.findViewById(R.id.title);
                textView.setText("Edit Profile");
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.botNav_container, new EditProfileFragment());
                fragmentTransaction.commit();
            }
        });

        return view;
    }



    private void onLogout() {


        APIRequestData apiRequestData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseLogout> responseLogoutCall = apiRequestData.ardLogout(
                "Bearer" + Preferences.getKeyToken(getContext())
        );

        responseLogoutCall.enqueue(new Callback<ResponseLogout>() {
            @Override
            public void onResponse(Call<ResponseLogout> call, Response<ResponseLogout> response) {

                Toast.makeText(view.getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                Preferences.clearLoggedInUser(view.getContext());
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();

            }

            @Override
            public void onFailure(Call<ResponseLogout> call, Throwable t) {

            }
        });


    }

}
