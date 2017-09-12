package ru.artempugachev.bakingapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Recipe class. Contains recipe info, ingredients and steps for how to prepare it
 */

public final class Recipe {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("ingredients")
    private List<Ingredient> ingredients;

    @SerializedName("steps")
    private List<Step> steps;

    @SerializedName("servings")
    private int servings;

    @SerializedName("image")
    private String imageUrl;
}
