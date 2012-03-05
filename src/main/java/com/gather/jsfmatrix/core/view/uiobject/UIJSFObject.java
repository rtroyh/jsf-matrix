package com.gather.jsfmatrix.core.view.uiobject;

import javax.faces.component.UIComponent;

public interface UIJSFObject extends UIObject {

    public UIComponent getComponent();

    public void setComponent(UIComponent component);
}
