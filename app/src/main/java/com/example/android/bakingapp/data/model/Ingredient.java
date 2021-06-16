package com.example.android.bakingapp.data.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ingredient implements Parcelable {


    // Initialization
    @SerializedName("quantity")
    @Expose
    private final String quantity;
    @SerializedName("measure")
    @Expose
    private final String measure;
    @SerializedName("ingredient")
    @Expose
    private final String ingredient;


    // Constructor used for parcel
    private Ingredient(Parcel in) {
        quantity = in.readString();
        measure = in.readString();
        ingredient = in.readString();
    }


    // Write object values to parcel for storage
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }


    // Return hashcode of object
    @Override
    public int describeContents() {
        return 0;
    }


    //  Used when un-parceling our parcel (creating the object)
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


    //  Getter methods
    public String getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }
}