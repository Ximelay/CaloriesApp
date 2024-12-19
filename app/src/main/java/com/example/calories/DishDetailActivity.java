package com.example.calories;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DishDetailActivity extends AppCompatActivity {
    private DishDatabaseHelper dbHelper;
    private Dish currentDish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_detail);

        dbHelper = new DishDatabaseHelper(this);

        int dishId = getIntent().getIntExtra("dishId", -1);
        if (dishId == -1) {
            Toast.makeText(this, "Ошибка при загрузке блюда", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        currentDish = dbHelper.getAllDishes().stream()
                .filter(dish -> dish.getId() == dishId)
                .findFirst()
                .orElse(null);

        if (currentDish == null) {
            Toast.makeText(this, "Блюдо не найдено", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        EditText nameEditText = findViewById(R.id.dish_name);
        EditText caloriesEditText = findViewById(R.id.dish_calories);
        EditText descriptionEditText = findViewById(R.id.dish_description); // Новое поле
        Spinner categorySpinner = findViewById(R.id.dish_category);
        Button updateButton = findViewById(R.id.update_button);
        Button deleteButton = findViewById(R.id.delete_button);

        nameEditText.setText(currentDish.getName());
        caloriesEditText.setText(String.valueOf(currentDish.getCalories()));
        descriptionEditText.setText(currentDish.getDescription()); // Установить описание

        updateButton.setOnClickListener(v -> {
            currentDish.setName(nameEditText.getText().toString());
            currentDish.setCalories(Integer.parseInt(caloriesEditText.getText().toString()));
            currentDish.setCategory(categorySpinner.getSelectedItem().toString());
            currentDish.setDescription(descriptionEditText.getText().toString()); // Обновить описание

            dbHelper.updateDish(currentDish);
            Toast.makeText(this, "Блюдо обновлено!", Toast.LENGTH_SHORT).show();
            finish();
        });

        deleteButton.setOnClickListener(v -> {
            dbHelper.deleteDish(currentDish.getId());
            Toast.makeText(this, "Блюдо удалено!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}