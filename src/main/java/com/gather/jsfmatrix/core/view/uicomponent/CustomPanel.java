package com.gather.jsfmatrix.core.view.uicomponent;

import org.primefaces.component.panel.Panel;

public class CustomPanel extends Panel {

    private Object userObject;

    public Object getUserObject() {
        return userObject;
    }

    public void setUserObject(Object userObject) {
        this.userObject = userObject;
    }

}
