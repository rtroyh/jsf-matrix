package com.gather.jsfmatrix.core.app.uicomponent;

import com.gather.gathercommons.util.Validator;
import com.gather.jsfmatrix.core.Ingredients;
import com.gather.jsfmatrix.core.Property;
import com.gather.jsfmatrix.core.model.ApplicationModelFactory;
import com.gather.jsfmatrix.core.model.IApplicationModel;
import com.gather.jsfmatrix.core.view.PrimeFacesUIComponentsFactory;
import com.gather.jsfmatrix.core.view.uiobject.UIJSFObject;

import java.util.Date;
import java.util.Map;
import javax.el.MethodExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.MethodExpressionActionListener;

import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.commandlink.CommandLink;

public class CalendarUIWidget implements UIJSFObject {

    private IApplicationModel applicationModel;
    private HtmlPanelGrid component;
    private CommandLink botonIngresar;
    private Calendar calendar;
    private Date date = java.util.Calendar.getInstance().getTime();

    public HtmlPanelGrid getComponent() {
        if (this.component == null) {
            this.component = PrimeFacesUIComponentsFactory.createHtmlPanelGrid(FacesContext.getCurrentInstance());
            this.component.setId(FacesContext.getCurrentInstance().getViewRoot().createUniqueId() +
                                         java.util.Calendar.getInstance().getTimeInMillis());
            this.component.setStyle("width: 300px; spacing: 0; margin:0; border:0px solid red;");
            this.component.setTransient(true);
            this.component.setColumns(1);

            if (Validator.validateInteger(this.getApplicationModel().getPropertyValue(Property.VIEW_TYPE),
                                          2)) {
                this.component.setStyle("spacing: 0; margin:0; border:0px solid red;");
                this.component.getChildren().add(this.getBotonIngresar());
            } else {
                this.component.getChildren().add(this.getCalendar());
            }
        }

        return this.component;
    }

    public IApplicationModel getApplicationModel() {
        if (this.applicationModel == null) {
            this.applicationModel = ApplicationModelFactory.createDefaultApplicationModel();
        }

        return this.applicationModel;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UIComponent getCalendar() {
        if (this.calendar == null) {
            this.calendar = PrimeFacesUIComponentsFactory.createCalendar(FacesContext.getCurrentInstance());
            this.calendar.setId(FacesContext.getCurrentInstance().getViewRoot().createUniqueId());
            this.calendar.setMode("inline");
            this.calendar.setTransient(true);
            this.calendar.setValue(this.getDate());

            FacesContext fc = FacesContext.getCurrentInstance();
            MethodExpression methodExpression = fc.getApplication().getExpressionFactory().createMethodExpression(fc.getELContext(),
                                                                                                                  "#{portalBean.handleCalendar}",
                                                                                                                  null,
                                                                                                                  null);

           // this.calendar.setSelectListener(methodExpression);

        }

        return this.calendar;
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
}
