package com.gather.jsfmatrix.core.view.uicomponent;

import org.primefaces.component.menuitem.MenuItem;

@SuppressWarnings("serial")
public class CustomMenuItem extends MenuItem {

    private Object userObject;

    public Object getUserObject() {
        return userObject;
    }

    public void setUserObject(Object userObject) {
        this.userObject = userObject;
    }
}
