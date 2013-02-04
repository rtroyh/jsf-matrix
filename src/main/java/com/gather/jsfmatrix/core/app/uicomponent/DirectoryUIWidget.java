package com.gather.jsfmatrix.core.app.uicomponent;

import com.gather.gathercommons.util.Validator;
import com.gather.jsfmatrix.core.Ingredients;
import com.gather.jsfmatrix.core.Property;
import com.gather.jsfmatrix.core.model.ApplicationModelFactory;
import com.gather.jsfmatrix.core.model.IApplicationModel;
import com.gather.jsfmatrix.core.view.PrimeFacesUIComponentsFactory;
import com.gather.jsfmatrix.core.view.uiobject.UIJSFObject;
import org.primefaces.component.commandlink.CommandLink;

import javax.el.MethodExpression;
import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.MethodExpressionActionListener;
import java.util.Map;

public class DirectoryUIWidget implements UIJSFObject {

    private CommandLink botonIngresar;
    private HtmlPanelGrid component;
    private HtmlPanelGrid matrix;
    private IApplicationModel applicationModel;

    public IApplicationModel getApplicationModel() {
        if (this.applicationModel == null) {
            this.applicationModel = ApplicationModelFactory.createDefaultApplicationModel();
        }

        return this.applicationModel;
    }

    public HtmlPanelGrid getPanel() {
        if (this.component == null) {
            this.component = PrimeFacesUIComponentsFactory.createHtmlPanelGrid(FacesContext.getCurrentInstance());
            this.component.setId(FacesContext.getCurrentInstance().getViewRoot().createUniqueId() +
                                         java.util.Calendar.getInstance().getTimeInMillis());
            this.component.setStyle("margin: 0px;");
            this.component.setCellspacing("0");
            this.component.setCellpadding("0");
            this.component.setTransient(true);
            this.component.setColumns(1);

            if (Validator.validateInteger(this.getApplicationModel().getPropertyValue(Property.VIEW_TYPE),
                                          2)) {
                this.component.getChildren().add(this.getBotonIngresar());
            } else {
                this.component.getChildren().add(this.getMatrix());
                this.component.getChildren().add(this.getBotonIngresar());
            }
        }

        return this.component;
    }

    public void setPanel(HtmlPanelGrid panel) {
        this.component = panel;
    }

    public HtmlPanelGrid getMatrix() {
        if (this.matrix == null) {
            this.matrix = PrimeFacesUIComponentsFactory.createHtmlPanelGrid(FacesContext.getCurrentInstance());
            this.matrix.setId(FacesContext.getCurrentInstance().getViewRoot().createUniqueId() +
                                      java.util.Calendar.getInstance().getTimeInMillis());
            this.matrix.setStyle("height:140px; width: 280px;");
            this.matrix.setTransient(true);
            this.matrix.setColumns(3);
        }

        return this.matrix;
    }

    public void setMatrix(HtmlPanelGrid matrix) {
        this.matrix = matrix;
    }

    public CommandLink getBotonIngresar() {
        if (this.botonIngresar == null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            this.botonIngresar = PrimeFacesUIComponentsFactory.createCommandLink(fc);
            this.botonIngresar.setId("botonIngresar_" +
                                             fc.getViewRoot().createUniqueId() +
                                             "_" +
                                             java.util.Calendar.getInstance().getTimeInMillis());
            this.botonIngresar.setTransient(true);
            this.botonIngresar.setProcess("@this");
            this.botonIngresar.setUpdate(":myForm:principal");

            HtmlGraphicImage image = PrimeFacesUIComponentsFactory.createHtmlGraphicImage(fc);
            image.setId("image_DirectoryUIWidget_" +
                                fc.getViewRoot().createUniqueId() +
                                java.util.Calendar.getInstance().getTimeInMillis());
            image.setTitle(Validator.validateString(this.getApplicationModel().getPropertyValue(Property.TITLE))
                                   ? this.getApplicationModel().getPropertyValue(Property.TITLE).toString()
                                   : "");
            image.setTransient(true);

            ResourceHandler rh = fc.getApplication().getResourceHandler();
            if (Validator.validateInteger(this.getApplicationModel().getPropertyValue(Property.VIEW_TYPE),
                                          2)) {
                this.botonIngresar.setStyle("margin: 0; border:0; text-decoration: none;");
                image.setStyle("border:0; margin: 0; text-decoration: none;");
                Resource r = rh.createResource("images/" + this.getApplicationModel().getPropertyValue(Property.ICON_PATH),
                                               "gather");
                image.setUrl(r.getRequestPath());
            } else {
                this.botonIngresar.setStyle("margin: 0; float: right; border:0; text-decoration: none;");
                image.setStyle("border:0; margin: 0; width: 35px; height: 35px; text-decoration: none;");
                Resource r = rh.createResource("images/ingresar.png",
                                               "gather");
                image.setUrl(r.getRequestPath());
            }

            this.botonIngresar.getChildren().add(image);

            MethodExpression methodExpression = fc.getApplication().getExpressionFactory().createMethodExpression(fc.getELContext(),
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

    public void populate(Map<Ingredients, Object> recipe) {
        this.matrix = null;
        this.component = null;
    }

    public void resetState() {
    }

    public void setComponent(Object o) {
        this.component = (HtmlPanelGrid) o;
    }

    public UIComponent getComponent() {
        return this.getPanel();
    }

    @Override
    public void setComponent(UIComponent component) {
        this.component = (HtmlPanelGrid) component;
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
