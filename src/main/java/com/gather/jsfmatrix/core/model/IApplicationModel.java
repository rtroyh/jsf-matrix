package com.gather.jsfmatrix.core.model;

import com.gather.jsfmatrix.core.IValueHolder;
import com.gather.jsfmatrix.core.Property;

import java.util.EnumMap;

public interface IApplicationModel extends IValueHolder {

    public EnumMap<Property, Object> getPropertyMap();

    public void addProperty(Property property,
                            Object value);

    public void removeProperty(Property property);
}
