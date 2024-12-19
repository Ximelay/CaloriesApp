package com.example.calories;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


public class AddDishActivity extends AppCompatActivity {
    private DishDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);

        dbHelper = new DishDatabaseHelper(this);

        EditText nameEditText = findViewById(R.id.dish_name);
        EditText caloriesEditText = findViewById(R.id.dish_calories);
        EditText descriptionEditText = findViewById(R.id.dish_description); // Новое поле
        Spinner categorySpinner = findViewById(R.id.dish_category);
        Button saveButton = findViewById(R.id.save_button);

        saveButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString();
            String calories = caloriesEditText.getText().toString();
            String description = descriptionEditText.getText().toString(); // Получение описания
            String category = categorySpinner.getSelectedItem().toString();

            if (name.isEmpty() || calories.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_SHORT).show();
                return;
            }

            dbHelper.addDish(new Dish(0, name, Integer.parseInt(calories), category, description));
            Toast.makeText(this, "Блюдо добавлено!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}