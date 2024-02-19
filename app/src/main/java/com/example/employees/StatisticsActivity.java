package com.example.employees;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import java.util.ArrayList;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;


public class StatisticsActivity extends AppCompatActivity {
    DatabaseHelper db;
    PieChart chart;
    TextView malePercentage, femalePercentage, avgAge;
    ArrayList<String> employee_id, employee_firstName, employee_lastName, employee_age, employee_gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        db = new DatabaseHelper(StatisticsActivity.this);
        employee_id = new ArrayList<>();
        employee_firstName = new ArrayList<>();
        employee_lastName = new ArrayList<>();
        employee_age = new ArrayList<>();
        employee_gender = new ArrayList<>();
        storeDataToArrays();
        double avg_age = 0;
        double males = 0, females = 0;
        if (employee_age.size() > 0) {
            for (String age : employee_age) {
                avg_age += Integer.parseInt(age);
            }
            avg_age = avg_age / employee_age.size();

            for(String gender:employee_gender){
                if(gender.equals("male")){
                    males++;
                }
                else if(gender.equals("female")){
                    females++;
                }
            }
        }
        avg_age = round(avg_age,1);
        double totalEmployees = employee_gender.size();
        males =round(males / totalEmployees,2);
        females = round(females / totalEmployees,2);

        malePercentage = findViewById(R.id.percentageLabel_txt);
        avgAge = findViewById(R.id.avgAge_txt);
        avgAge.setText(avgAge.getText().toString() + String.valueOf(avg_age));

        float malesPercent = (float) (males * 100);
        float femalesPercent = (float) (females * 100);

        // Create pie chart entries
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(malesPercent , "Male"));
        entries.add(new PieEntry(femalesPercent, "Female"));


        // Create a dataset with entries
        PieDataSet dataSet = new PieDataSet(entries,"");
        dataSet.setColors(new int[]{R.color.blue, R.color.pink}, this);
        dataSet.setValueTextSize(16f);
        dataSet.setValueTextColor(Color.WHITE);

        // Create a data object from the dataset
        PieData pieData = new PieData(dataSet);
        pieData.setValueFormatter(new PercentFormatter());

        // Get a reference to the PieChart view
        PieChart chart = findViewById(R.id.chart);
        chart.setData(pieData);
        chart.getDescription().setText("");
        chart.animateY(1000);
        chart.invalidate(); // Refresh chart
    }
    void storeDataToArrays(){
        employee_id.clear();
        employee_firstName.clear();
        employee_lastName.clear();
        employee_age.clear();
        employee_gender.clear();
        Cursor cursor = db.readAllData();
        while(cursor.moveToNext()){
            employee_id.add(cursor.getString(0));
            employee_firstName.add(cursor.getString(1));
            employee_lastName.add(cursor.getString(2));
            employee_age.add(cursor.getString(3));
            employee_gender.add(cursor.getString(4));
        }
    }
    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}