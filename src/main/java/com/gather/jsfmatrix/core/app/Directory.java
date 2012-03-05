package com.gather.jsfmatrix.core.app;

import com.gather.jsfmatrix.core.IMatrixApplication;
import com.gather.jsfmatrix.core.IMatrixApplicationHandler;
import com.gather.jsfmatrix.core.MatrixApplicationHandlerFactory;

public class Directory implements IMatrixApplication {

    private IMatrixApplicationHandler matrixApplicationHandler;

    public Directory() {
        super();
    }

    @Override
    public IMatrixApplicationHandler getMatrixApplicationHandler() {
        if (this.matrixApplicationHandler == null) {
            this.matrixApplicationHandler = MatrixApplicationHandlerFactory.createDefaultMatrixApplicationHandler();
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
        return true;
    }

    @Override
    public boolean showStack() {
        return true;
    }

    @Override
    public boolean updateBreadCrumb() {
        return true;
    }
}