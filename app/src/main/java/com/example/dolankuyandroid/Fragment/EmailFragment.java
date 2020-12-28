package com.example.dolankuyandroid.Fragment;

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
import androidx.fragment.app.FragmentTransaction;

import com.example.dolankuyandroid.API.APIRequestData;
import com.example.dolankuyandroid.API.RetroServer;
import com.example.dolankuyandroid.Model.ResponseUser;
import com.example.dolankuyandroid.Model.User;
import com.example.dolankuyandroid.Preferences.Preferences;
import com.example.dolankuyandroid.R;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailFragment extends Fragment {


    private View view;
    private Button btn_save;
    private EditText et_email;
    private String email="abc";

    private User credentials;

    private static final int IMAGE_PICK_CODE = 1000;

    public EmailFragment(String email){
        this.email = email;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.email_fragment, container, false);

        et_email = view.findViewById(R.id.et_email);
        btn_save = view.findViewById(R.id.bt_saveEmail);

        et_email.setText(email);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEdit();

            }
        });

        return view;
    }

    private void onEdit() {

        String e_email = et_email.getText().toString();

        if(e_email.trim().equals("")) {

            et_email.setError("Email Cannot be Empty !");

        } else if (e_email.trim().equals(email)) {

            et_email.setError("Email has been used !");

        }  else {

            APIRequestData apiRequestData = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponseUser> responseUserCall = apiRequestData.ardEditEmail(
                    "Bearer" + Preferences.getKeyToken(view.getContext()),
                    e_email
            );

            responseUserCall.enqueue(new Callback<ResponseUser>() {
                @Override
                public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                    if (response.isSuccessful()) {
                        credentials = response.body().getUsers();
                        Toast.makeText(view.getContext(), "Email has been saved", Toast.LENGTH_SHORT).show();
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
