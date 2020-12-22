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

public class AdapterDataDashboard extends RecyclerView.Adapter<AdapterDataDashboard.HolderData> {
    private Context context;
    private List<DataModelDashboard> listWisataDashboard;

    public AdapterDataDashboard(Context context, List<DataModelDashboard> listWisataDashboard) {
        this.context = context;
        this.listWisataDashboard = listWisataDashboard;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_dashboard,parent,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataModelDashboard dmDashboard = listWisataDashboard.get(position);

        holder.tvId.setText(String.valueOf(dmDashboard.getId()));
        holder.tvName.setText(dmDashboard.getName());
        Picasso.get()
                .load("http://192.168.1.10/DolanKuy-backend/DolanKuy-backend/public/storage/dolankuy/"+ dmDashboard.getImage())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return listWisataDashboard.size();
    }

    public class HolderData extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvId;
        TextView tvName;
        Button detailBtn;
        ImageView imageView;

        @SuppressLint("ResourceType")
        public HolderData(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.idWisataDashboard);
            tvName = itemView.findViewById(R.id.nameWisataDashboard);
            detailBtn = itemView.findViewById(R.id.detailButton);
            imageView = itemView.findViewById(R.id.imageDashboard);

            detailBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String id = tvId.getText().toString();

            Intent intent = new Intent(context, DetailListLocationsActivity.class);
            //intent.putExtra("imageDetail", R.drawable.singapore);
            intent.putExtra("id", id);
            context.startActivity(intent);
        }
    }
}
