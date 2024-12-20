package com.example.calories;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class Utils {
    private static final String TAG = "DishExport";

    public static File exportDishesToJson(Context context, List<Dish> dishes) {
        JSONArray jsonArray = new JSONArray();

        try {
            for (Dish dish : dishes) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", dish.getId());
                jsonObject.put("name", dish.getName());
                jsonObject.put("calories", dish.getCalories());
                jsonObject.put("category", dish.getCategory());
                jsonObject.put("description", dish.getDescription());
                jsonArray.put(jsonObject);
            }

            // Преобразуем JSON-объект в строку
            String jsonString = jsonArray.toString(4); // 4 — для форматирования с отступами
            Log.d(TAG, "Exported JSON:\n" + jsonString); // Вывод JSON в консоль

            // Записываем JSON в файл
            File file = new File(context.getExternalFilesDir(null), "dishes.json");
            FileWriter writer = new FileWriter(file);
            writer.write(jsonString);
            writer.close();

            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
// eee