package com.example.dolankuyandroid.Model;

import java.util.List;

public class ResponseModelDashboard {
    private List<DataModelDashboard> locations;
    private List<DataModelGallery> galery;

    public List<DataModelGallery> getGalery() {
        return galery;
    }

    public void setGalery(List<DataModelGallery> galery) {
        this.galery = galery;
    }

    public List<DataModelDashboard> getLocations() {
        return locations;
    }

    public void setLocations(List<DataModelDashboard> locations) {
        this.locations = locations;
    }
}
