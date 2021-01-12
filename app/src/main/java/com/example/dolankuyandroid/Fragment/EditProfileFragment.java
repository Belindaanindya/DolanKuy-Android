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
import androidx.fragment.app.FragmentTransaction;

import com.example.dolankuyandroid.API.APIRequestData;
import com.example.dolankuyandroid.API.RetroServer;
import com.example.dolankuyandroid.Activity.DashboardActivity;
import com.example.dolankuyandroid.Model.ResponseUser;
import com.example.dolankuyandroid.Model.User;
import com.example.dolankuyandroid.Preferences.Preferences;
import com.example.dolankuyandroid.R;
import com.squareup.picasso.Picasso;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class EditProfileFragment extends Fragment {

    private View view;
    private Button btn_save;
    private EditText et_username;

    private CircleImageView civ_editProfile;
    private TextView tv_editProfile;
    private String image;
    private String username="";
    File file;

    private User credentials;

    private static final int IMAGE_PICK_CODE = 1000;

    public EditProfileFragment(String image, String username){
        this.image = image;
        this.username = username;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_edit_profile, container, false);

        //getTag();

        et_username = view.findViewById(R.id.et_username);

        civ_editProfile = view.findViewById(R.id.profile_image);
        tv_editProfile = view.findViewById(R.id.tv_editProfile);

        et_username.setText(username);

        Picasso.get()
                .load("http://192.168.1.10/DolanKuy-backend/DolanKuy-backend/public/storage/users/"+ image)
                .into(civ_editProfile);

        tv_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromGallery();
            }
        });

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

        String name = et_username.getText().toString();

        if(name.trim().equals("")) {

            et_username.setError("Username Cannot be Empty !");

        } else if (name.trim().equals("")) {

            et_username.setError("Password Cannot be Empty !");

        }  else {

            APIRequestData apiRequestData = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponseUser> responseUserCall = apiRequestData.ardEditProfile(
                    "Bearer" + Preferences.getKeyToken(view.getContext()),
                    name
            );

            responseUserCall.enqueue(new Callback<ResponseUser>() {
                @Override
                public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                    if (response.isSuccessful()) {
                        credentials = response.body().getUsers();
                        Toast.makeText(view.getContext(), "Profile has been saved", Toast.LENGTH_SHORT).show();
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

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            civ_editProfile.setImageURI(data.getData());
            file = new File(data.getData().getPath());
        }
    }
}
