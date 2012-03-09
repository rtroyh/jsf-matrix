package com.gather.jsfmatrix.core.listener;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.apache.log4j.Logger;
import org.primefaces.component.menuitem.MenuItem;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.gather.jsfmatrix.core.IMatrixApplication;
import com.gather.jsfmatrix.core.view.ViewType;
import com.gather.jsfmatrix.core.view.managedBean.PortalBean;

class DirectoryListener implements ActionListener {
    private static final Logger LOG = Logger.getLogger(DirectoryListener.class);

    public void processAction(ActionEvent event) throws
                                                 AbortProcessingException {
        LOG.info("INICIO EVENTO CLICK EN DIRECTORIO");

        UIComponent source = event.getComponent();
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        PortalBean pb = (PortalBean) ctx.getBean("portalBean");
        MenuItem mi = (MenuItem) source;
        IMatrixApplication ma = (IMatrixApplication) mi.getAttributes().get("ma");

        pb.getDashboardBean().addMatrixApplication(ma,
                                                   ViewType.DOCK);
        pb.updateView();
    }
}