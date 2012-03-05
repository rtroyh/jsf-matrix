package com.gather.jsfmatrix.core.listener;

import com.gather.jsfmatrix.core.IMatrixApplication;
import com.gather.jsfmatrix.core.Property;
import com.gather.jsfmatrix.core.view.managedBean.DashBoardBean;
import com.gather.jsfmatrix.core.view.managedBean.PortalBean;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.AjaxBehaviorListener;

/**
 * Created by IntelliJ IDEA.
 * User: rodrigotroy
 * Date: 3/5/12
 * Time: 7:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class DashBoardPanelCloseListener implements AjaxBehaviorListener {
    @Override
    public void processAjaxBehavior(AjaxBehaviorEvent event) throws
                                                             AbortProcessingException {
        Logger.getLogger(TitlePopupListener.class).info("INICIO EVENTO EDITAR TITULO WIDGET");

        UIComponent source = event.getComponent().getChildren().get(0);
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());

        PortalBean us = (PortalBean) ctx.getBean("portalBean");
        DashBoardBean db = us.getDashboardBean();

        for (IMatrixApplication ma : db.getMatrix().getApplications()) {
            if (ma.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.JSF_CLIENT_ID).equals(db.getSelectedPanel().getId())) {
                if (db.getTitleSelectedPanel() != null) {
                    db.getService().getTitleRenameService().resetParameter();
                    db.getService().getTitleRenameService().addParameter("1",
                                                                         ma.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.SESION));
                    db.getService().getTitleRenameService().addParameter("2",
                                                                         ma.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.MATRIX_ID));
                    db.getService().getTitleRenameService().addParameter("3",
                                                                         db.getTitleSelectedPanel());
                    db.getService().getTitleRenameService().executeQuery();
                    db.populate(null);
                    return;
                }
            }
        }
    }
}
