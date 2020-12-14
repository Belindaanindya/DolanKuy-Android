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
import com.example.dolankuyandroid.Model.ResponseLogout;
import com.example.dolankuyandroid.Model.ResponseUser;
import com.example.dolankuyandroid.Model.User;
import com.example.dolankuyandroid.Preferences.Preferences;
import com.example.dolankuyandroid.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private View view;
    private Button btn_logout;
    private Button btn_editProfile;
    private User credentials;
    private TextView tv_username;
    private TextView tv_email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_profile, container, false);

        tv_email = view.findViewById(R.id.email_profile);
        tv_username = view.findViewById(R.id.username_profile);

        getDetailUser();

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
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new EditProfileFragment());
                fragmentTransaction.commit();
            }
        });

        return view;
    }


    private void getDetailUser() {

        APIRequestData apiRequestData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseUser> responseUserCall = apiRequestData.ardUser(
                "Bearer" + Preferences.getKeyToken(view.getContext())
        );

        responseUserCall.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {

                if(!response.body().getUsers().getName().isEmpty()) {

                    credentials = response.body().getUsers();
                    tv_email.setText(credentials.getEmail());
                    tv_username.setText(credentials.getName());
                    Toast.makeText(view.getContext(), "Token is valid", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(view.getContext(), "Token is Invalid", Toast.LENGTH_SHORT).show();
                    Preferences.setStatus(view.getContext(), "false");

                }

            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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
                Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}
