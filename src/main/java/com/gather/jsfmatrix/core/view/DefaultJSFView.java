package com.gather.jsfmatrix.core.view;

import java.util.Map;

import com.gather.jsfmatrix.core.Ingredients;
import com.gather.jsfmatrix.core.view.uiobject.UIJSFObject;
import com.gather.jsfmatrix.core.view.uiobject.UIObject;

public class DefaultJSFView extends BaseJSFView {

    public DefaultJSFView(UIJSFObject uiObject) {
        super(uiObject);
    }

    @Override
    public void populate(Map<Ingredients, Object> recipe) {
        this.getUIObject().populate(recipe);
    }

    @Override
    public ViewType getViewType() {
        return ViewType.APPLICATION;
    }

    @Override
    public void setUIObject(UIObject ui) {
        this.uiObject = ui;
    }
}
