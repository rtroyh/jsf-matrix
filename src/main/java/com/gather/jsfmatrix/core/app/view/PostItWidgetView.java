package com.gather.jsfmatrix.core.app.view;

import com.gather.jsfmatrix.core.app.uicomponent.PostItUIWidget;
import com.gather.jsfmatrix.core.view.DefaultJSFView;
import com.gather.jsfmatrix.core.view.uiobject.UIJSFObject;

public class PostItWidgetView extends DefaultJSFView {

    public PostItWidgetView() {
        super(new PostItUIWidget());
    }

    public PostItWidgetView(UIJSFObject uiObject) {
        super(uiObject);
    }

}
