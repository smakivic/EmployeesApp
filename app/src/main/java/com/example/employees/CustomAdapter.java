package com.example.employees;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{

    private Context context;
    Activity activity;
    private ArrayList employee_id, employee_firstName, employee_lastName, employee_age, employee_gender;
    CustomAdapter(Activity activity, Context context, ArrayList employee_id, ArrayList employee_firstName, ArrayList employee_lastName, ArrayList employee_age, ArrayList employee_gender){
        this.activity = activity;
        this.context = context;
        this.employee_id = employee_id;
        this.employee_firstName = employee_firstName;
        this.employee_lastName = employee_lastName;
        this.employee_age = employee_age;
        this.employee_gender = employee_gender;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.id_txt.setText(String.valueOf(employee_id.get(position)));
        holder.firstName_txt.setText(String.valueOf(employee_firstName.get(position)));
        holder.lastName_txt.setText(String.valueOf(employee_lastName.get(position)));
        holder.age_txt.setText(String.valueOf(employee_age.get(position)));
        holder.gender_txt.setText(String.valueOf(employee_gender.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("employee_id", String.valueOf(employee_id.get(position)));
                intent.putExtra("employee_firstName", String.valueOf(employee_firstName.get(position)));
                intent.putExtra("employee_lastName", String.valueOf(employee_lastName.get(position)));
                intent.putExtra("employee_age", String.valueOf(employee_age.get(position)));
                intent.putExtra("employee_gender", String.valueOf(employee_gender.get(position)));
                activity.startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return employee_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id_txt, firstName_txt, lastName_txt, age_txt, gender_txt;
        LinearLayoutCompat mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id_txt = itemView.findViewById(R.id.id_txt);
            firstName_txt = itemView.findViewById(R.id.firstName_txt);
            lastName_txt = itemView.findViewById(R.id.lastName_txt);
            age_txt = itemView.findViewById(R.id.age_txt);
            gender_txt = itemView.findViewById(R.id.gender_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
