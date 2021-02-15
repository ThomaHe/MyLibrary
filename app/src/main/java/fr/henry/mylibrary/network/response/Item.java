package fr.henry.mylibrary.network.response;

import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("id")
    private String id;
    @SerializedName("volumeInfo")
    private VolumeInfo volumeInfo;

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }
}
