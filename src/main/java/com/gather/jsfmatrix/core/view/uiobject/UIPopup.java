package com.gather.jsfmatrix.core.view.uiobject;

import org.primefaces.component.dialog.Dialog;

public class UIPopup {

    private Long id;
    private Dialog component;

    public Long getID() {
        return this.id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Dialog getComponent() {
        return component;
    }

    public void setComponent(Dialog component) {
        this.component = component;
    }

}
