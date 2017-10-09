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


    /**
     * As text string
     * Name (quantity measure)
     * */
    public String asText() {
        String quantityStr;

        // we don't want to display decimal digits for int values (6.0 TBSP)
        // so check if quantity is int (check rounded-down value of quantity is the same as quantity)
        if (Math.floor(quantity) == quantity) {
            // quantity can be display as int
            quantityStr = String.valueOf(Math.round(quantity));
        } else {
            // quantity should be double
            quantityStr = String.valueOf(quantity);
        }

        return name + " (" + quantityStr + " " + measureUnit + ")";
    }

    /**
     * Parcelable methods
     * */
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
