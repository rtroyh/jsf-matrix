package com.gather.jsfmatrix.core.view.managedBean;

import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.gather.gathercommons.util.Validator;
import com.gather.jsfmatrix.core.IMatrixApplication;
import com.gather.jsfmatrix.core.Ingredients;
import com.gather.jsfmatrix.core.Matrix;
import com.gather.jsfmatrix.core.MatrixApplicationFactory;
import com.gather.jsfmatrix.core.Property;
import com.gather.jsfmatrix.core.RecipeFactory;
import com.gather.jsfmatrix.core.model.ApplicationModelFactory;
import com.gather.jsfmatrix.core.model.IApplicationModel;
import com.gather.jsfmatrix.core.service.StackServices;
import com.gather.jsfmatrix.core.view.BaseJSFView;
import com.gather.jsfmatrix.core.view.ViewType;
import com.gather.jsfmatrix.core.view.uiobject.UIObject;
import com.gather.jsfmatrix.core.view.uiobject.UIStack;

public class StackBean extends BaseJSFView {

    private Matrix matrix;
    private DataSource ds;
    private StackServices stackService;
    private IApplicationModel applicationModel;

    public IApplicationModel getApplicationModel() {
        if (this.applicationModel == null) {
            this.applicationModel = ApplicationModelFactory.createDefaultApplicationModel();
        }

        return this.applicationModel;
    }

    public Matrix getMatrix() {
        if (this.matrix == null) {
            this.matrix = new Matrix();
        }

        return this.matrix;
    }

    public StackBean(DataSource ds) {
        super(new UIStack());
        this.ds = ds;
    }

    public void setMatrixBean(Matrix matrix) {
        this.matrix = matrix;
    }

    public StackServices getStackServices() {
        if (this.stackService == null) {
            this.stackService = new StackServices(this.ds);
        }

        return this.stackService;
    }

    private void build() {
        Logger.getLogger(StackBean.class).info("INICIO CONSTRUCCION STACK");

        try {
            WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
            IUserBean lb = (IUserBean) ctx.getBean("userBean");

            if (lb != null &&
                    lb.getUser().getId() != null) {
                this.getStackServices().getListService().resetParameter();
                this.getStackServices().getListService().addParameter("1",
                                                                      lb.getUser().getId());
                this.getStackServices().getListService().executeQuery();

                List<List<Object>> data = this.getStackServices().getListService().getResultSetasListofList();

                this.getMatrix().getApplications().clear();
                if (Validator.validateList(data)) {
                    for (List<Object> lo : data) {
                        if (Validator.validateLong(lo.get(0)) ||
                                Validator.validateInteger(lo.get(0))) {
                            IMatrixApplication ma = MatrixApplicationFactory.createGeneric();
                            ma.getMatrixApplicationHandler().getApplicationModel().addProperty(Property.CLAVE,
                                                                                               lo.get(0));
                            ma.getMatrixApplicationHandler().getApplicationModel().addProperty(Property.TITLE,
                                                                                               lo.get(2));
                            ma.getMatrixApplicationHandler().getApplicationModel().addProperty(Property.ICON_PATH,
                                                                                               lo.get(3));

                            this.getMatrix().getApplications().add(ma);
                        }
                    }
                }
            }
        } catch (BeansException e) {
            Logger.getLogger(StackBean.class).error(e.getMessage());
        } catch (DataAccessException e) {
            Logger.getLogger(StackBean.class).error(e.getMessage());
        }
    }

    @Override
    public ViewType getViewType() {
        return ViewType.CORE;
    }

    @Override
    public void populate(Map<Ingredients, Object> recipe) {
        this.build();

        this.getUIObject().resetState();
        if (Validator.validateList(this.getMatrix().getApplications())) {
            this.getUIObject().populate(RecipeFactory.createRecipe(Ingredients.APPLICATION_LIST,
                                                                   this.getMatrix().getApplications()));
        }
    }

    @Override
    public void setUIObject(UIObject ui) {
        this.uiObject = ui;
    }
}
