package com.gather.jsfmatrix.core.model;

import com.gather.jsfmatrix.core.Property;

import java.util.EnumMap;

public class DefaultApplicationModel implements IApplicationModel {

    private EnumMap<Property, Object> propertyMap;

    @Override
    public void addProperty(Property property,
                            Object value) {
        this.getPropertyMap().put(property,
                                  value);

    }

    @Override
    public Object getPropertyValue(Property property) {
        return this.getPropertyMap().get(property);
    }

    @Override
    public EnumMap<Property, Object> getPropertyMap() {
        if (this.propertyMap == null) {
            this.propertyMap = new EnumMap<Property, Object>(Property.class);
        }

        return this.propertyMap;
    }

    @Override
    public void removeProperty(Property property) {
        this.getPropertyMap().remove(property);
    }

    @Override
    public IApplicationModel getApplicationModel() {
        return this;
    }

    @Override
    public void setApplicationModel(IApplicationModel applicationModel) {
    }
}
