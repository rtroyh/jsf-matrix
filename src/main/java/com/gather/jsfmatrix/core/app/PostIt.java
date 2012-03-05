package com.gather.jsfmatrix.core.app;

import javax.faces.event.ActionEvent;

import com.gather.jsfmatrix.core.IMatrixApplication;
import com.gather.jsfmatrix.core.IMatrixApplicationHandler;
import com.gather.jsfmatrix.core.MatrixApplicationHandlerFactory;
import com.gather.jsfmatrix.core.Property;
import com.gather.jsfmatrix.core.app.view.PostItWidgetView;

public class PostIt implements IMatrixApplication {

    private IMatrixApplicationHandler matrixApplicationHandler;
    private String text;
    private String titulo = "";

    @Override
    public IMatrixApplicationHandler getMatrixApplicationHandler() {
        if (this.matrixApplicationHandler == null) {
            this.matrixApplicationHandler = MatrixApplicationHandlerFactory.createDefaultMatrixApplicationHandler();
            this.matrixApplicationHandler.getIApplicationView().addView("0",
                                                                        new PostItWidgetView());
        }

        return this.matrixApplicationHandler;
    }

    @Override
    public void onStart() {
        this.getMatrixApplicationHandler().setURL(this.getMatrixApplicationHandler().getPropertyValue(Property.VIEW_PATH).toString() +
                                                          "/app");
    }

    public String getTitulo() { //EJEMPLO DE USO DE PARAMETROS INYECTADOS POR EL PORTAL
        return titulo +
                this.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.TITLE).toString();
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void handleUpdate(ActionEvent event) {
    }

    public boolean haveAccess() {
        return true;
    }

    @Override
    public boolean showDock() {
        return false;
    }

    @Override
    public boolean showStack() {
        return false;
    }

    @Override
    public boolean updateBreadCrumb() {
        return true;
    }
}
