package com.gather.jsfmatrix.core.view;

import com.gather.jsfmatrix.core.app.uicomponent.DirectoryUIWidget;
import com.gather.jsfmatrix.core.view.uiobject.UIJSFObject;

public class DefaultWidgetView extends DefaultJSFView {

    public DefaultWidgetView() {
        super(new DirectoryUIWidget());
    }

    public DefaultWidgetView(UIJSFObject uijsfObject) {
        super(uijsfObject);
    }
}
