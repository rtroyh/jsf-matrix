package com.gather.jsfmatrix.core.view;

import com.gather.jsfmatrix.core.view.uiobject.UIObject;

public abstract class BaseView implements Viewer {

    protected UIObject uiObject;

    BaseView() {
        super();
    }

    BaseView(UIObject uiObject) {
        super();
        this.uiObject = uiObject;
    }

    @Override
    public UIObject getUIObject() {
        return uiObject;
    }

    @Override
    public void resetState() {
        this.getUIObject().resetState();
    }
}
