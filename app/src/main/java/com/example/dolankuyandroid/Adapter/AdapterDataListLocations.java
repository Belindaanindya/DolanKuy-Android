package com.example.dolankuyandroid.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dolankuyandroid.Activity.DetailListLocationsActivity;
import com.example.dolankuyandroid.Model.DataModelDashboard;
import com.example.dolankuyandroid.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AdapterDataListLocations extends RecyclerView.Adapter<AdapterDataListLocations.HolderData> implements Filterable {
    private Context context;
    private List<DataModelDashboard> listLocations;
    private  List<DataModelDashboard> listLocationsSearch;

    public AdapterDataListLocations(Context context, List<DataModelDashboard> listLocations) {
        this.context = context;
        this.listLocations = listLocations;
        this.listLocationsSearch = new ArrayList<>(this.listLocations);
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_wisata,parent,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataModelDashboard dmDashboard = listLocations.get(position);

        float distance = (float) dmDashboard.getDistance();

        holder.idListWisata1.setText(String.valueOf(dmDashboard.getId()));
        holder.nameListWisata1.setText(dmDashboard.getName());
        holder.distanceListWisata1.setText(" "+ String.valueOf(new DecimalFormat("##.##").format(distance)) + " Km ");
        holder.locationListWisataBtn1.setText(dmDashboard.getAddress());
        Picasso.get()
                .load("http://192.168.1.5/DolanKuy/DolanKuy-backend/public/storage/dolankuy/"+ dmDashboard.getImage())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return listLocations.size();
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    private Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<DataModelDashboard> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(listLocationsSearch);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (DataModelDashboard exampleItem : listLocationsSearch) {
                    if (exampleItem.getName().toLowerCase().contains(filterPattern)||
                        exampleItem.getAddress().toLowerCase().contains(filterPattern)){

                        filteredList.add(exampleItem);

                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listLocations.clear();
            listLocations.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class HolderData extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView nameListWisata1;
        TextView distanceListWisata1;
        TextView idListWisata1;
        Button locationListWisataBtn1;
        ImageView imageView;


        @SuppressLint("ResourceType")
        public HolderData(@NonNull View itemView) {
            super(itemView);

            idListWisata1 = itemView.findViewById(R.id.idListWisata1);
            nameListWisata1 = itemView.findViewById(R.id.nameListWisata1);
            distanceListWisata1 = itemView.findViewById(R.id.distanceListWisata1);
            locationListWisataBtn1 = itemView.findViewById(R.id.locationListWisataBtn1);
            imageView = itemView.findViewById(R.id.imgListWisata1);

            locationListWisataBtn1.setOnClickListener(this);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String id = idListWisata1.getText().toString();
            String distance = distanceListWisata1.getText().toString();

            Intent intent = new Intent(context, DetailListLocationsActivity.class);
            intent.putExtra("imageDetail", R.drawable.list_wisata_picture);
            intent.putExtra("id", id);
            intent.putExtra("distance", distance);
            context.startActivity(intent);
        }



    }
}
