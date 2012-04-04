package com.gather.jsfmatrix.core.view.managedBean;

import com.gather.gathercommons.util.Validator;
import com.gather.jsfmatrix.core.*;
import com.gather.jsfmatrix.core.model.ApplicationModelFactory;
import com.gather.jsfmatrix.core.model.IApplicationModel;
import com.gather.jsfmatrix.core.service.MatrixService;
import com.gather.jsfmatrix.core.view.BaseJSFView;
import com.gather.jsfmatrix.core.view.ViewType;
import com.gather.jsfmatrix.core.view.uiobject.UIDashBoard;
import com.gather.jsfmatrix.core.view.uiobject.UIObject;
import com.gather.springcommons.services.IResultSetProvider;
import org.apache.log4j.Logger;
import org.primefaces.component.dashboard.Dashboard;
import org.primefaces.component.panel.Panel;
import org.primefaces.event.DashboardReorderEvent;
import org.springframework.beans.BeansException;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import javax.faces.context.FacesContext;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public class DashBoardBean extends BaseJSFView {
    private static final Logger LOG = Logger.getLogger(DashBoardBean.class);

    private final DataSource ds;
    private IApplicationModel applicationModel;
    private MatrixService service;
    private Matrix matrix;
    private Panel selectedPanel;
    private String titleSelectedPanel = "";

    public DashBoardBean(DataSource ds) {
        super(new UIDashBoard());
        this.ds = ds;
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

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public MatrixService getService() {
        if (this.service == null) {
            this.service = new MatrixService(this.ds);
        }

        return service;
    }

    public void setService(MatrixService service) {
        this.service = service;
    }

    private void build() {
        LOG.info("INICIO CONSTRUCCION DASHBOARD");

        try {
            this.getMatrix().getApplications().clear();

            WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
            IUserBean userBean = (IUserBean) ctx.getBean("userBean");

            if (userBean != null && userBean.getUser().getId() != null) {
                IResultSetProvider resultSetProvider = this.getService().getApplications(userBean.getUser().getId());

                List<List<Object>> properties = resultSetProvider.getResultSetasListofList(1);
                List<List<Object>> data = resultSetProvider.getResultSetasListofList(2);

                if (Validator.validateList(data) && Validator.validateList(properties)) {
                    this.getMatrix().setProperties(properties);

                    for (List<Object> lo : data) {
                        if (Validator.validateLong(lo.get(0)) || Validator.validateInteger(lo.get(0))) {
                            IMatrixApplication ma = null;

                            if (ma == null) {
                                ma = MatrixApplicationFactory.createGeneric();
                            }

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
                            this.getMatrix().getApplications().add(ma);
                        }
                    }
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

    @Override
    public void populate(Map<Ingredients, Object> recipe) {
        try {
            this.build();

            Map<Ingredients, Object> theRecipe = RecipeFactory.createRecipe();
            theRecipe.put(Ingredients.APPLICATION_LIST,
                          this.getMatrix().getApplications());
            theRecipe.put(Ingredients.UNIDIMENSIONAL_LIST,
                          this.getMatrix().getProperties());

            this.getUIObject().resetState();
            this.getUIObject().populate(theRecipe);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    public void handleReorder(DashboardReorderEvent event) {
        LOG.info("INICIO EVENTO REORDER");

        try {
            Dashboard d = (Dashboard) event.getComponent();

            for (IMatrixApplication ma : this.getMatrix().getApplications()) {
                IApplicationModel model = ma.getMatrixApplicationHandler().getApplicationModel();

                if (model.getPropertyValue(Property.JSF_CLIENT_ID).equals(event.getWidgetId())) {

                    Integer pos = d.getModel().getColumnCount() *
                            event.getItemIndex() +
                            event.getColumnIndex() +
                            1;

                    this.getService().moveWidget(model.getPropertyValue(Property.SESION),
                                                 model.getPropertyValue(Property.MATRIX_ID),
                                                 pos);
                    this.populate(null);
                    return;
                }
            }
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    public void removeWidget(IMatrixApplication ma) {
        try {
            IApplicationModel model = ma.getMatrixApplicationHandler().getApplicationModel();
            this.getService().closeWidget(model.getPropertyValue(Property.SESION),
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
        LOG.info("INICIO INSERCION DE APPLICACION DESDE DOCK o STACK");

        try {
            WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
            IUserBean lb = (IUserBean) ctx.getBean("userBean");

            if (lb != null && ma != null && viewType != null) {
                IApplicationModel model = ma.getMatrixApplicationHandler().getApplicationModel();

                if (viewType.equals(ViewType.DOCK)) {
                    this.getService().addApplicationFromDock(lb.getUser().getId(),
                                                             -1,
                                                             model.getPropertyValue(Property.PAQUETE));
                } else if (viewType.equals(ViewType.STACK)) {
                    this.getService().addApplicationFromStack(lb.getUser().getId(),
                                                              model.getPropertyValue(Property.CLAVE));
                }

                this.populate(null);
            }
        } catch (BeansException e) {
            LOG.error(e.getMessage());
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    @Override
    public void setUIObject(UIObject ui) {
        this.uiObject = ui;
    }
}
