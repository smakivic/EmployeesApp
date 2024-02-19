package com.example.employees;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ImageView noData_image;
    TextView noData_text;
    DatabaseHelper db;
    ArrayList<String> employee_id, employee_firstName, employee_lastName, employee_age, employee_gender;
    CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        noData_text = findViewById(R.id.noData_txt);
        noData_image = findViewById(R.id.empty_icon);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
        db = new DatabaseHelper(MainActivity.this);
        employee_id = new ArrayList<>();
        employee_firstName = new ArrayList<>();
        employee_lastName = new ArrayList<>();
        employee_age = new ArrayList<>();
        employee_gender = new ArrayList<>();

        storeDataToArrays();

        customAdapter = new CustomAdapter(MainActivity.this,this,employee_id,employee_firstName,employee_lastName,employee_age,employee_gender);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1)
        {
            recreate();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Refresh data when activity is resumed
        storeDataToArrays();
        customAdapter.notifyDataSetChanged(); // Notify the adapter that data has changed
    }
    void storeDataToArrays(){
        employee_id.clear();
        employee_firstName.clear();
        employee_lastName.clear();
        employee_age.clear();
        employee_gender.clear();
        Cursor cursor = db.readAllData();
        if(cursor.getCount() == 0){
            noData_text.setVisibility(View.VISIBLE);
            noData_image.setVisibility(View.VISIBLE);
        }else{
            while(cursor.moveToNext()){
                employee_id.add(cursor.getString(0));
                employee_firstName.add(cursor.getString(1));
                employee_lastName.add(cursor.getString(2));
                employee_age.add(cursor.getString(3));
                employee_gender.add(cursor.getString(4));
            }
            noData_text.setVisibility(View.GONE);
            noData_image.setVisibility(View.GONE);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            confirmDialog();
        }
        if(item.getItemId() == R.id.statistics){
            Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete all employees?");
        builder.setMessage("Are you sure you want to delete all employees?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseHelper db = new DatabaseHelper(MainActivity.this);
                db.deleteAllData();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

}