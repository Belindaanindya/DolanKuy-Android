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

public class AdapterListAcomodation extends RecyclerView.Adapter<AdapterListAcomodation.HolderData> implements Filterable {
    private Context context;
    private List<DataModelDashboard> listAcomodation;
    private  List<DataModelDashboard> listLocationsSearch;

    public AdapterListAcomodation(Context context, List<DataModelDashboard> listAcomodation) {
        this.context = context;
        this.listAcomodation = listAcomodation;
        this.listLocationsSearch = new ArrayList<>(this.listAcomodation);
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
        holder.tv_distance.setText(String.valueOf(new DecimalFormat("##.##").format(dmDashboard.getDistance()))+ " Km");
        Picasso.get()
                .load("http://192.168.1.7/DolanKuy/DolanKuy-backend/public/storage/dolankuy/"+ dmDashboard.getImage())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return listAcomodation.size();
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
            listAcomodation.clear();
            listAcomodation.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class HolderData extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nameListAcomodation;
        private TextView idListAcomodation;
        private ImageView imageView;
        private TextView tv_distance;


        @SuppressLint("ResourceType")
        public HolderData(@NonNull View itemView) {
            super(itemView);

            idListAcomodation = itemView.findViewById(R.id.idAkomodasi);
            nameListAcomodation = itemView.findViewById(R.id.nameAkomodasi);
            imageView = itemView.findViewById(R.id.imageAkomodasi);
            tv_distance = itemView.findViewById(R.id.distance_akomodasi);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String id = idListAcomodation.getText().toString();

            Intent intent = new Intent(context, DetailListLocationsActivity.class);
            intent.putExtra("imageDetail", R.drawable.sate);
            intent.putExtra("distance", tv_distance.getText());
            intent.putExtra("id", id);
            context.startActivity(intent);
        }
    }
}
