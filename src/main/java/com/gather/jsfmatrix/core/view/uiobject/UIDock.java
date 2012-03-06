package com.gather.jsfmatrix.core.view.uiobject;

import java.util.List;
import java.util.Map;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.component.dock.Dock;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

import com.gather.jsfmatrix.core.IMatrixApplication;
import com.gather.jsfmatrix.core.Ingredients;
import com.gather.jsfmatrix.core.Property;
import com.gather.jsfmatrix.core.listener.DockListener;
import com.gather.jsfmatrix.core.model.ApplicationModelFactory;
import com.gather.jsfmatrix.core.model.IApplicationModel;
import com.gather.jsfmatrix.core.view.PrimeFacesUIComponentsFactory;

public class UIDock implements UIJSFObject {
    private static final Logger LOG = Logger.getLogger(UIDashBoard.class);

    private Dock component;
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
        return this.getDock();
    }

    public Dock getDock() {
        if (this.component == null) {
            this.component = PrimeFacesUIComponentsFactory.createDock(FacesContext.getCurrentInstance());
            this.component.setModel(this.getMenuModel());
            this.component.setTransient(true);
        }

        return component;
    }

    public void setDock(Dock component) {
        this.component = component;
    }

    @SuppressWarnings("unchecked")
    public void populate(Map<Ingredients, Object> recipe) {
        try {
            if (!recipe.isEmpty()) {
                if (recipe.containsKey(Ingredients.APPLICATION_LIST)) {
                    List<IMatrixApplication> data = (List<IMatrixApplication>) recipe.get(Ingredients.APPLICATION_LIST);

                    for (IMatrixApplication ma : data) {
                        FacesContext fc = FacesContext.getCurrentInstance();
                        MenuItem item = PrimeFacesUIComponentsFactory.createMenuItem(fc);
                        item.setId("dock_" +
                                           FacesContext.getCurrentInstance().getViewRoot().createUniqueId());
                        item.setValue(ma.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.TITLE));

                        ResourceHandler rh = fc.getApplication().getResourceHandler();
                        Resource r = rh.createResource("images/" +
                                                               ma.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.ICON_PATH),
                                                       "gather");

                        item.setIcon(r.getRequestPath());
                        item.addActionListener(new DockListener());
                        item.getAttributes().put("ma",
                                                 ma);

                        this.getMenuModel().addMenuItem(item);
                        this.getDock().getChildren().add(item);
                    }
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    public void resetState() {
        this.setMenuModel(null);
        this.getDock().setModel(this.getMenuModel());
        this.getDock().getChildren().clear();
    }

    public boolean isPopulated() {
        return false;
    }

    public void setPopulated(boolean value) {
        // TODO Auto-generated method stub
    }

    @Override
    public void setComponent(Object o) {
        this.component = (Dock) component;
    }

    @Override
    public void setComponent(UIComponent component) {
        this.component = (Dock) component;
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
