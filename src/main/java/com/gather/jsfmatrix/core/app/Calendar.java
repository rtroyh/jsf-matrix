package com.gather.jsfmatrix.core.app;

import com.gather.jsfmatrix.core.IMatrixApplication;
import com.gather.jsfmatrix.core.IMatrixApplicationHandler;
import com.gather.jsfmatrix.core.MatrixApplicationHandlerFactory;
import com.gather.jsfmatrix.core.app.view.CalendarWidgetView;

public class Calendar implements IMatrixApplication {

    //CODIGO BASE**********
    private IMatrixApplicationHandler matrixApplicationHandler;

    public Calendar() {
        super();
    }

    @Override
    public IMatrixApplicationHandler getMatrixApplicationHandler() {
        if (this.matrixApplicationHandler == null) {
            this.matrixApplicationHandler = MatrixApplicationHandlerFactory.createDefaultMatrixApplicationHandler();
            this.matrixApplicationHandler.getIApplicationView().addView("0",
                                                                        new CalendarWidgetView());
        }

        return this.matrixApplicationHandler;
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
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
