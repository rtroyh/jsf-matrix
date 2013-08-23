package com.gather.jsfmatrix.core.listener;

import com.gather.jsfmatrix.core.IMatrixApplication;
import com.gather.jsfmatrix.core.view.ViewType;
import com.gather.jsfmatrix.core.view.managedBean.DashBoardBean;
import com.gather.jsfmatrix.core.view.managedBean.PortalBean;
import org.apache.log4j.Logger;
import org.primefaces.component.menuitem.MenuItem;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

public class DockListener implements ActionListener {
    private static final Logger LOG = Logger.getLogger(DockListener.class);

    public void processAction(ActionEvent event) throws
                                                 AbortProcessingException {
        LOG.info("INICIO EVENTO CLICK EN DOCK");

        UIComponent source = event.getComponent();
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());

        DashBoardBean us = (DashBoardBean) ctx.getBean("dashboardBean");
        PortalBean pb = (PortalBean) ctx.getBean("portalBean");
        MenuItem mi = (MenuItem) source;
        IMatrixApplication ma = (IMatrixApplication) mi.getAttributes().get("ma");

        us.addMatrixApplication(ma,
                                ViewType.DOCK);
        pb.updateView();
    }
}