package ru.artempugachev.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Step for prepare recipe. Contains description and links to video and image.
 */

public final class Step implements Parcelable {
    @SerializedName("id")
    private int id;

    @SerializedName("shortDescription")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("videoURL")
    private String videoUrl;

    @SerializedName("thumbnailURL")
    private String thumbnailUrl;

    private Step(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        videoUrl = in.readString();
        thumbnailUrl = in.readString();
    }


    /**
     * Display step as text string. (ex.: 0. Recipe Introduction).
     * */
    public String asText() {
        return id + ". " + title;
    }

    /**
     * Parcelable methods
     * */
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(videoUrl);
        dest.writeString(thumbnailUrl);
    }
}
