package com.gather.jsfmatrix.core.view;

import com.gather.jsfmatrix.core.IPopulable;
import com.gather.jsfmatrix.core.view.uiobject.UIObject;

public interface Viewer extends IPopulable {

    public UIObject getUIObject();

    public void setUIObject(UIObject ui);

    public ViewType getViewType();
}
