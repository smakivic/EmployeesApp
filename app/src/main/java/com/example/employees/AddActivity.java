package com.example.employees;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddActivity extends AppCompatActivity {

    EditText firstName_input, lastName_input, age_input;
    Spinner gender_select;
    Button addEmployee_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        firstName_input = findViewById(R.id.firstName_edit);
        lastName_input = findViewById(R.id.lastName_edit);
        age_input = findViewById(R.id.age_edit);
        gender_select = findViewById(R.id.gender_edit);
        addEmployee_button = findViewById(R.id.editEmployee_button);
        addEmployee_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(AddActivity.this);
                db.addEmployee(firstName_input.getText().toString().trim(),
                        lastName_input.getText().toString().trim(),
                        Integer.parseInt(age_input.getText().toString().trim()),
                        gender_select.getSelectedItem().toString().trim());

                firstName_input.clearFocus();
                lastName_input.clearFocus();
                age_input.clearFocus();

                firstName_input.setText("");
                lastName_input.setText("");
                age_input.setText("");


                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }

        });
    }
}