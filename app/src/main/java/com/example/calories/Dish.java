package com.example.calories;


public class Dish {
    private int id;
    private String name;
    private int calories;
    private String category;

    public Dish(int id, String name, int calories, String category) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.category = category;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getCalories() { return calories; }
    public String getCategory() { return category; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCalories(int calories) { this.calories = calories; }
    public void setCategory(String category) { this.category = category; }
}
