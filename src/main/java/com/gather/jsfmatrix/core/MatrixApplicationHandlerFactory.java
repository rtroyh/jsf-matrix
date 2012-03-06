package com.gather.jsfmatrix.core;

import com.gather.jsfmatrix.core.model.ApplicationModelFactory;
import com.gather.jsfmatrix.core.view.DefaultWidgetView;

public class MatrixApplicationHandlerFactory {

    public static IMatrixApplicationHandler createDefaultMatrixApplicationHandler() {
        IMatrixApplicationHandler ma = new DefaultMatrixApplicationHandler(ApplicationModelFactory.createDefaultApplicationModel());

        ma.getIApplicationView().addView("0",
                                         new DefaultWidgetView());
        return ma;
    }
}
