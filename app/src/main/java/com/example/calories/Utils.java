package com.example.calories;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class Utils {
    public static File exportDishesToJson(Context context, List<Dish> dishes) {
        JSONArray jsonArray = new JSONArray();

        try {
            for (Dish dish : dishes) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", dish.getId());
                jsonObject.put("name", dish.getName());
                jsonObject.put("calories", dish.getCalories());
                jsonObject.put("category", dish.getCategory());
                jsonArray.put(jsonObject);
            }

            File file = new File(context.getExternalFilesDir(null), "dishes.json");
            FileWriter writer = new FileWriter(file);
            writer.write(jsonArray.toString());
            writer.close();

            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}