package com.gather.jsfmatrix.core.app;

import com.gather.jsfmatrix.core.IMatrixApplication;
import com.gather.jsfmatrix.core.IMatrixApplicationHandler;
import com.gather.jsfmatrix.core.MatrixApplicationHandlerFactory;

public class Generic implements IMatrixApplication {

    private IMatrixApplicationHandler matrixApplicationHandler;

    public Generic() {
        super();
    }

    @Override
    public IMatrixApplicationHandler getMatrixApplicationHandler() {
        if (this.matrixApplicationHandler == null) {
            this.matrixApplicationHandler = MatrixApplicationHandlerFactory.createDefaultMatrixApplicationHandler();
            this.matrixApplicationHandler.getIApplicationView().removeView("0");
        }

        return this.matrixApplicationHandler;
    }

    @Override
    public void onStart() {
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
