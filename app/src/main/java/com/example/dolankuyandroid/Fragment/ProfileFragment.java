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
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private View view;
    private Button btn_logout;
    private Button btn_editProfile;
    private Button btn_sign_in_profile;
    private Button btn_changePassword;
    private Button btn_changeEmail;
    private Button btn_getLocation;
    private User credentials = new User();
    private TextView tv_username;
    private TextView tv_email;
    private CircleImageView civ_profileImage;
    private String status="";
    private String token="";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_profile, container, false);

        initialize();

        if (! token.isEmpty()) {
            btn_getLocation.setVisibility(View.VISIBLE);
            btn_changePassword.setVisibility(View.VISIBLE);
            btn_changeEmail.setVisibility(View.VISIBLE);
            btn_sign_in_profile.setVisibility(View.GONE);
            btn_editProfile.setVisibility(View.VISIBLE);
            btn_logout.setVisibility(View.VISIBLE);

            getDetailUser();

            btn_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onLogout();
                }
            });

            btn_changeEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, new EmailFragment(tv_email.getText().toString()) );
                    fragmentTransaction.commit();
                }
            });

            btn_changePassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, new PasswordFragment());
                    fragmentTransaction.commit();
                }
            });


            btn_editProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, new EditProfileFragment(credentials.getImage(), credentials.getName()));
                    fragmentTransaction.commit();
                }
            });

        } else {

            btn_logout.setVisibility(View.GONE);
            btn_sign_in_profile.setVisibility(View.VISIBLE);
            btn_editProfile.setVisibility(View.GONE);
            btn_getLocation.setVisibility(View.GONE);
            btn_changePassword.setVisibility(View.GONE);
            btn_changeEmail.setVisibility(View.GONE);

            btn_sign_in_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(view.getContext(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            });

        }

        return view;
    }

    private void initialize() {

        token = Preferences.getKeyToken(view.getContext());

        tv_email = view.findViewById(R.id.email_profile);
        tv_username = view.findViewById(R.id.username_profile);
        civ_profileImage = view.findViewById(R.id.profile_image);
        btn_sign_in_profile = view.findViewById(R.id.bt_sign_in_profile);
        btn_logout = view.findViewById(R.id.bt_logout);
        btn_editProfile = view.findViewById(R.id.bt_editProfile);
        btn_changeEmail = view.findViewById(R.id.btn_changeEmail);
        btn_changePassword = view.findViewById(R.id.btn_changePassword);
        btn_getLocation = view.findViewById(R.id.btn_getLocation);
    }

    private void getDetailUser() {

        APIRequestData apiRequestData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseUser> responseUserCall = apiRequestData.ardUser(
                "Bearer" + token
        );

        responseUserCall.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {

                if(response.isSuccessful()) {

                    status = response.body().getStatus();

                    if(status.equalsIgnoreCase("token is valid")) {
                        credentials = response.body().getUsers();
                        tv_email.setText(credentials.getEmail());
                        tv_username.setText(credentials.getName());

                        Picasso.get()
                                .load("http://192.168.1.10/DolanKuy-backend/DolanKuy-backend/public/storage/users/" + credentials.getImage())
                                .into(civ_profileImage);

                        Toast.makeText(view.getContext(), status, Toast.LENGTH_SHORT).show();
                    } else {
                        Preferences.clearLoggedInUser(view.getContext());
                        btn_logout.setVisibility(View.GONE);
                        btn_sign_in_profile.setVisibility(View.VISIBLE);
                        btn_editProfile.setVisibility(View.GONE);
                        Toast.makeText(view.getContext(), status, Toast.LENGTH_SHORT).show();
                    }


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

                Toast.makeText(view.getContext(), response.body().getStatus(), Toast.LENGTH_SHORT).show();

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
