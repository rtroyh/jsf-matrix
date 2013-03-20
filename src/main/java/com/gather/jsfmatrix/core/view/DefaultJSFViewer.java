package com.gather.jsfmatrix.core.view;

import com.gather.jsfmatrix.core.Ingredients;
import com.gather.jsfmatrix.core.view.uiobject.UIJSFObject;

import java.util.Map;

public class DefaultJSFViewer extends JSFViewer {

    DefaultJSFViewer(UIJSFObject uiObject) {
        super(uiObject);
    }

    @Override
    public void populate(Map<Ingredients, Object> recipe) {
        this.getUIJSFObject().populate(recipe);
    }
}
