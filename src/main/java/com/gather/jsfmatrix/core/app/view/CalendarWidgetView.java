package com.gather.jsfmatrix.core.app.view;

import com.gather.jsfmatrix.core.app.uicomponent.CalendarUIWidget;
import com.gather.jsfmatrix.core.view.DefaultJSFView;
import com.gather.jsfmatrix.core.view.uiobject.UIJSFObject;

public class CalendarWidgetView extends DefaultJSFView {

    public CalendarWidgetView() {
        super(new CalendarUIWidget());
    }

    public CalendarWidgetView(UIJSFObject uiObject) {
        super(uiObject);
    }

}
