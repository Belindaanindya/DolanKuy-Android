package com.DolanKuy.dolankuyandroid.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.DolanKuy.dolankuyandroid.API.APIRequestData;
import com.DolanKuy.dolankuyandroid.API.RetroServer;
import com.DolanKuy.dolankuyandroid.Model.ResponseUser;
import com.DolanKuy.dolankuyandroid.Model.User;
import com.DolanKuy.dolankuyandroid.Preferences.Preferences;
import com.DolanKuy.dolankuyandroid.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordFragment extends Fragment {

    private View view;
    private Button btn_save;
    private EditText et_password;
    private EditText et_rePassword;

    private User credentials;

    private static final int IMAGE_PICK_CODE = 1000;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.password_fragment, container, false);

        //getTag();

        et_password = view.findViewById(R.id.et_password);
        et_rePassword = view.findViewById(R.id.et_repassword);

        btn_save = view.findViewById(R.id.bt_savePassword);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEdit();

            }
        });

        return view;
    }

    private void onEdit() {

        String password = et_password.getText().toString();
        String rePassword = et_rePassword.getText().toString();

        if(password.trim().equals("")) {

            et_password.setError("Password Cannot be Empty !");

        } else if (!rePassword.trim().equals(password)) {

            et_rePassword.setError("Password Not Same !");

        }  else {

            APIRequestData apiRequestData = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponseUser> responseUserCall = apiRequestData.ardEditPassword(
                    "Bearer" + Preferences.getKeyToken(view.getContext()),
                    password
            );

            responseUserCall.enqueue(new Callback<ResponseUser>() {
                @Override
                public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                    if (response.isSuccessful()) {
                        credentials = response.body().getUsers();
                        Toast.makeText(view.getContext(), "Password has been saved", Toast.LENGTH_SHORT).show();
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frameLayout, new ProfileFragment());
                        fragmentTransaction.commit();

                    } else {
                        Toast.makeText(view.getContext(), "Respon Gagal", Toast.LENGTH_SHORT).show();
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
