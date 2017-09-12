package ru.artempugachev.bakingapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Ingredient for recipe.
 */

public final class Ingredient {
    @SerializedName("quantity")
    private double quantity;

    @SerializedName("measure")
    private String measureUnit;

    @SerializedName("ingredient")
    private String name;
}
