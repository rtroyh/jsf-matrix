package com.gather.jsfmatrix.core.view.managedBean;

import com.gather.jsfmatrix.core.IMatrixApplication;
import com.gather.jsfmatrix.core.Property;
import com.gather.jsfmatrix.core.model.IApplicationModel;
import com.gather.jsfmatrix.util.MapPropertyUtil;
import com.gather.jsfspringcommons.utils.BeanUtil;
import org.apache.log4j.Logger;
import org.primefaces.component.panel.Panel;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

public class PortalBean implements INavigation {
    private static final Logger LOG = Logger.getLogger(PortalBean.class);

    private DockBean dockBean;
    private DashBoardBean dashboardBean;
    private BreadCrumbBean breadCrumbBean;

    private boolean renderDock = true;
    private boolean renderStack = true;

    private String body = "desktop";

    public PortalBean(final DockBean dockBean,
                      final BreadCrumbBean breadCrumbBean,
                      final DashBoardBean dashboardBean) {
        super();
        this.dockBean = dockBean;
        this.dashboardBean = dashboardBean;
        this.breadCrumbBean = breadCrumbBean;
    }

    public boolean isRenderStack() {
        return renderStack;
    }

    public void setRenderStack(boolean renderStack) {
        this.renderStack = renderStack;
    }

    public boolean isRenderDock() {
        return renderDock;
    }

    public void setRenderDock(boolean renderDock) {
        this.renderDock = renderDock;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public void setBody(String body) {
        this.body = body;
    }

    public void onClickHeader(ActionEvent event) {
        LOG.info("INICIO EVENTO IR A HOME");

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        IUserBean userBean = (IUserBean) ctx.getBean("userBean");

        this.updateThroughBreadCrumb(userBean.getUser().getId(),
                                     0,
                                     1);
    }

    public void updateThroughBreadCrumbHome(Object sesion) {
        LOG.info("INICIO METODO SURF");

        try {
            this.dashboardBean.updateThroughBreadCrumb(sesion,
                                                       0,
                                                       "");

            this.setBody("desktop");
            this.renderDock = true;
            this.renderStack = true;
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    private void updateThroughBreadCrumb(Object sesion,
                                         Object matrixID,
                                         Object javaID) {
        LOG.info("INICIO METODO SURF");

        try {
            this.dashboardBean.updateThroughBreadCrumb(sesion,
                                                       matrixID,
                                                       "");

            this.breadCrumbBean.populate(null);

            if (javaID.equals(1)) {
                this.dashboardBean.populate(null);

                this.setBody("desktop");
                this.renderDock = true;
                this.renderStack = true;
            }
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

        this.updateView();
    }

    public void updateThroughBreadCrumb(IMatrixApplication ma) {
        LOG.info("INICIO METODO SURF");

        if (ma != null && ma.updateBreadCrumb()) {
            this.updateThroughBreadCrumb(ma.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.SESION),
                                         ma.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.MATRIX_ID),
                                         ma.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.JAVA_ID));
        }
    }

    public void updateView() {
        LOG.info("INICIO LLAMADO AL REQUESTCONTEXT PARA ACTUALIZAR LA VISTA");

        try {
            RequestContext.getCurrentInstance().update("myFormBread");
            RequestContext.getCurrentInstance().update("myFormDock");
            RequestContext.getCurrentInstance().update("myForm");
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    public void handleTitleChange(ActionEvent event) {
        LOG.info("INICIO EVENTO CLICK EN GUARDAR NUEVO TITULO");

        for (IMatrixApplication ma : this.dashboardBean.getMatrix().getApplications()) {
            IApplicationModel model = ma.getMatrixApplicationHandler().getApplicationModel();

            if (model.getPropertyValue(Property.JSF_CLIENT_ID).equals(this.dashboardBean.getSelectedPanel().getId())) {
                if (this.dashboardBean.getTitleSelectedPanel() != null) {
                    this.dashboardBean.titleRename(model.getPropertyValue(Property.SESION),
                                                   model.getPropertyValue(Property.MATRIX_ID),
                                                   this.dashboardBean.getTitleSelectedPanel());
                    this.dashboardBean.populate(null);
                    return;
                }
            }
        }
    }

    public void handleView(ActionEvent event) { //LLAMADO POR EL COMMANDLINK "INGRESAR", CREADO EN EL UIDASHBOARD.
        LOG.info("INICIO EVENTO HANDLE VIEW");

        Panel d;
        if (event.getComponent().getParent() instanceof Panel) {
            d = (Panel) event.getComponent().getParent();
        } else if (event.getComponent().getParent().getParent() instanceof Panel) {
            d = (Panel) event.getComponent().getParent().getParent();
        } else {
            LOG.info("PANEL NO ENCONTRADO");
            return;
        }

        for (IMatrixApplication ma : this.dashboardBean.getMatrix().getApplications()) {
            if (ma.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.JSF_CLIENT_ID).equals(d.getId())) {
                this.updateThroughBreadCrumb(ma);

                if (ma.getMatrixApplicationHandler().getPropertyValue(Property.VIEW_PATH) != null) {
                    try {
                        if (BeanUtil.getBean(FacesContext.getCurrentInstance(),
                                             ma.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.VIEW_PATH).toString()) != null) {
                            IMatrixApplication lb = (IMatrixApplication) BeanUtil.getBean(FacesContext.getCurrentInstance(),
                                                                                          ma.getMatrixApplicationHandler().getPropertyValue(Property.VIEW_PATH).toString());
                            lb.getMatrixApplicationHandler().getApplicationModel().getPropertyMap().clear();

                            MapPropertyUtil.copyProperties(ma.getMatrixApplicationHandler().getApplicationModel().getPropertyMap(),
                                                           lb.getMatrixApplicationHandler().getApplicationModel().getPropertyMap());

                            this.setRenderDock(lb.showDock());
                            this.setRenderStack(lb.showStack());

                            LOG.info("LLAMANDO A METODO ONSTART ESPECIFICO DEL BEAN: " + ma.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.VIEW_PATH).toString());
                            lb.onStart();
                        }
                    } catch (BeanCreationException e) {
                        LOG.warn(e.getMessage());
                    } catch (NoSuchBeanDefinitionException e) {
                        LOG.warn(e.getMessage());
                    } catch (Exception e) {
                        LOG.error(e.getMessage());
                    }

                    this.updateView();
                }

                return;
            }
        }
    }

    @SuppressWarnings("unused")
    @PostConstruct
    private void build() {
        LOG.info("INICIO CARGA COMPONENTES MATRIZ");

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        IUserBean lb = (IUserBean) ctx.getBean("userBean");

        this.updateThroughBreadCrumbHome(lb.getUser().getId());
        this.breadCrumbBean.populate(null);
        this.dockBean.populate(null);
        this.dashboardBean.populate(null);

        this.updateView();
    }
}
