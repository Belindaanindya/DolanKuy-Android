package com.example.dolankuyandroid.Activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.dolankuyandroid.Fragment.ListLocationsFragment;
import com.example.dolankuyandroid.R;

public class ListLocationsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_bottom);


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.botNav_container, new ListLocationsFragment());
        fragmentTransaction.commit();

        TextView textView = findViewById(R.id.title);
        textView.setText("List Wisata");


    }


}