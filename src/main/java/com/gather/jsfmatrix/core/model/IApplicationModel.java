package com.gather.jsfmatrix.core.model;

import java.util.EnumMap;

import com.gather.jsfmatrix.core.IValueHolder;
import com.gather.jsfmatrix.core.Property;

public interface IApplicationModel extends IValueHolder {

    public EnumMap<Property, Object> getPropertyMap();

    public IApplicationModel addProperty(Property property,
                                         Object value);

    public void removeProperty(Property property);
}
