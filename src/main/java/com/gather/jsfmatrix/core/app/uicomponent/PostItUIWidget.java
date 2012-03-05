package com.gather.jsfmatrix.core.app.uicomponent;

import java.util.Map;

import javax.el.MethodExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.AjaxBehaviorListener;
import javax.faces.event.MethodExpressionActionListener;

import org.apache.log4j.Logger;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.commandlink.CommandLink;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.gather.gathercommons.util.Validator;
import com.gather.jsfmatrix.core.Ingredients;
import com.gather.jsfmatrix.core.Property;
import com.gather.jsfmatrix.core.app.PostIt;
import com.gather.jsfmatrix.core.model.ApplicationModelFactory;
import com.gather.jsfmatrix.core.model.IApplicationModel;
import com.gather.jsfmatrix.core.view.PrimeFacesUIComponentsFactory;
import com.gather.jsfmatrix.core.view.managedBean.PortalBean;
import com.gather.jsfmatrix.core.view.uiobject.UIJSFObject;

public class PostItUIWidget implements UIJSFObject {

    private CommandButton botonGuardar;
    private CommandLink botonIngresar;
    private HtmlPanelGrid component;
    private HtmlInputTextarea inputTextArea;
    private IApplicationModel applicationModel;

    public IApplicationModel getApplicationModel() {
        if (this.applicationModel == null) {
            this.applicationModel = ApplicationModelFactory.createDefaultApplicationModel();
        }

        return this.applicationModel;
    }

    public HtmlInputTextarea getInputTextArea() {
        if (this.inputTextArea == null) {
            this.inputTextArea = PrimeFacesUIComponentsFactory.createHtmlInputTextarea(FacesContext.getCurrentInstance());
            this.inputTextArea.setTransient(true);
            this.inputTextArea.setRows(8);
            this.inputTextArea.setCols(50);
        }

        return this.inputTextArea;
    }

    @SuppressWarnings("unused")
    private class ShowAppListener implements AjaxBehaviorListener {

        @Override
        public void processAjaxBehavior(AjaxBehaviorEvent event) throws
                                                                 AbortProcessingException {
            Logger.getLogger(ShowAppListener.class).info("INICIO EVENTO CLICK EN DOCK");

            WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
            PortalBean us = (PortalBean) ctx.getBean("portalBean");

            us.setBody("./app/" +
                               getApplicationModel().getPropertyValue(Property.VIEW_PATH).toString() +
                               "/app");
            us.updateView();
        }
    }

    public void setInputTextArea(HtmlInputTextarea component) {
        this.inputTextArea = (HtmlInputTextarea) component;
    }

    public HtmlPanelGrid getComponent() {
        if (this.component == null) {
            this.component = PrimeFacesUIComponentsFactory.createHtmlPanelGrid(FacesContext.getCurrentInstance());
            this.component.setId(FacesContext.getCurrentInstance().getViewRoot().createUniqueId() +
                                         java.util.Calendar.getInstance().getTimeInMillis());
            this.component.setStyle("spacing: 0; margin:0; border:0;");
            this.component.setTransient(true);
            this.component.setColumns(1);

            if (Validator.validateInteger(this.getApplicationModel().getPropertyValue(Property.VIEW_TYPE),
                                          2)) {
                this.component.getChildren().add(this.getBotonIngresar());
            } else {
                this.component.getChildren().add(this.getInputTextArea());
                this.component.getChildren().add(this.getBotonGuardar());
            }
        }

        return this.component;
    }

    public CommandLink getBotonIngresar() {
        if (this.botonIngresar == null) {
            this.botonIngresar = PrimeFacesUIComponentsFactory.createCommandLink(FacesContext.getCurrentInstance());
            this.botonIngresar.setId("botonIngresar_" +
                                             FacesContext.getCurrentInstance().getViewRoot().createUniqueId() +
                                             "_" +
                                             java.util.Calendar.getInstance().getTimeInMillis());
            this.botonIngresar.setTransient(true);
            this.botonIngresar.setStyle("float: right; border:0; text-decoration: none;");
            this.botonIngresar.setOnstart("cargaDialogWV.show();");
            this.botonIngresar.setOncomplete("cargaDialogWV.hide();");

            HtmlGraphicImage image = PrimeFacesUIComponentsFactory.createHtmlGraphicImage(FacesContext.getCurrentInstance());
            image.setId("image_DirectoryUIWidget_" +
                                FacesContext.getCurrentInstance().getViewRoot().createUniqueId() +
                                java.util.Calendar.getInstance().getTimeInMillis());

            if (Validator.validateInteger(this.getApplicationModel().getPropertyValue(Property.VIEW_TYPE),
                                          2)) {
                image.setStyle("border:0; text-decoration: none;");
                image.setUrl("./images/" +
                                     this.getApplicationModel().getPropertyValue(Property.ICON_PATH));
                image.setTitle(Validator.validateString(this.getApplicationModel().getPropertyValue(Property.TITLE))
                                       ? this.getApplicationModel().getPropertyValue(Property.TITLE).toString()
                                       : "");
            } else {
                image.setStyle("width: 40px; height: 40px; border:0; text-decoration: none;");
                image.setUrl("./images/ingresar.png");
                image.setTitle("Ingresar");
            }

            image.setTransient(true);

            this.botonIngresar.getChildren().add(image);

            MethodExpression methodExpression = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().createMethodExpression(
                    FacesContext.getCurrentInstance().getELContext(),
                    "#{portalBean.handleView}",
                    null,
                    new Class[]{ActionEvent.class});

            this.botonIngresar.addActionListener(new MethodExpressionActionListener(methodExpression));
        }

        return this.botonIngresar;
    }

    public void setBotonIngresar(CommandLink botonIngresar) {
        this.botonIngresar = botonIngresar;
    }

    public void setPanel(HtmlPanelGrid panel) {
        this.component = panel;
    }

    public CommandButton getBotonGuardar() {
        if (this.botonGuardar == null) {
            this.botonGuardar = PrimeFacesUIComponentsFactory.createCommandButton(FacesContext.getCurrentInstance());
            this.botonGuardar.setTransient(true);
            this.botonGuardar.setValue("Guardar");
            this.botonGuardar.addActionListener(new GuardarListener());
        }

        return this.botonGuardar;
    }

    private final class GuardarListener implements ActionListener {

        public void processAction(ActionEvent event) throws
                                                     AbortProcessingException {
            Logger.getLogger(GuardarListener.class).info("INICIO EVENTO GUARDAR POSTIT");
            WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
            PostIt pb = (PostIt) ctx.getBean("postit");

            try {

            } catch (DataAccessException e) {
                Logger.getLogger(PostItUIWidget.class).error(e.getMessage());
            } catch (Exception e) {
                Logger.getLogger(PostItUIWidget.class).error(e.getMessage());
            }
        }
    }

    public void setBotonGuardar(CommandButton crearEjecutivo) {
        this.botonGuardar = crearEjecutivo;
    }

    @Override
    public void populate(Map<Ingredients, Object> recipe) {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        PostIt pb = (PostIt) ctx.getBean("postit");

    }

    @Override
    public void resetState() {
        // TODO Auto-generated method stub
    }

    @Override
    public void setComponent(UIComponent component) {
        this.component = (HtmlPanelGrid) component;

    }

    @Override
    public void setComponent(Object o) {
        this.component = (HtmlPanelGrid) o;
    }

    @Override
    public Object getPropertyValue(Property property) {
        return this.getApplicationModel().getPropertyValue(property);
    }

    @Override
    public void setApplicationModel(IApplicationModel applicationModel) {
        this.applicationModel = applicationModel;
    }
}
