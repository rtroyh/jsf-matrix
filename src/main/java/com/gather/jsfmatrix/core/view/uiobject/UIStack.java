package com.gather.jsfmatrix.core.view.uiobject;

import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.stack.Stack;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

import com.gather.jsfmatrix.core.IMatrixApplication;
import com.gather.jsfmatrix.core.Ingredients;
import com.gather.jsfmatrix.core.Property;
import com.gather.jsfmatrix.core.listener.StackListener;
import com.gather.jsfmatrix.core.model.ApplicationModelFactory;
import com.gather.jsfmatrix.core.model.IApplicationModel;
import com.gather.jsfmatrix.core.view.PrimeFacesUIComponentsFactory;

public class UIStack implements UIJSFObject {

    private IApplicationModel applicationModel;
    private Stack component;
    private MenuModel menuModel;

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
        return this.getStack();
    }

    public Stack getStack() {
        if (this.component == null) {
            this.component = PrimeFacesUIComponentsFactory.createStack(FacesContext.getCurrentInstance());
            this.component.setIcon("./images/stack.png");
            this.component.setModel(this.getMenuModel());
            this.component.setTransient(true);
        }

        return component;
    }

    public void setStack(Stack component) {
        this.component = component;
    }

    @SuppressWarnings("unchecked")
    public void populate(Map<Ingredients, Object> recipe) {
        if (!recipe.isEmpty()) {
            if (recipe.containsKey(Ingredients.APPLICATION_LIST)) {
                List<IMatrixApplication> data = (List<IMatrixApplication>) recipe.get(Ingredients.APPLICATION_LIST);

                for (IMatrixApplication ma : data) {
                    MenuItem item = PrimeFacesUIComponentsFactory.createMenuItem(FacesContext.getCurrentInstance());
                    item.setId("stack_item_" +
                                       FacesContext.getCurrentInstance().getViewRoot().createUniqueId() +
                                       "_" +
                                       java.util.Calendar.getInstance().getTimeInMillis());
                    item.setValue(ma.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.TITLE));
                    item.setIcon("./images/" +
                                         ma.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.ICON_PATH));
                    item.addActionListener(new StackListener());
                    item.getAttributes().put("ma",
                                             ma);
                    item.setTransient(true);

                    this.getMenuModel().addMenuItem(item);
                    this.getStack().getChildren().add(item);
                }
            }
        }
    }

    public void resetState() {
        this.setMenuModel(null);
        this.getStack().getChildren().clear();
        this.getStack().setModel(this.getMenuModel());
    }

    public boolean isPopulated() {
        return false;
    }

    public void setPopulated(boolean value) {
        // TODO Auto-generated method stub
    }

    @Override
    public void setComponent(Object o) {
        this.component = (Stack) component;
    }

    @Override
    public void setComponent(UIComponent component) {
        this.component = (Stack) component;
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
