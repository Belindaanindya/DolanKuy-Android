package com.DolanKuy.dolankuyandroid.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.DolanKuy.dolankuyandroid.Preferences.Preferences;
import com.DolanKuy.dolankuyandroid.R;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class UserLocationFragment extends Fragment {

    private View view;
    private MaterialButton usrLoc_btn;
    private MaterialButton back_btn;
    private MaterialTextView tv_address;
    private MaterialTextView tv_lat;
    private MaterialTextView tv_long;
    private ProgressBar progressBar;
    private double latitude;
    private double longitude;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_location, container, false);

        usrLoc_btn = view.findViewById(R.id.btn_getUsrLoc);
        tv_address = view.findViewById(R.id.tv_usrAddr);
        tv_lat = view.findViewById(R.id.tv_lat);
        tv_long = view.findViewById(R.id.tv_long);
        progressBar = view.findViewById(R.id.pb_usr);
        back_btn = view.findViewById(R.id.btn_back);

        usrLoc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(
                        view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_CODE_LOCATION_PERMISSION
                    );
                } else {
                    getUsrLocation();


                }
            }
        });


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new ProfileFragment());
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getUsrLocation();
            } else {
                Toast.makeText(view.getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void getUsrLocation() {
        progressBar.setVisibility(View.VISIBLE);

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(getActivity())
                .requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(getActivity())
                                .removeLocationUpdates(this);
                        if (locationRequest != null && locationResult.getLocations().size() > 0 ) {
                            int latestLocationIndex = locationResult.getLocations().size() - 1;
                            latitude =
                                    locationResult.getLocations().get(latestLocationIndex).getLatitude();
                            longitude =
                                    locationResult.getLocations().get(latestLocationIndex).getLongitude();
                            tv_lat.setText(
                                    String.format(
                                            "Latitude : %s",
                                            latitude
                                    )
                            );

                            tv_long.setText(
                                    String.format(
                                            "Longitude : %s",
                                            longitude
                                    )
                            );

                            Preferences.setKeyLatitude(view.getContext(), String.valueOf(latitude)+"&"
                            +longitude);

                        }



                        progressBar.setVisibility(View.GONE);

                    }
                }, Looper.getMainLooper());



    }

}
