package com.gather.jsfmatrix.bean;

import com.gather.jsfmatrix.core.IMatrixApplicationHandler;
import com.gather.jsfmatrix.core.MatrixApplicationHandlerFactory;

/**
 * Created with IntelliJ IDEA.
 * $ Project: BCI_ComisionesAPV
 * User: rodrigotroy
 * Date: 9/24/15
 * Time: 10:41
 */
public abstract class BaseMatrixApplicationBean {
    private IMatrixApplicationHandler matrixApplicationHandler;

    public IMatrixApplicationHandler getMatrixApplicationHandler() {
        if (this.matrixApplicationHandler == null) {
            this.matrixApplicationHandler = MatrixApplicationHandlerFactory.createDefaultMatrixApplicationHandler();
        }

        return this.matrixApplicationHandler;
    }

    public boolean haveAccess() {
        return true;
    }

    public boolean showDock() {
        return false;
    }

    public boolean showStack() {
        return false;
    }

    public boolean updateBreadCrumb() {
        return true;
    }
}
