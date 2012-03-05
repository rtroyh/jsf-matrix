package com.gather.jsfmatrix.core.view.uiobject;

import com.gather.jsfmatrix.core.IPopulable;
import com.gather.jsfmatrix.core.IValueHolder;

public interface UIObject extends IPopulable,
                                  IValueHolder {

    public Object getComponent();

    public void setComponent(Object o);
}
