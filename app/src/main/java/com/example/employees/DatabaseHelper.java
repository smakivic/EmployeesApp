package com.example.employees;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME="EmployeesDB.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "employees";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_AGE = "age";
    private static final String COLUMN_GENDER = "gender";
    DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_FIRST_NAME + " TEXT, " +
                        COLUMN_LAST_NAME + " TEXT, " +
                        COLUMN_AGE + " INTEGER, " +
                        COLUMN_GENDER + " TEXT CHECK (gender IN ('male', 'female')));";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addEmployee(String first_name, String last_name, int age, String gender){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FIRST_NAME, first_name);
        cv.put(COLUMN_LAST_NAME, last_name);
        cv.put(COLUMN_AGE, age);
        cv.put(COLUMN_GENDER, gender.toLowerCase());
        long result = db.insertOrThrow(TABLE_NAME, null, cv);
        if (result == -1){
            Toast.makeText(context, "Failed :(", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    boolean updateData(String id, String first_name, String last_name, int age, String gender){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FIRST_NAME, first_name);
        cv.put(COLUMN_LAST_NAME, last_name);
        cv.put(COLUMN_AGE, age);
        cv.put(COLUMN_GENDER, gender.toLowerCase());
        long result = db.update(TABLE_NAME, cv, " _id=?", new String[]{id});
        if (result == -1){
            Toast.makeText(context, "Failed to update :(", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            Toast.makeText(context, "Updated successfully!", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
    void deleteOneRow(String id){
        SQLiteDatabase db = getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{id});
        if (result == -1){
            Toast.makeText(context, "Failed :(", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Deleted successfully!", Toast.LENGTH_SHORT).show();
        }
    }
    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);

    }
}
