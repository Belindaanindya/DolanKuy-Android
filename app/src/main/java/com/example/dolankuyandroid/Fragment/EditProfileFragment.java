package com.example.dolankuyandroid.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dolankuyandroid.API.APIRequestData;
import com.example.dolankuyandroid.API.RetroServer;
import com.example.dolankuyandroid.Activity.DashboardActivity;
import com.example.dolankuyandroid.Model.ResponseUser;
import com.example.dolankuyandroid.Model.User;
import com.example.dolankuyandroid.Preferences.Preferences;
import com.example.dolankuyandroid.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileFragment extends Fragment {

    private View view;
    private Button btn_save;
    private EditText et_username;
    private EditText et_password;
    private EditText et_email;
    private User credentials;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_edit_profile, container, false);

        //getTag();

        et_username = view.findViewById(R.id.et_username);
        et_email = view.findViewById(R.id.et_email);
        et_password = view.findViewById(R.id.et_password);

        btn_save = view.findViewById(R.id.bt_saveProfile);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEdit();
            }
        });

        return view;
    }

    private void onEdit() {

        String email = et_email.getText().toString();
        String name = et_username.getText().toString();
        String password = et_password.getText().toString();

        if(name.trim().equals("")) {

            et_username.setError("Username Cannot be Empty !");

        } else if (password.trim().equals("")) {

            et_password.setError("Password Cannot be Empty !");

        } else if (email.trim().equals("")) {

            et_email.setError("Email Cannot be Empty !");

        } else {

            APIRequestData apiRequestData = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponseUser> responseUserCall = apiRequestData.ardEditProfile(
                    "Bearer" + Preferences.getKeyToken(view.getContext()), name, email, password
            );

            responseUserCall.enqueue(new Callback<ResponseUser>() {
                @Override
                public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                    if (response.isSuccessful()) {
                        credentials = response.body().getUsers();
                        Toast.makeText(view.getContext(), "Profile has been saved", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(view.getContext(), DashboardActivity.class));

                    } else {
                        Toast.makeText(view.getContext(), email + password + name, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseUser> call, Throwable t) {

                    Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }


    }


}
