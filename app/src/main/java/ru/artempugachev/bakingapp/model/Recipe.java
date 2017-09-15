package ru.artempugachev.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Recipe class. Contains recipe info, ingredients and steps for how to prepare it
 */

public final class Recipe implements Parcelable {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("ingredients")
    private List<Ingredient> ingredients = new ArrayList<Ingredient>();

    @SerializedName("steps")
    private List<Step> steps = new ArrayList<Step>();

    @SerializedName("servings")
    private int servings;

    @SerializedName("image")
    private String imageUrl;

    private Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        in.readTypedList(ingredients, Ingredient.CREATOR);
        in.readTypedList(steps, Step.CREATOR);
        servings = in.readInt();
        imageUrl = in.readString();
    }


    /**
     * Returns ingredients list as text with bullets and new lines
     * Use it to fill ingredients in recipe details
     * */
    public String toIngredientsText() {
        String ingredientsText = "";

        if (ingredients != null && !ingredients.isEmpty()) {
            for (Ingredient ingredient : ingredients) {
                String ingredientItem = "\u2022 " + ingredient.asText() + "\n";
                ingredientsText += ingredientItem;
            }
        }

        return ingredientsText;
    }


    /**
     * Parcelable methods
     * */
    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
        dest.writeInt(servings);
        dest.writeString(imageUrl);
    }
}
