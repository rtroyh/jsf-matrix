package com.gather.jsfmatrix.core;

import com.gather.jsfmatrix.core.view.IApplicationView;

public interface IMatrixApplicationHandler extends IValueHolder {

    public void generateInstance(Object... parameters);

    public String getLastGenerateInstanceMessage();

    public void setURL(String url);

    public IApplicationView getIApplicationView();
}
