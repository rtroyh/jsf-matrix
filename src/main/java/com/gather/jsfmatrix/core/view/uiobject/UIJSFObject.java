package com.gather.jsfmatrix.core.view.uiobject;

import com.gather.jsfmatrix.core.IPopulable;
import com.gather.jsfmatrix.core.IValueHolder;

import javax.faces.component.UIComponent;

public interface UIJSFObject extends IValueHolder,
                                     IPopulable {

    public UIComponent getComponent();

    public void setComponent(UIComponent component);
}
