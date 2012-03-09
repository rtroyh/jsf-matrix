package com.gather.jsfmatrix.core.view;

import com.gather.jsfmatrix.core.view.uiobject.UIJSFObject;

public abstract class BaseJSFView extends BaseView {

    protected BaseJSFView() {
        super();
    }

    public BaseJSFView(UIJSFObject uiObject) {
        super(uiObject);
    }

    public UIJSFObject getUIObject() {
        return (UIJSFObject) super.getUIObject();
    }

    public void setUIObject(UIJSFObject jsfObject) {
        this.uiObject = jsfObject;
    }

}
