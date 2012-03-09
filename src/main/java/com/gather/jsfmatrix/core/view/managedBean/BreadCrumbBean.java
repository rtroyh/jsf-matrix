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
import com.gather.jsfmatrix.core.service.BreadCrumbService;
import com.gather.jsfmatrix.core.view.BaseJSFView;
import com.gather.jsfmatrix.core.view.uiobject.UIBreadCrumb;
import com.gather.jsfmatrix.core.view.uiobject.UIObject;

public class BreadCrumbBean extends BaseJSFView {
    private static final Logger LOG = Logger.getLogger(BreadCrumbBean.class);

    private final DataSource ds;
    private Matrix matrix;
    private BreadCrumbService BreadCrumbService;
    private IApplicationModel applicationModel;

    public BreadCrumbBean(DataSource ds) {
        super(new UIBreadCrumb());
        this.ds = ds;
    }

    public IApplicationModel getApplicationModel() {
        if (this.applicationModel == null) {
            this.applicationModel = ApplicationModelFactory.createDefaultApplicationModel();
        }

        return this.applicationModel;
    }

    Matrix getMatrix() {
        if (this.matrix == null) {
            this.matrix = new Matrix();
        }

        return this.matrix;
    }

    public void setMatrixBean(Matrix matrix) {
        this.matrix = matrix;
    }

    private BreadCrumbService getBreadCrumbService() {
        if (this.BreadCrumbService == null) {
            this.BreadCrumbService = new BreadCrumbService(this.ds);
        }

        return this.BreadCrumbService;
    }

    private void build() {
        LOG.info("INICIO CONSTRUCCION BREADCRUMB");

        try {
            WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
            IUserBean lb = (IUserBean) ctx.getBean("userBean");

            if (lb != null && lb.getUser().getId() != null) {
                this.getBreadCrumbService().getHistorialService().resetParameter();
                this.getBreadCrumbService().getHistorialService().addParameter("1",
                                                                               lb.getUser().getId());
                this.getBreadCrumbService().getHistorialService().executeQuery();

                List<List<Object>> data = this.getBreadCrumbService().getHistorialService().getResultSetasListofList();

                this.getMatrix().getApplications().clear();

                if (Validator.validateList(data.get(0))) {
                    data.get(0).set(0,
                                    "");
                }

                for (List<Object> lo : data) {
                    IMatrixApplication ma = MatrixApplicationFactory.createGeneric();
                    ma.getMatrixApplicationHandler().getApplicationModel().addProperty(Property.SESION,
                                                                                       lb.getUser().getId());
                    ma.getMatrixApplicationHandler().getApplicationModel().addProperty(Property.TITLE,
                                                                                       lo.get(0));
                    ma.getMatrixApplicationHandler().getApplicationModel().addProperty(Property.MATRIX_ID,
                                                                                       lo.get(1));
                    ma.getMatrixApplicationHandler().getApplicationModel().addProperty(Property.JAVA_ID,
                                                                                       lo.get(2));
                    this.getMatrix().getApplications().add(ma);
                }
            }
        } catch (BeansException e) {
            LOG.error(e.getMessage());
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
    }

    @Override
    public void populate(Map<Ingredients, Object> recipe) {
        this.build();

        this.getUIObject().resetState();
        this.getUIObject().populate(RecipeFactory.createRecipe(Ingredients.APPLICATION_LIST,
                                                               this.getMatrix().getApplications()));
    }

    @Override
    public void setUIObject(UIObject ui) {
        this.uiObject = ui;
    }
}
