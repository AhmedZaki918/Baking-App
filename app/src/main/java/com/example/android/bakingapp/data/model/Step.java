package com.example.android.bakingapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Step implements Parcelable {


    // Initialization
    @SerializedName("shortDescription")
    @Expose
    private final String shortDescription;
    @SerializedName("description")
    @Expose
    private final String description;
    @SerializedName("videoURL")
    @Expose
    private final String videoURL;
    @SerializedName("id")
    @Expose
    private final String id;


    // Constructor used for parcel
    private Step(Parcel in) {
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        id = in.readString();
    }


    // Used when un-parceling our parcel (creating the object)
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


    // Return hashcode of object
    @Override
    public int describeContents() {
        return 0;
    }


    // Write object values to parcel for storage
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(id);
    }


    // Getters
    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getId() {
        return id;
    }
}