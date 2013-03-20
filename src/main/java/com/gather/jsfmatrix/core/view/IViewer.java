package com.gather.jsfmatrix.core.view;

import com.gather.jsfmatrix.core.IPopulable;
import com.gather.jsfmatrix.core.view.uiobject.UIJSFObject;

public interface IViewer extends IPopulable {

    public UIJSFObject getUIJSFObject();

    public void setUIJSFObject(UIJSFObject ui);
}
