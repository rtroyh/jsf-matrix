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
import com.gather.jsfmatrix.core.service.MatrixService;
import com.gather.jsfmatrix.core.view.JSFViewer;
import com.gather.jsfmatrix.core.view.ViewType;
import com.gather.jsfmatrix.core.view.uiobject.UIDashBoard;
import com.gather.springcommons.services.IResultSetProvider;
import org.apache.log4j.Logger;
import org.primefaces.component.panel.Panel;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

public class DashBoardBean extends JSFViewer {
    private static final Logger LOG = Logger.getLogger(DashBoardBean.class);

    private IUserBean userBean;
    private IApplicationModel applicationModel;
    private MatrixService service;
    private Matrix matrix;
    private Panel selectedPanel;
    private String titleSelectedPanel = "";

    public DashBoardBean(MatrixService service,
                         IUserBean userBean) {
        super(new UIDashBoard());
        this.userBean = userBean;
        this.service = service;
    }

    public IApplicationModel getApplicationModel() {
        if (this.applicationModel == null) {
            this.applicationModel = ApplicationModelFactory.createDefaultApplicationModel();
        }

        return this.applicationModel;
    }

    public String getTitleSelectedPanel() {
        return titleSelectedPanel;
    }

    public void setTitleSelectedPanel(String titleSelectedPanel) {
        this.titleSelectedPanel = titleSelectedPanel;
    }

    public Panel getSelectedPanel() {
        return selectedPanel;
    }

    public void setSelectedPanel(Panel selectedPanel) {
        this.selectedPanel = selectedPanel;
    }

    public Matrix getMatrix() {
        if (this.matrix == null) {
            this.matrix = new Matrix();
        }

        return matrix;
    }

    public final void updateThroughBreadCrumb(Object sesion,
                                              Object matrixID,
                                              Object javaID) {
        this.service.updateThroughBreadCrumb(sesion,
                                             matrixID,
                                             javaID);
    }

    public final void titleRename(Object sesion,
                                  Object matrixID,
                                  Object title) {
        this.service.titleRename(sesion,
                                 matrixID,
                                 title);
    }

    private void build() {
        LOG.info("INICIO CONSTRUCCION DASHBOARD");

        try {
            final Matrix matrix1 = this.getMatrix();
            final List<IMatrixApplication> matrixApplicationList = matrix1.getApplications();
            matrixApplicationList.clear();

            IResultSetProvider resultSetProvider = service.getApplications(userBean.getUser().getId());

            List<List<Object>> properties = resultSetProvider.getResultSetasListofList(1);
            List<List<Object>> data = resultSetProvider.getResultSetasListofList(2);

            if (Validator.validateList(data) && Validator.validateList(properties)) {
                matrix1.setProperties(properties);

                for (List<Object> lo : data) {
                    if (Validator.validateLong(lo.get(0)) || Validator.validateInteger(lo.get(0))) {
                        IMatrixApplication ma = MatrixApplicationFactory.createGeneric();
                        IApplicationModel model = ma.getMatrixApplicationHandler().getApplicationModel();

                        model.addProperty(Property.SESION,
                                          userBean.getUser().getId());
                        model.addProperty(Property.POSITION,
                                          lo.get(0));
                        model.addProperty(Property.MATRIX_ID,
                                          lo.get(1));
                        model.addProperty(Property.JAVA_ID,
                                          lo.get(3));
                        model.addProperty(Property.INSTANCE,
                                          lo.get(4));
                        model.addProperty(Property.TITLE,
                                          lo.get(5));
                        model.addProperty(Property.BORRABLE,
                                          lo.get(7));
                        model.addProperty(Property.VIEW_PATH,
                                          lo.get(8));
                        model.addProperty(Property.ICON_PATH,
                                          lo.get(9));
                        model.addProperty(Property.VIEW_TYPE,
                                          properties.get(0).get(0));

                        matrixApplicationList.add(ma);
                    }
                }
            }
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    @Override
    public void populate(Map<Ingredients, Object> recipe) {
        LOG.info("INICIO POBLAMIENTO DASHBOARD");

        try {
            this.build();

            Map<Ingredients, Object> theRecipe = RecipeFactory.createRecipe();
            theRecipe.put(Ingredients.APPLICATION_LIST,
                          this.getMatrix().getApplications());
            theRecipe.put(Ingredients.UNIDIMENSIONAL_LIST,
                          this.getMatrix().getProperties());

            this.getUIJSFObject().resetState();
            this.getUIJSFObject().populate(theRecipe);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    public void removeWidget(IMatrixApplication ma) {
        try {
            IApplicationModel model = ma.getMatrixApplicationHandler().getApplicationModel();
            service.closeWidget(model.getPropertyValue(Property.SESION),
                                model.getPropertyValue(Property.MATRIX_ID));

            this.populate(null);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    public void addMatrixApplication(IMatrixApplication ma,
                                     ViewType viewType) {
        LOG.info("INICIO INSERCION DE APPLICACION DESDE DOCK");

        try {
            IApplicationModel model = ma.getMatrixApplicationHandler().getApplicationModel();

            if (viewType.equals(ViewType.DOCK)) {
                service.addApplicationFromDock(userBean.getUser().getId(),
                                               -1,
                                               model.getPropertyValue(Property.PAQUETE));
            }

            this.populate(null);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }
}
