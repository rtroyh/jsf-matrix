package com.gather.jsfmatrix.core.view.uiobject;

import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.component.breadcrumb.BreadCrumb;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

import com.gather.jsfmatrix.core.IMatrixApplication;
import com.gather.jsfmatrix.core.Ingredients;
import com.gather.jsfmatrix.core.Property;
import com.gather.jsfmatrix.core.listener.BreadCrumbListener;
import com.gather.jsfmatrix.core.model.ApplicationModelFactory;
import com.gather.jsfmatrix.core.model.IApplicationModel;
import com.gather.jsfmatrix.core.view.PrimeFacesUIComponentsFactory;

public class UIBreadCrumb implements UIJSFObject {
    private static final Logger LOG = Logger.getLogger(UIBreadCrumb.class);

    private BreadCrumb component;
    private MenuModel menuModel;
    private IApplicationModel applicationModel;

    public IApplicationModel getApplicationModel() {
        if (this.applicationModel == null) {
            this.applicationModel = ApplicationModelFactory.createDefaultApplicationModel();
        }

        return this.applicationModel;
    }

    MenuModel getMenuModel() {
        if (this.menuModel == null) {
            this.menuModel = new DefaultMenuModel();
        }

        return menuModel;
    }

    void setMenuModel(MenuModel menuModel) {
        this.menuModel = menuModel;
    }

    public UIComponent getComponent() {
        return this.getBreadCrumb();
    }

    BreadCrumb getBreadCrumb() {
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
                        MenuItem item = PrimeFacesUIComponentsFactory.createMenuItem(FacesContext.getCurrentInstance());
                        item.setId("breadcrumb_item_" +
                                           FacesContext.getCurrentInstance().getViewRoot().createUniqueId() +
                                           "_" +
                                           java.util.Calendar.getInstance().getTimeInMillis());
                        item.setValue(ma.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.TITLE));
                        item.setTransient(true);
                        item.setUpdate(":myForm:principal");
                        item.setProcess("@this");
                        item.getAttributes().put("ma",
                                                 ma);
                        item.addActionListener(new BreadCrumbListener());

                        this.getMenuModel().addMenuItem(item);
                        this.getBreadCrumb().getChildren().add(item);
                    }
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
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
