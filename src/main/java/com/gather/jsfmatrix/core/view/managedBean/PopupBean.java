package com.gather.jsfmatrix.core.view.managedBean;

import java.util.Map;

import com.gather.jsfmatrix.core.Ingredients;
import com.gather.jsfmatrix.core.model.ApplicationModelFactory;
import com.gather.jsfmatrix.core.model.IApplicationModel;
import com.gather.jsfmatrix.core.view.BaseJSFView;
import com.gather.jsfmatrix.core.view.ViewType;
import com.gather.jsfmatrix.core.view.uiobject.UIJSFObject;
import com.gather.jsfmatrix.core.view.uiobject.UIObject;

public class PopupBean extends BaseJSFView {

    private IApplicationModel applicationModel;

    public IApplicationModel getApplicationModel() {
        if (this.applicationModel == null) {
            this.applicationModel = ApplicationModelFactory.createDefaultApplicationModel();
        }

        return this.applicationModel;
    }

    public PopupBean(UIJSFObject uiObject) {
        super(uiObject);
    }

    @Override
    public ViewType getViewType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setUIObject(UIObject ui) {
        // TODO Auto-generated method stub

    }

    @Override
    public void populate(Map<Ingredients, Object> recipe) {
        // TODO Auto-generated method stub

    }

}
