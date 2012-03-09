package com.gather.jsfmatrix.core.view;

import java.util.Map;

import com.gather.jsfmatrix.core.Ingredients;
import com.gather.jsfmatrix.core.view.uiobject.UIJSFObject;
import com.gather.jsfmatrix.core.view.uiobject.UIObject;

public class DefaultJSFView extends BaseJSFView {

    DefaultJSFView(UIJSFObject uiObject) {
        super(uiObject);
    }

    @Override
    public void populate(Map<Ingredients, Object> recipe) {
        this.getUIObject().populate(recipe);
    }

    @Override
    public void setUIObject(UIObject ui) {
        this.uiObject = ui;
    }
}
