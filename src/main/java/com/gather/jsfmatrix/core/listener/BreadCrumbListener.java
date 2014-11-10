package com.gather.jsfmatrix.core.listener;

import com.gather.jsfmatrix.core.IMatrixApplication;
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

public class BreadCrumbListener implements ActionListener {
    private static final Logger LOG = Logger.getLogger(BreadCrumbListener.class);

    public void processAction(ActionEvent event) throws
                                                 AbortProcessingException {
        LOG.info("INICIO EVENTO CLICK EN BREADCRUMB");

        UIComponent source = event.getComponent();
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());

        PortalBean pb = (PortalBean) ctx.getBean("portalBean");
        MenuItem mi = (MenuItem) source;
        IMatrixApplication ma = (IMatrixApplication) mi.getAttributes().get("ma");

        pb.updateThroughBreadCrumb(ma);
    }
}