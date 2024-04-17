package com.example.pcsuit;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.MyViewHolder> implements Filterable {

    private final Context context;
    private final ArrayList<Doc> listDoctors;
    private final ArrayList<Doc> filteredDoctorList;

    public DoctorAdapter(Context context, ArrayList<Doc> listDoctors) {
        this.context = context;
        this.listDoctors = listDoctors;
        this.filteredDoctorList = new ArrayList<>(listDoctors);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_doctor, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Doc docItem = filteredDoctorList.get(position);
        holder.name.setText(docItem.getName());
        holder.speciality.setText(docItem.getSpeciality());
        holder.email.setText(docItem.getEmail());

        holder.itemView.setOnClickListener(v -> {
            // Get the position of the clicked item
            int position1 = holder.getAdapterPosition();

            // Check if position is valid
            if (position1 != RecyclerView.NO_POSITION) {
                Doc selectedDoctor = filteredDoctorList.get(position1);
                String selectedDoctorId = selectedDoctor.getId();

                // Pass the doctor ID to AdminHomePage activity
                Intent intent = new Intent(context, AdminHomePage.class);
                intent.putExtra("selectedDoctorId", selectedDoctorId);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return filteredDoctorList.size();
    }

    // Implement Filterable interface methods
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filterPattern = constraint.toString().toLowerCase().trim();
                List<Doc> filteredList = new ArrayList<>();

                if (filterPattern.isEmpty()) {
                    filteredList.addAll(listDoctors);
                } else {
                    for (Doc data : listDoctors) {
                        if (data.getName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(data);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredDoctorList.clear();
                filteredDoctorList.addAll((List<Doc>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, speciality, email;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.doctor_name_text_view);
            speciality = itemView.findViewById(R.id.specialty_text_view);
            email = itemView.findViewById(R.id.email_text_view);
        }
    }
}
