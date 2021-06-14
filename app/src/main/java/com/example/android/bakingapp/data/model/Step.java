package com.example.android.bakingapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Step implements Parcelable {


    /**
     * Initialize the variables
     */
    @SerializedName("shortDescription")
    @Expose
    private String shortDescription;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("videoURL")
    @Expose
    private String videoURL;
    @SerializedName("id")
    @Expose
    private String id;


    /**
     * Constructor used for parcel
     */
    private Step(Parcel in) {
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        id = in.readString();
    }


    /**
     * Used when un-parceling our parcel (creating the object)
     */
    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };


    /**
     * Return hashcode of object
     */
    @Override
    public int describeContents() {
        return 0;
    }


    /**
     * Write object values to parcel for storage
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(id);
    }


    /**
     * Getters
     */
    // Get short description for the step
    public String getShortDescription() {
        return shortDescription;
    }

    // Get description for the step
    public String getDescription() {
        return description;
    }

    // Get video url for the step
    public String getVideoURL() {
        return videoURL;
    }

    // Get the id
    public String getId() {
        return id;
    }
}