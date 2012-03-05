package com.gather.jsfmatrix.core;

import com.gather.jsfmatrix.core.model.IApplicationModel;

public interface IValueHolder {

    public Object getPropertyValue(Property property);

    public IApplicationModel getApplicationModel();

    public void setApplicationModel(IApplicationModel applicationModel);
}
