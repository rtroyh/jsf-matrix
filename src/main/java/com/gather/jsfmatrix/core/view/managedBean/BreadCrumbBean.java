package com.gather.jsfmatrix.core.view.managedBean;

import com.gather.gathercommons.util.Validator;
import com.gather.jsfmatrix.core.*;
import com.gather.jsfmatrix.core.model.ApplicationModelFactory;
import com.gather.jsfmatrix.core.model.IApplicationModel;
import com.gather.jsfmatrix.core.service.BreadCrumbService;
import com.gather.jsfmatrix.core.view.JSFViewer;
import com.gather.jsfmatrix.core.view.uiobject.UIBreadCrumb;
import com.gather.jsfmatrix.core.view.uiobject.UIJSFObject;
import com.gather.springcommons.services.AdvancedSSPService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

public class BreadCrumbBean extends JSFViewer {
    private static final Logger LOG = Logger.getLogger(BreadCrumbBean.class);

    private IUserBean userBean;
    private Matrix matrix;
    private BreadCrumbService breadCrumbService;
    private IApplicationModel applicationModel;

    public BreadCrumbBean(BreadCrumbService breadCrumbService,
                          IUserBean userBean) {
        super(new UIBreadCrumb());
        this.userBean = userBean;
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
            final AdvancedSSPService historialService = this.breadCrumbService.getHistorialService();
            historialService.resetParameter();
            historialService.addParameter(userBean.getUser().getId());
            historialService.executeQuery();

            List<List<Object>> data = historialService.getResultSetasListofList();

            this.getMatrix().getApplications().clear();

            if (Validator.validateList(data)) {
                if (Validator.validateList(data.get(0))) {
                    data.get(0).set(0,
                                    "");
                }

                for (List<Object> lo : data) {
                    IMatrixApplication ma = MatrixApplicationFactory.createGeneric();
                    final IApplicationModel iApplicationModel = ma.getMatrixApplicationHandler().getApplicationModel();
                    iApplicationModel.addProperty(Property.SESION,
                                                  userBean.getUser().getId());
                    iApplicationModel.addProperty(Property.TITLE,
                                                  lo.get(0));
                    iApplicationModel.addProperty(Property.MATRIX_ID,
                                                  lo.get(1));
                    iApplicationModel.addProperty(Property.JAVA_ID,
                                                  lo.get(2));
                    this.getMatrix().getApplications().add(ma);
                }
            }
        } catch (BeansException | DataAccessException e) {
            LOG.error(e.getMessage());
        }
    }

    @Override
    public void populate(Map<Ingredients, Object> recipe) {
        this.build();

        final UIJSFObject uijsfObject = this.getUIJSFObject();
        uijsfObject.resetState();
        uijsfObject.populate(RecipeFactory.createRecipe(Ingredients.APPLICATION_LIST,
                                                        this.getMatrix().getApplications()));
    }
}
