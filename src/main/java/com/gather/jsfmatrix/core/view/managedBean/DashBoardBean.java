package com.gather.jsfmatrix.core.view.managedBean;

import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.primefaces.component.dashboard.Dashboard;
import org.primefaces.component.panel.Panel;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.DashboardReorderEvent;
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
import com.gather.jsfmatrix.core.service.MatrixService;
import com.gather.jsfmatrix.core.view.BaseJSFView;
import com.gather.jsfmatrix.core.view.ViewType;
import com.gather.jsfmatrix.core.view.uiobject.UIDashBoard;
import com.gather.jsfmatrix.core.view.uiobject.UIObject;

public class DashBoardBean extends BaseJSFView {

    private DataSource ds;
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
        Logger.getLogger(DashBoardBean.class).info("INICIO CONSTRUCCION DASHBOARD");

        try {
            this.getMatrix().getApplications().clear();

            WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
            IUserBean lb = (IUserBean) ctx.getBean("userBean");

            if (lb != null && lb.getUser().getId() != null) {
                this.getService().getApplicationsService().resetParameter();
                this.getService().getApplicationsService().addParameter("1",
                                                                        lb.getUser().getId());
                this.getService().getApplicationsService().executeQuery();

                List<List<Object>> properties = this.getService().getApplicationsService().getResultSetasListofList(1);
                List<List<Object>> data = this.getService().getApplicationsService().getResultSetasListofList(2);

                if (Validator.validateList(data) &&
                        Validator.validateList(properties)) {

                    this.getMatrix().setProperties(properties);
                    for (List<Object> lo : data) {
                        if (Validator.validateLong(lo.get(0)) ||
                                Validator.validateInteger(lo.get(0))) {
                            IMatrixApplication ma = null;

                            if (ma == null) {
                                ma = MatrixApplicationFactory.createGeneric();
                            }

                            ma.getMatrixApplicationHandler().getApplicationModel().addProperty(Property.SESION,
                                                                                               lb.getUser().getId());
                            ma.getMatrixApplicationHandler().getApplicationModel().addProperty(Property.POSITION,
                                                                                               lo.get(0));
                            ma.getMatrixApplicationHandler().getApplicationModel().addProperty(Property.MATRIX_ID,
                                                                                               lo.get(1));
                            ma.getMatrixApplicationHandler().getApplicationModel().addProperty(Property.JAVA_ID,
                                                                                               lo.get(3));
                            ma.getMatrixApplicationHandler().getApplicationModel().addProperty(Property.INSTANCE,
                                                                                               lo.get(4));
                            ma.getMatrixApplicationHandler().getApplicationModel().addProperty(Property.TITLE,
                                                                                               lo.get(5));
                            ma.getMatrixApplicationHandler().getApplicationModel().addProperty(Property.BORRABLE,
                                                                                               lo.get(7));
                            ma.getMatrixApplicationHandler().getApplicationModel().addProperty(Property.VIEW_PATH,
                                                                                               lo.get(8));
                            ma.getMatrixApplicationHandler().getApplicationModel().addProperty(Property.ICON_PATH,
                                                                                               lo.get(9));
                            ma.getMatrixApplicationHandler().getApplicationModel().addProperty(Property.VIEW_TYPE,
                                                                                               properties.get(0).get(0));
                            this.getMatrix().getApplications().add(ma);

                        }
                    }
                }
            }
        } catch (BeansException e) {
            Logger.getLogger(DashBoardBean.class).error(e.getMessage());
        } catch (DataAccessException e) {
            Logger.getLogger(DashBoardBean.class).error(e.getMessage());
        } catch (Exception e) {
            Logger.getLogger(DashBoardBean.class).error(e.getMessage());
        }
    }

    @Override
    public ViewType getViewType() {
        return ViewType.CORE;
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
            Logger.getLogger(DashBoardBean.class).error(e.getMessage());
        }
    }

    public void handleReorder(DashboardReorderEvent event) {
        Logger.getLogger(DashBoardBean.class).info("INICIO EVENTO REORDER");

        try {
            Dashboard d = (Dashboard) event.getComponent();

            for (IMatrixApplication ma : this.getMatrix().getApplications()) {
                if (ma.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.JSF_CLIENT_ID).equals(event.getWidgetId())) {
                    this.getService().getMoveWidgetService().resetParameter();
                    this.getService().getMoveWidgetService().addParameter("1",
                                                                          ma.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.SESION));
                    this.getService().getMoveWidgetService().addParameter("2",
                                                                          ma.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.MATRIX_ID));

                    Integer pos = d.getModel().getColumnCount() *
                            event.getItemIndex() +
                            event.getColumnIndex() +
                            1;

                    this.getService().getMoveWidgetService().addParameter("3",
                                                                          pos);

                    this.getService().getMoveWidgetService().executeQuery();
                    this.populate(null);
                    return;
                }
            }
        } catch (DataAccessException e) {
            Logger.getLogger(DashBoardBean.class).error(e.getMessage());
        } catch (Exception e) {
            Logger.getLogger(DashBoardBean.class).error(e.getMessage());
        }

    }

    public void handleClose(CloseEvent event) {
        Logger.getLogger(DashBoardBean.class).info("INICIO EVENTO QUITAR WIDGET");
        Panel d = (Panel) event.getComponent();

        for (IMatrixApplication ma : this.getMatrix().getApplications()) {
            if (ma.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.JSF_CLIENT_ID).equals(d.getId())) {
                this.removeWidget(ma);
                return;
            }
        }
    }

    public void removeWidget(IMatrixApplication ma) {
        try {
            this.getService().getCloseWidgetService().resetParameter();
            this.getService().getCloseWidgetService().addParameter("1",
                                                                   ma.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.SESION));
            this.getService().getCloseWidgetService().addParameter("2",
                                                                   ma.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.MATRIX_ID));

            this.getService().getCloseWidgetService().executeQuery();

            this.populate(null);
        } catch (DataAccessException e) {
            Logger.getLogger(DashBoardBean.class).error(e.getMessage());
        } catch (Exception e) {
            Logger.getLogger(DashBoardBean.class).error(e.getMessage());
        }

        RequestContext.getCurrentInstance().addPartialUpdateTarget("mainDashBoard");
    }

    public void addMatrixApplication(IMatrixApplication ma,
                                     ViewType viewType) {
        Logger.getLogger(DashBoardBean.class).info("INICIO METODO addMatrixApplication");

        try {
            WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
            IUserBean lb = (IUserBean) ctx.getBean("userBean");

            if (lb != null &&
                    ma != null &&
                    viewType != null) {
                if (viewType.equals(ViewType.DOCK)) {
                    this.getService().getAddApplicationFromDockService().resetParameter();
                    this.getService().getAddApplicationFromDockService().addParameter("1",
                                                                                      lb.getUser().getId());
                    this.getService().getAddApplicationFromDockService().addParameter("2",
                                                                                      -1);
                    this.getService().getAddApplicationFromDockService().addParameter("3",
                                                                                      ma.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.PAQUETE));
                    this.getService().getAddApplicationFromDockService().executeQuery();
                } else if (viewType.equals(ViewType.STACK)) {
                    this.getService().getAddApplicationFromStackService().resetParameter();
                    this.getService().getAddApplicationFromStackService().addParameter("1",
                                                                                       lb.getUser().getId());
                    this.getService().getAddApplicationFromStackService().addParameter("2",
                                                                                       ma.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.CLAVE));

                    this.getService().getAddApplicationFromStackService().executeQuery();
                }

                this.populate(null);
            }
        } catch (BeansException e) {
            Logger.getLogger(DashBoardBean.class).error(e.getMessage());
        } catch (DataAccessException e) {
            Logger.getLogger(DashBoardBean.class).error(e.getMessage());
        } catch (Exception e) {
            Logger.getLogger(DashBoardBean.class).error(e.getMessage());
        }
    }

    @Override
    public void setUIObject(UIObject ui) {
        this.uiObject = ui;
    }
}
