package com.example.pcsuit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.MyViewHolder> implements Filterable {

    private final Context context;
    private final ArrayList<HospitalData> hospitalData;
    private final List<HospitalData> filteredHospitalDataList;
    private final HospitalClickListener listener;

    public interface HospitalClickListener {
        void onHospitalClicked(String selectedHospitalId); // Method to pass selected hospital ID
    }

    public HospitalAdapter(Context context, ArrayList<HospitalData> hospitalData, HospitalClickListener listener) {
        this.context = context;
        this.hospitalData = hospitalData;
        this.filteredHospitalDataList = new ArrayList<>(hospitalData);
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HospitalData hospitalDataItem = filteredHospitalDataList.get(position);
        holder.name.setText(hospitalDataItem.getName());
        holder.location.setText(hospitalDataItem.getLocation());
        holder.emergenceContact.setText(hospitalDataItem.getEmergenceContact());
        holder.description.setText(hospitalDataItem.getDescription());

        holder.itemView.setOnClickListener(v -> {
            int position1 = holder.getAdapterPosition();
            if (position1 != RecyclerView.NO_POSITION) {
                HospitalData selectedHospital = filteredHospitalDataList.get(position1);
                String selectedHospitalId = selectedHospital.getId();
                listener.onHospitalClicked(selectedHospitalId); // Call the callback method with ID
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredHospitalDataList.size();
    }

    // Implement Filterable interface methods
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filterPattern = constraint.toString().toLowerCase().trim();
                List<HospitalData> filteredList = new ArrayList<>();

                if (filterPattern.isEmpty()) {
                    filteredList.addAll(hospitalData);
                } else {
                    for (HospitalData data : hospitalData) {
                        if (data.getName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(data);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                results.count = filteredList.size(); // Make sure to set the count
                return results;
            }


            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredHospitalDataList.clear();
                filteredHospitalDataList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, location, emergenceContact, description;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            location = itemView.findViewById(R.id.location);
            emergenceContact = itemView.findViewById(R.id.emergencyContact);
            description = itemView.findViewById(R.id.briefDescription);
        }
    }
}
