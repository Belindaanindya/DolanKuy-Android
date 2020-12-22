package com.example.dolankuyandroid.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dolankuyandroid.Activity.DetailListLocationsActivity;
import com.example.dolankuyandroid.Model.DataModelDashboard;
import com.example.dolankuyandroid.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterListAcomodation extends RecyclerView.Adapter<AdapterListAcomodation.HolderData> {
    private Context context;
    private List<DataModelDashboard> listAcomodation;

    public AdapterListAcomodation(Context context, List<DataModelDashboard> listAcomodation) {
        this.context = context;
        this.listAcomodation = listAcomodation;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_akomodasi,parent,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataModelDashboard dmDashboard = listAcomodation.get(position);

        holder.idListAcomodation.setText(String.valueOf(dmDashboard.getId()));
        holder.nameListAcomodation.setText(dmDashboard.getName());
        Picasso.get()
                .load("http://192.168.1.10/DolanKuy-backend/DolanKuy-backend/public/storage/dolankuy/"+ dmDashboard.getImage())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return listAcomodation.size();
    }

    public class HolderData extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView nameListAcomodation;
        TextView idListAcomodation;
        ImageView imageView;


        @SuppressLint("ResourceType")
        public HolderData(@NonNull View itemView) {
            super(itemView);

            idListAcomodation = itemView.findViewById(R.id.idAkomodasi);
            nameListAcomodation = itemView.findViewById(R.id.nameAkomodasi);
            imageView = itemView.findViewById(R.id.imageAkomodasi);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String id = idListAcomodation.getText().toString();

            Intent intent = new Intent(context, DetailListLocationsActivity.class);
            intent.putExtra("imageDetail", R.drawable.sate);
            intent.putExtra("id", id);
            context.startActivity(intent);
        }
    }
}
