package com.gather.jsfmatrix.core.listener;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.gather.jsfmatrix.core.IMatrixApplication;
import com.gather.jsfmatrix.core.view.managedBean.PortalBean;
import com.gather.jsfmatrix.core.view.uicomponent.CustomMenuItem;

public class BreadListener implements ActionListener {

    public BreadListener() {
    }

    public void processAction(ActionEvent event) throws
                                                 AbortProcessingException {
        Logger.getLogger(BreadListener.class).info("INICIO EVENTO CLICK EN BREADCRUMB");

        UIComponent source = event.getComponent();
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());

        PortalBean pb = (PortalBean) ctx.getBean("portalBean");
        pb.updateThroughBreadCrumb((IMatrixApplication) ((CustomMenuItem) source).getUserObject());
    }
}