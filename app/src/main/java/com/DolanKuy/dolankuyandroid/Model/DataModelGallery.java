package com.DolanKuy.dolankuyandroid.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataModelGallery {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("list_location_id")
    @Expose
    private int list_location_id;

    @SerializedName("filename")
    @Expose
    private String filename;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getList_location_id() {
        return list_location_id;
    }

    public void setList_location_id(int list_location_id) {
        this.list_location_id = list_location_id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
