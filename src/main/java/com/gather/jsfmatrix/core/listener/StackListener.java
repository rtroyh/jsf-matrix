package com.gather.jsfmatrix.core.listener;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.gather.jsfmatrix.core.IMatrixApplication;
import com.gather.jsfmatrix.core.view.ViewType;
import com.gather.jsfmatrix.core.view.managedBean.PortalBean;
import com.gather.jsfmatrix.core.view.uicomponent.CustomMenuItem;

public class StackListener implements ActionListener {

    public StackListener() {
    }

    public void processAction(ActionEvent event) throws
                                                 AbortProcessingException {
        Logger.getLogger(StackListener.class).info("INICIO EVENTO CLICK EN STACK");
  }
}