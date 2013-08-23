package com.gather.jsfmatrix.core.view.managedBean;

import com.gather.gathercommons.util.Validator;
import com.gather.jsfmatrix.core.*;
import com.gather.jsfmatrix.core.model.ApplicationModelFactory;
import com.gather.jsfmatrix.core.model.IApplicationModel;
import com.gather.jsfmatrix.core.service.BreadCrumbService;
import com.gather.jsfmatrix.core.view.JSFViewer;
import com.gather.jsfmatrix.core.view.uiobject.UIBreadCrumb;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import javax.faces.context.FacesContext;
import java.util.List;
import java.util.Map;

public class BreadCrumbBean extends JSFViewer {
    private static final Logger LOG = Logger.getLogger(BreadCrumbBean.class);

    private Matrix matrix;
    private BreadCrumbService breadCrumbService;
    private IApplicationModel applicationModel;

    public BreadCrumbBean(BreadCrumbService breadCrumbService) {
        super(new UIBreadCrumb());
        this.breadCrumbService = breadCrumbService;
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

    private void build() {
        LOG.info("INICIO CONSTRUCCION BREADCRUMB");

        try {
            WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
            IUserBean lb = (IUserBean) ctx.getBean("userBean");

            if (lb != null && lb.getUser().getId() != null) {
                this.breadCrumbService.getHistorialService().resetParameter();
                this.breadCrumbService.getHistorialService().addParameter(lb.getUser().getId());
                this.breadCrumbService.getHistorialService().executeQuery();

                List<List<Object>> data = this.breadCrumbService.getHistorialService().getResultSetasListofList();

                this.getMatrix().getApplications().clear();

                if (Validator.validateList(data)) {
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

        this.getUIJSFObject().resetState();
        this.getUIJSFObject().populate(RecipeFactory.createRecipe(Ingredients.APPLICATION_LIST,
                                                                  this.getMatrix().getApplications()));
    }
}
