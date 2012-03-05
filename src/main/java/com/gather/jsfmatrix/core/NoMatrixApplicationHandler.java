package com.gather.jsfmatrix.core;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.gather.jsfmatrix.core.model.ApplicationModelFactory;
import com.gather.jsfmatrix.core.model.IApplicationModel;
import com.gather.jsfmatrix.core.view.DefaultApplicationView;
import com.gather.jsfmatrix.core.view.IApplicationView;
import com.gather.jsfmatrix.core.view.managedBean.INavigation;
import com.gather.jsfmatrix.core.view.managedBean.PortalBean;

public class NoMatrixApplicationHandler implements IMatrixApplicationHandler {

    private String lastGenerateInstanceMessage = "";
    private IApplicationModel applicationModel;
    private IApplicationView applicationView;

    public NoMatrixApplicationHandler(IApplicationModel defaultApplicationModel) {
        super();
        this.applicationModel = defaultApplicationModel;
    }

    @Override
    public IApplicationModel getApplicationModel() {
        if (this.applicationModel == null) {
            this.applicationModel = ApplicationModelFactory.createDefaultApplicationModel();
        }

        return this.applicationModel;
    }

    @Override
    public Object getPropertyValue(Property property) {
        return this.getApplicationModel().getPropertyValue(property);
    }

    @Override
    public IApplicationView getIApplicationView() {
        if (this.applicationView == null) {
            this.applicationView = new DefaultApplicationView();
        }

        return this.applicationView;
    }

    @Override
    public void setApplicationModel(IApplicationModel applicationModel) {
        this.applicationModel = applicationModel;

    }

    @Override
    public void setURL(String url) {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());

        INavigation us = (INavigation) ctx.getBean("navigationBean");
        us.setBody("app/" +
                           url);
        us.updateView();
    }

    @Override
    public void generateInstance(Object... parameters) {

    }

    @Override
    public String getLastGenerateInstanceMessage() {
        return this.lastGenerateInstanceMessage;
    }

    public void setLastGenerateInstanceMessage(String lastGenerateInstanceMessage) {
        this.lastGenerateInstanceMessage = lastGenerateInstanceMessage;
    }
}
