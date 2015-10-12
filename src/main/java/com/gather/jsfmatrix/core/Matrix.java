package com.gather.jsfmatrix.core;

import java.util.LinkedList;
import java.util.List;

public class Matrix {
    private List<List<Object>> properties;
    private List<IMatrixApplication> applications;
    private List<List<Object>> messages;

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
        if (this.properties == null) {
            this.properties = new LinkedList<List<Object>>();
        }

        return properties;
    }

    public void setProperties(List<List<Object>> properties) {
        this.properties = properties;
    }

    public List<List<Object>> getMessages() {
        return messages;
    }

    public void setMessages(List<List<Object>> messages) {
        this.messages = messages;
    }
}
