package com.example.calories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DishDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "calorie_counter.db";
    private static final int DATABASE_VERSION = 2; // Увеличили версию для добавления нового поля

    private static final String TABLE_DISHES = "dishes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_CALORIES = "calories";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_DESCRIPTION = "description";

    public DishDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_DISHES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_CALORIES + " INTEGER, " +
                COLUMN_CATEGORY + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_DISHES + " ADD COLUMN " + COLUMN_DESCRIPTION + " TEXT");
        }
    }

    public void addDish(Dish dish) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, dish.getName());
        values.put(COLUMN_CALORIES, dish.getCalories());
        values.put(COLUMN_CATEGORY, dish.getCategory());
        values.put(COLUMN_DESCRIPTION, dish.getDescription());
        db.insert(TABLE_DISHES, null, values);
        db.close();
    }

    public List<Dish> getAllDishes() {
        List<Dish> dishes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_DISHES, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                dishes.add(new Dish(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CALORIES)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dishes;
    }

    public void updateDish(Dish dish) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, dish.getName());
        values.put(COLUMN_CALORIES, dish.getCalories());
        values.put(COLUMN_CATEGORY, dish.getCategory());
        values.put(COLUMN_DESCRIPTION, dish.getDescription());
        db.update(TABLE_DISHES, values, COLUMN_ID + " = ?", new String[]{String.valueOf(dish.getId())});
        db.close();
    }

    public void deleteDish(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DISHES, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}