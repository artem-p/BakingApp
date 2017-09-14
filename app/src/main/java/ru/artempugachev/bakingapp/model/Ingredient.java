package ru.artempugachev.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Ingredient for recipe.
 */

public final class Ingredient implements Parcelable {
    @SerializedName("quantity")
    private double quantity;

    @SerializedName("measure")
    private String measureUnit;

    @SerializedName("ingredient")
    private String name;

    private Ingredient(Parcel in) {
        quantity = in.readDouble();
        measureUnit = in.readString();
        name = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(quantity);
        dest.writeString(measureUnit);
        dest.writeString(name);
    }
}
