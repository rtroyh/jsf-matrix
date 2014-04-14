package com.gather.jsfmatrix.core.view;

import com.gather.jsfmatrix.core.IPopulable;
import com.gather.jsfmatrix.core.view.uiobject.UIJSFObject;

public abstract class JSFViewer implements IPopulable {
    protected UIJSFObject uiObject;

    protected JSFViewer() {
        super();
    }

    protected JSFViewer(UIJSFObject uiObject) {
        this.uiObject = uiObject;
    }

    public UIJSFObject getUIJSFObject() {
        return this.uiObject;
    }

    public void setUIJSFObject(UIJSFObject jsfObject) {
        this.uiObject = jsfObject;
    }

    @Override
    public void resetState() {
        this.getUIJSFObject().resetState();
    }
}
