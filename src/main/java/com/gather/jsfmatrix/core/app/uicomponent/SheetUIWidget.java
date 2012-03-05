package com.gather.jsfmatrix.core.app.uicomponent;

import java.util.Map;

import javax.el.MethodExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.MethodExpressionActionListener;

import org.primefaces.component.commandlink.CommandLink;

import com.gather.gathercommons.util.Validator;
import com.gather.jsfmatrix.core.Ingredients;
import com.gather.jsfmatrix.core.Property;
import com.gather.jsfmatrix.core.model.ApplicationModelFactory;
import com.gather.jsfmatrix.core.model.IApplicationModel;
import com.gather.jsfmatrix.core.view.PrimeFacesUIComponentsFactory;
import com.gather.jsfmatrix.core.view.uiobject.UIJSFObject;


public class SheetUIWidget implements UIJSFObject {

    private IApplicationModel applicationModel;
    private HtmlPanelGrid component;
    private CommandLink botonIngresar;

    public HtmlPanelGrid getComponent() {
        if (this.component == null) {
            this.component = PrimeFacesUIComponentsFactory.createHtmlPanelGrid(FacesContext.getCurrentInstance());
            this.component.setId(FacesContext.getCurrentInstance().getViewRoot().createUniqueId() +
                                         java.util.Calendar.getInstance().getTimeInMillis());
            this.component.setStyle("spacing: 0; margin:0; border:0px solid red;");
            this.component.setTransient(true);
            this.component.setColumns(1);
            this.component.getChildren().add(this.getBotonIngresar());
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
            image.setId(FacesContext.getCurrentInstance().getViewRoot().createUniqueId() +
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

    public IApplicationModel getApplicationModel() {
        if (this.applicationModel == null) {
            this.applicationModel = ApplicationModelFactory.createDefaultApplicationModel();
        }

        return this.applicationModel;
    }

    @Override
    public void setComponent(UIComponent component) {
        this.component = (HtmlPanelGrid) component;
    }

    @Override
    public void setComponent(Object o) {
        this.component = (HtmlPanelGrid) component;
    }

    @Override
    public void populate(Map<Ingredients, Object> recipe) {
        // TODO Auto-generated method stub
    }

    @Override
    public void resetState() {
        // TODO Auto-generated method stub
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
