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

public class CMDeleteWidgetListener implements ActionListener {
    private static final Logger LOG = Logger.getLogger(CMDeleteWidgetListener.class);

    public void processAction(ActionEvent event) throws
                                                 AbortProcessingException {
        LOG.info("INICIO EVENTO BORRAR EN CONTEXT MENU");

        UIComponent source = event.getComponent();

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());

        PortalBean us = (PortalBean) ctx.getBean("portalBean");
        us.getDashboardBean().removeWidget((IMatrixApplication) source.getAttributes().get("IMatrixApplicationModel"));
        us.updateView();
    }
}