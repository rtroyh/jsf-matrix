package com.gather.jsfmatrix.core.listener;

import com.gather.jsfmatrix.core.IMatrixApplication;
import com.gather.jsfmatrix.core.view.managedBean.PortalBean;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.AjaxBehaviorListener;

public class DashBoardPanelCloseListener implements AjaxBehaviorListener {
    private static final Logger LOG = Logger.getLogger(DashBoardPanelCloseListener.class);

    @Override
    public void processAjaxBehavior(AjaxBehaviorEvent event) throws
                                                             AbortProcessingException {
        LOG.info("INICIO EVENTO EDITAR TITULO WIDGET");

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        UIComponent source = event.getComponent();
        PortalBean us = (PortalBean) ctx.getBean("portalBean");
        us.getDashboardBean().removeWidget((IMatrixApplication) source.getAttributes().get("IMatrixApplicationModel"));
    }
}
