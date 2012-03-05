package com.gather.jsfmatrix.core;

import com.gather.jsfmatrix.core.app.Generic;

public class MatrixApplicationFactory {

    public static IMatrixApplication createMatrixApplication() {
        return MatrixApplicationFactory.createGeneric();
    }

    public static IMatrixApplication createGeneric() {
        return new Generic();
    }
}
