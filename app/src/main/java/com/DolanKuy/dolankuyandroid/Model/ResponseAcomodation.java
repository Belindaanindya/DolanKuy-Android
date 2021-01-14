package com.DolanKuy.dolankuyandroid.Model;

import java.util.List;

public class ResponseAcomodation {

    private DataCategory category;

    private List<DataModelDashboard> currentLocation;

    public DataCategory getCategory() {
        return category;
    }

    public void setCategory(DataCategory category) {
        this.category = category;
    }

    public List<DataModelDashboard> getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(List<DataModelDashboard> currentLocation) {
        this.currentLocation = currentLocation;
    }
}
