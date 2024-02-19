package com.example.employees;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText firstName_edit, lastName_edit, age_edit;
    Spinner gender_edit;
    Button editEmployee_button, deleteEmployee_button;
    String id, firstName, lastName, age, gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        firstName_edit = findViewById(R.id.firstName_edit);
        lastName_edit = findViewById(R.id.lastName_edit);
        age_edit = findViewById(R.id.age_edit);
        gender_edit = findViewById(R.id.gender_edit);
        editEmployee_button = findViewById(R.id.editEmployee_button);
        deleteEmployee_button = findViewById(R.id.deleteEmployee_button);
        getAndSetIntentData();
        editEmployee_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(UpdateActivity.this);
                boolean isUpdated = db.updateData(id, firstName_edit.getText().toString().trim(),
                        lastName_edit.getText().toString().trim(),
                        Integer.parseInt(age_edit.getText().toString().trim()),
                        gender_edit.getSelectedItem().toString().trim());
                if (isUpdated) {
                    Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        deleteEmployee_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
    }
    void getAndSetIntentData(){
        if(getIntent().hasExtra("employee_id") &&
                getIntent().hasExtra("employee_firstName") &&
                getIntent().hasExtra("employee_lastName") &&
                getIntent().hasExtra("employee_age") &&
                getIntent().hasExtra("employee_gender")){
            //getting data from intent
            id = getIntent().getStringExtra("employee_id");
            firstName = getIntent().getStringExtra("employee_firstName");
            lastName = getIntent().getStringExtra("employee_lastName");
            age = getIntent().getStringExtra("employee_age");
            gender = getIntent().getStringExtra("employee_gender");

            //setting intent data
            firstName_edit.setText(firstName);
            lastName_edit.setText(lastName);
            age_edit.setText(age);
            selectGenderInSpinner(gender);
        }else{
            Toast.makeText(this, "No data!", Toast.LENGTH_SHORT).show();
        }
    }
    private void selectGenderInSpinner(String gender) {
        // get the position of the gender in the array of gender options
        int position = -1;
        String[] genderOptions = getResources().getStringArray(R.array.gender_options);
        for (int i = 0; i < genderOptions.length; i++) {
            if (genderOptions[i].toLowerCase().equals(gender)) {
                position = i;
                break;
            }
        }
        // set the selected item of the Spinner to the position
        if (position != -1) {
            gender_edit.setSelection(position);
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + firstName + " " + lastName +"?");
        builder.setMessage("Are you sure you want to delete this employee?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseHelper db = new DatabaseHelper(UpdateActivity.this);
                db.deleteOneRow(id);
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
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