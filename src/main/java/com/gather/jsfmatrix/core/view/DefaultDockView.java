package com.gather.jsfmatrix.core.view;

import com.gather.jsfmatrix.core.view.uiobject.UIJSFObject;

public class DefaultDockView extends DefaultJSFView {

    public DefaultDockView(UIJSFObject uiObject) {
        super(uiObject);
    }

    @Override
    public ViewType getViewType() {
        return ViewType.DOCK;
    }

}
