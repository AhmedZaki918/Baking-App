package com.example.android.bakingapp.data.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Recipe implements Parcelable {


    /**
     * Initialize the variables
     */
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ingredients")
    @Expose
    private List<Ingredient> ingredients;
    @SerializedName("steps")
    @Expose
    private List<Step> steps;


    /**
     * Constructor used for parcel
     */
    protected Recipe(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        steps = in.createTypedArrayList(Step.CREATOR);
    }


    /**
     * Write object values to parcel for storage
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
    }


    /**
     * Return hashcode of object
     */
    @Override
    public int describeContents() {
        return 0;
    }


    /**
     * Used when un-parceling our parcel (creating the object)
     */
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


    /**
     * Getter & Setter
     */
    // Get the steps
    public List<Step> getSteps() {
        return steps;
    }

    // Get the recipe name
    public String getName() {
        return name;
    }

    // Set the recipe name
    public void setName(String name) {
        this.name = name;
    }

    // Get the ingredients of the recipe
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    // Get the Id of the recipe
    public Integer getId() {
        return id;
    }

    // Set the Id of the recipe
    public void setId(Integer id) {
        this.id = id;
    }
}