package com.gather.jsfmatrix.core;

import java.util.LinkedList;
import java.util.List;

public class Matrix {

    private List<IMatrixApplication> applications;
    private List<List<Object>> Properties;

    public List<IMatrixApplication> getApplications() {
        if (this.applications == null) {
            this.applications = new LinkedList<IMatrixApplication>();
        }

        return applications;
    }

    public void setApplications(List<IMatrixApplication> applications) {
        this.applications = applications;
    }

    public void addMatrixApplication(IMatrixApplication ma) {
        this.getApplications().add(ma);
    }

    public List<List<Object>> getProperties() {
        if (this.Properties == null) {
            this.Properties = new LinkedList<List<Object>>();
        }

        return Properties;
    }

    public void setProperties(List<List<Object>> properties) {
        Properties = properties;
    }

}
