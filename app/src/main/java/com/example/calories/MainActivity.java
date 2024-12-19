package com.example.calories;

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

public class MainActivity extends AppCompatActivity {
    private DishDatabaseHelper dbHelper;
    private DishAdapter dishAdapter;
    private ListView listView;
    private TextView emptyTextView;
    private Button addDishButtonTop;
    private Button addDishButtonBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DishDatabaseHelper(this);

        // Инициализация UI элементов
        listView = findViewById(R.id.dish_list);
        emptyTextView = findViewById(R.id.empty_view);
        addDishButtonTop = findViewById(R.id.add_dish_button);
        addDishButtonBottom = findViewById(R.id.add_dish_button_bottom);

        // Обработчик верхней кнопки "Добавить блюдо"
        addDishButtonTop.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddDishActivity.class));
        });

        // Обработчик нижней кнопки "Добавить блюдо"
        addDishButtonBottom.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddDishActivity.class));
        });

        updateDishList();
    }

    private void updateDishList() {
        List<Dish> dishes = dbHelper.getAllDishes();

        if (dishes.isEmpty()) {
            listView.setVisibility(ListView.GONE);
            emptyTextView.setVisibility(TextView.VISIBLE);
            addDishButtonTop.setVisibility(Button.VISIBLE);
        } else {
            listView.setVisibility(ListView.VISIBLE);
            emptyTextView.setVisibility(TextView.GONE);
            addDishButtonTop.setVisibility(Button.GONE);

            dishAdapter = new DishAdapter(this, dishes);
            listView.setAdapter(dishAdapter);

            listView.setOnItemClickListener((adapterView, view, i, l) -> {
                Dish selectedDish = (Dish) dishAdapter.getItem(i);
                Intent intent = new Intent(MainActivity.this, DishDetailActivity.class);
                intent.putExtra("dishId", selectedDish.getId());
                startActivity(intent);
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDishList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_dish) {
            startActivity(new Intent(this, AddDishActivity.class));
            return true;
        } else if (item.getItemId() == R.id.export_json) {
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
}