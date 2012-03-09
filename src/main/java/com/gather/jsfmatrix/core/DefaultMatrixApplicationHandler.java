package com.gather.jsfmatrix.core;

import javax.faces.context.FacesContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.gather.jsfmatrix.core.model.ApplicationModelFactory;
import com.gather.jsfmatrix.core.model.IApplicationModel;
import com.gather.jsfmatrix.core.view.DefaultApplicationView;
import com.gather.jsfmatrix.core.view.IApplicationView;
import com.gather.jsfmatrix.core.view.managedBean.INavigation;

class DefaultMatrixApplicationHandler implements IMatrixApplicationHandler {

    private String lastGenerateInstanceMessage = "";
    private IApplicationModel applicationModel;
    private IApplicationView applicationView;

    public DefaultMatrixApplicationHandler(IApplicationModel defaultApplicationModel) {
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

        INavigation us = (INavigation) ctx.getBean("portalBean");
        us.setBody("apps/" + url);
        us.updateView();
    }
}
