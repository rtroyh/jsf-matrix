package com.gather.jsfmatrix.core.listener;

import com.gather.jsfmatrix.core.IMatrixApplication;
import com.gather.jsfmatrix.core.view.managedBean.DashBoardBean;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

public class CMDeleteWidgetListener implements ActionListener {
    private static final Logger LOG = Logger.getLogger(CMDeleteWidgetListener.class);

    public void processAction(ActionEvent event) throws
                                                 AbortProcessingException {
        LOG.info("INICIO EVENTO BORRAR EN CONTEXT MENU");

        UIComponent source = event.getComponent();

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());

        DashBoardBean us = (DashBoardBean) ctx.getBean("dashboardBean");
        us.removeWidget((IMatrixApplication) source.getAttributes().get("IMatrixApplicationModel"));
    }
}