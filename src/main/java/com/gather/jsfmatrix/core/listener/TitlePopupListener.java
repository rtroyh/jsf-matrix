package com.gather.jsfmatrix.core.listener;

import com.gather.jsfmatrix.core.view.managedBean.PortalBean;
import org.apache.log4j.Logger;
import org.primefaces.component.panel.Panel;
import org.primefaces.context.RequestContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.AjaxBehaviorListener;

public class TitlePopupListener implements AjaxBehaviorListener {
    private static final Logger LOG = Logger.getLogger(TitlePopupListener.class);

    @Override
    public void processAjaxBehavior(AjaxBehaviorEvent event) throws
                                                             AbortProcessingException {
        LOG.info("INICIO EVENTO EDITAR TITULO WIDGET");

        UIComponent source = event.getComponent().getChildren().get(0);
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());

        PortalBean us = (PortalBean) ctx.getBean("portalBean");

        UIComponent panel = event.getComponent().getParent();
        while (!(panel instanceof Panel)) {
            panel = panel.getParent();
        }

        us.getDashboardBean().setSelectedPanel((Panel) panel);

        if (source instanceof HtmlOutputText && ((HtmlOutputText) source).getTitle() != null) {
            us.getDashboardBean().setTitleSelectedPanel(((HtmlOutputText) source).getTitle());
        }

        RequestContext.getCurrentInstance().update("innerTitleDialog");
    }

}
