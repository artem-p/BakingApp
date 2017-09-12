package ru.artempugachev.bakingapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Step for prepare recipe. Contains description and links to video and image.
 */

public final class Step {
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
}
