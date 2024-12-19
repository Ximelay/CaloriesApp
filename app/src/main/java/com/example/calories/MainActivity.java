package com.example.calories;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private DishDatabaseHelper dbHelper;
    private DishAdapter dishAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DishDatabaseHelper(this);

        listView = findViewById(R.id.dish_list);

        updateDishList();
    }

    private void updateDishList() {
        List<Dish> dishes = dbHelper.getAllDishes();
        dishAdapter = new DishAdapter(this, dishes);
        listView.setAdapter(dishAdapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Dish selectedDish = (Dish) dishAdapter.getItem(i);
            Intent intent = new Intent(MainActivity.this, DishDetailActivity.class);
            intent.putExtra("dishId", selectedDish.getId());
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDishList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu); // Подключение меню
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.add_dish) {// Открыть экран добавления блюда
            startActivity(new Intent(this, AddDishActivity.class));
            return true;
        } else if (itemId == R.id.filter_category) {// Открыть фильтр по категориям
            showCategoryFilterDialog();
            return true;
        } else if (itemId == R.id.export_json) {// Экспорт всех данных в JSON
            List<Dish> dishes = dbHelper.getAllDishes();
            File jsonFile = Utils.exportDishesToJson(this, dishes);
            if (jsonFile != null) {
                Toast.makeText(this, "Данные экспортированы: " + jsonFile.getPath(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Ошибка экспорта данных", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Метод для отображения диалога с выбором категории
    private void showCategoryFilterDialog() {
        String[] categories = getResources().getStringArray(R.array.categories);

        new AlertDialog.Builder(this)
                .setTitle("Выберите категорию")
                .setItems(categories, (dialog, which) -> {
                    String selectedCategory = categories[which];
                    filterDishesByCategory(selectedCategory);
                })
                .setNegativeButton("Отмена", null)
                .show();
    }

    // Метод для фильтрации списка блюд по категории
    private void filterDishesByCategory(String category) {
        List<Dish> filteredDishes = dbHelper.getAllDishes().stream()
                .filter(dish -> dish.getCategory().equals(category))
                .collect(Collectors.toList());

        if (filteredDishes.isEmpty()) {
            Toast.makeText(this, "Нет блюд в категории: " + category, Toast.LENGTH_SHORT).show();
        } else {
            dishAdapter = new DishAdapter(this, filteredDishes);
            listView.setAdapter(dishAdapter);
        }
    }
}