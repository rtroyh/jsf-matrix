package com.gather.jsfmatrix.core.view.uiobject;

import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.component.breadcrumb.BreadCrumb;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

import com.gather.jsfmatrix.core.IMatrixApplication;
import com.gather.jsfmatrix.core.Ingredients;
import com.gather.jsfmatrix.core.Property;
import com.gather.jsfmatrix.core.listener.BreadListener;
import com.gather.jsfmatrix.core.model.ApplicationModelFactory;
import com.gather.jsfmatrix.core.model.IApplicationModel;
import com.gather.jsfmatrix.core.view.PrimeFacesUIComponentsFactory;
import com.gather.jsfmatrix.core.view.uicomponent.CustomMenuItem;

public class UIBreadCrumb implements UIJSFObject {

    private BreadCrumb component;
    private MenuModel menuModel;
    private IApplicationModel applicationModel;

    public IApplicationModel getApplicationModel() {
        if (this.applicationModel == null) {
            this.applicationModel = ApplicationModelFactory.createDefaultApplicationModel();
        }

        return this.applicationModel;
    }

    public MenuModel getMenuModel() {
        if (this.menuModel == null) {
            this.menuModel = new DefaultMenuModel();
        }

        return menuModel;
    }

    public void setMenuModel(MenuModel menuModel) {
        this.menuModel = menuModel;
    }

    public UIComponent getComponent() {
        return this.getBreadCrumb();
    }

    public BreadCrumb getBreadCrumb() {
        if (this.component == null) {
            this.component = PrimeFacesUIComponentsFactory.createbreadCrumb(FacesContext.getCurrentInstance());
            this.component.setStyle("width: 990px;");
            this.component.setModel(this.getMenuModel());
            this.component.setTransient(true);
        }

        return component;
    }

    public void setBreadCrumb(BreadCrumb component) {
        this.component = component;
    }

    @SuppressWarnings("unchecked")
    public void populate(Map<Ingredients, Object> recipe) {
        try {
            if (!recipe.isEmpty()) {
                if (recipe.containsKey(Ingredients.APPLICATION_LIST)) {
                    this.resetState();
                    List<IMatrixApplication> data = (List<IMatrixApplication>) recipe.get(Ingredients.APPLICATION_LIST);

                    for (IMatrixApplication ma : data) {
                        CustomMenuItem item = new CustomMenuItem();
                        item.setId("breadcrumb_item_" +
                                           FacesContext.getCurrentInstance().getViewRoot().createUniqueId() +
                                           "_" +
                                           java.util.Calendar.getInstance().getTimeInMillis());
                        item.setValue(ma.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.TITLE));
                        item.setTransient(true);
                        item.setUserObject(ma);
                        item.addActionListener(new BreadListener());
                        item.setOnstart("cargaDialogWV.show();");
                        item.setOncomplete("cargaDialogWV.hide();");

                        this.getMenuModel().addMenuItem(item);
                        this.getBreadCrumb().getChildren().add(item);
                    }
                }
            }
        } catch (Exception e) {
            Logger.getLogger(UIBreadCrumb.class).error(e.getMessage());
        }
    }

    public void resetState() {
        this.getBreadCrumb().getChildren().clear();
        this.setMenuModel(null);
        this.getBreadCrumb().setModel(this.getMenuModel());
    }

    @Override
    public void setComponent(Object component) {
        this.component = (BreadCrumb) component;
    }

    @Override
    public void setComponent(UIComponent component) {
        this.component = (BreadCrumb) component;
    }

    @Override
    public Object getPropertyValue(Property property) {
        return this.getApplicationModel().getPropertyValue(property);
    }

    @Override
    public void setApplicationModel(IApplicationModel applicationModel) {
        this.applicationModel = applicationModel;
    }
}
