package com.gather.jsfmatrix.core.view.managedBean;

import com.gather.gathercommons.util.Validator;
import com.gather.jsfmatrix.core.IMatrixApplication;
import com.gather.jsfmatrix.core.Ingredients;
import com.gather.jsfmatrix.core.Matrix;
import com.gather.jsfmatrix.core.MatrixApplicationFactory;
import com.gather.jsfmatrix.core.Property;
import com.gather.jsfmatrix.core.RecipeFactory;
import com.gather.jsfmatrix.core.model.ApplicationModelFactory;
import com.gather.jsfmatrix.core.model.IApplicationModel;
import com.gather.jsfmatrix.core.service.DockServices;
import com.gather.jsfmatrix.core.view.JSFViewer;
import com.gather.jsfmatrix.core.view.uiobject.UIDock;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

public class DockBean extends JSFViewer {
    private static final Logger LOG = Logger.getLogger(DockBean.class);

    private IUserBean userBean;
    private IApplicationModel applicationModel;
    private Matrix matrix;
    private DockServices dockService;

    public DockBean(DockServices dockService,
                    IUserBean userBean) {
        super(new UIDock());
        this.userBean = userBean;
        this.dockService = dockService;
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
        LOG.info("INICIO CONSTRUCCION DOCK");

        try {
            this.dockService.getList().resetParameter();
            this.dockService.getList().addParameter(userBean.getUser().getId());
            this.dockService.getList().executeQuery();

            List<List<Object>> data = this.dockService.getList().getResultSetasListofList();

            final List<IMatrixApplication> matrixApplicationList = this.getMatrix().getApplications();
            matrixApplicationList.clear();

            if (Validator.validateList(data)) {
                for (List<Object> lo : data) {
                    IMatrixApplication ma = createMatrixApplication(lo);

                    matrixApplicationList.add(ma);
                }
            }
        } catch (BeansException e) {
            LOG.error(e.getMessage());
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    private IMatrixApplication createMatrixApplication(List<Object> lo) {
        IMatrixApplication ma = MatrixApplicationFactory.createGeneric();
        ma.getMatrixApplicationHandler().getApplicationModel().addProperty(Property.PAQUETE,
                                                                           lo.get(0));
        ma.getMatrixApplicationHandler().getApplicationModel().addProperty(Property.TITLE,
                                                                           lo.get(1));
        ma.getMatrixApplicationHandler().getApplicationModel().addProperty(Property.ICON_PATH,
                                                                           lo.get(2));
        return ma;
    }

    @Override
    public void populate(Map<Ingredients, Object> recipe) {
        this.build();

        this.getUIJSFObject().resetState();
        this.getUIJSFObject().populate(RecipeFactory.createRecipe(Ingredients.APPLICATION_LIST,
                                                                  this.getMatrix().getApplications()));
    }
}
