package com.gather.jsfmatrix.core.app.view;

import com.gather.jsfmatrix.core.app.uicomponent.SheetUIWidget;
import com.gather.jsfmatrix.core.view.DefaultJSFView;
import com.gather.jsfmatrix.core.view.uiobject.UIJSFObject;

public class SheetWidgetView extends DefaultJSFView {

    public SheetWidgetView() {
        super(new SheetUIWidget());
    }

    public SheetWidgetView(UIJSFObject uiObject) {
        super(uiObject);
    }

}
