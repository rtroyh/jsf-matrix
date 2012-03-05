package com.gather.jsfmatrix.core;

public interface IMatrixApplication {

    public IMatrixApplicationHandler getMatrixApplicationHandler();

    public void onStart();

    public boolean haveAccess();

    public boolean showDock();

    public boolean showStack();

    public boolean updateBreadCrumb();
}
