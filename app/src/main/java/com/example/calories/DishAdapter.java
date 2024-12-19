package com.example.calories;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DishAdapter extends android.widget.ArrayAdapter<Dish> {
    private Context context;
    private List<Dish> dishes;

    public DishAdapter(Context context, List<Dish> dishes) {
        super(context, R.layout.dish_item, dishes);
        this.context = context;
        this.dishes = dishes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.dish_item, parent, false);
        }

        Dish currentDish = dishes.get(position);

        TextView nameTextView = convertView.findViewById(R.id.dish_name);
        TextView caloriesTextView = convertView.findViewById(R.id.dish_calories);

        nameTextView.setText(currentDish.getName());
        caloriesTextView.setText(context.getString(R.string.calories_format, currentDish.getCalories()));

        return convertView;
    }
}
