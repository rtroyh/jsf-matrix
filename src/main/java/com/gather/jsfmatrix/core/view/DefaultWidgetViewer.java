package com.gather.jsfmatrix.core.view;

import com.gather.jsfmatrix.core.app.uicomponent.DirectoryUIWidget;
import com.gather.jsfmatrix.core.view.uiobject.UIJSFObject;

public class DefaultWidgetViewer extends DefaultJSFViewer {

    public DefaultWidgetViewer() {
        super(new DirectoryUIWidget());
    }

    public DefaultWidgetViewer(UIJSFObject uijsfObject) {
        super(uijsfObject);
    }
}
