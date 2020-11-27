package com.example.dolankuyandroid.Model;

import java.util.List;

public class ResponseModelDashboard {
    private List<DataModelDashboard> acomodation;

    public List<DataModelDashboard> getLocation() {

        return acomodation;
    }

    public void setLocation(List<DataModelDashboard> location) {

        this.acomodation = location;
    }
}
