package com.example.pcsuit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.MyViewHolder> {

    private final Context context;
    private final ArrayList<Doc> doctorList;



    public DoctorAdapter(Context context, ArrayList<Doc> doctorList) {
        this.context = context;
        this.doctorList = doctorList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_doctor, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Doc doclist = doctorList.get(position);
        holder.doctorNameTextView.setText(doclist.getName());
        holder.specialtyTextView.setText(doclist.getSpeciality());
        holder.doctorEmailTextView.setText(doclist.getEmail());
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView doctorNameTextView;
        private final TextView specialtyTextView;
        private final TextView doctorEmailTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorNameTextView = itemView.findViewById(R.id.doctor_name_text_view);
            specialtyTextView = itemView.findViewById(R.id.specialty_text_view);
            doctorEmailTextView = itemView.findViewById(R.id.email_text_view);
        }

        public void bind(Doc doc) {
            doctorNameTextView.setText(doc.getName());
            specialtyTextView.setText(doc.getSpeciality());
            doctorEmailTextView.setText(doc.getEmail());
        }
    }
}
