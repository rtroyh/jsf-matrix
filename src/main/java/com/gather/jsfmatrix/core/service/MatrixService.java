package com.gather.jsfmatrix.core.service;

import javax.sql.DataSource;

import com.gather.springcommons.services.AdvancedSSPService;

public class MatrixService {

    private DataSource ds;

    private AdvancedSSPService headerService;
    private AdvancedSSPService applicationsService;
    private AdvancedSSPService moveWidgetService;
    private AdvancedSSPService closeWidgetService;
    private AdvancedSSPService addApplicationFromDockService;
    private AdvancedSSPService addApplicationFromStackService;
    private AdvancedSSPService directoryService;
    private AdvancedSSPService titleRenameService;

    public MatrixService(DataSource ds) {
        super();
        this.ds = ds;
    }

    public synchronized AdvancedSSPService getTitleRenameService() {
        if (this.titleRenameService == null) {
            this.titleRenameService = new AdvancedSSPService(ds,
                                                             "PORTAL.MATRIZ_RENAME",
                                                             1);
        }

        return titleRenameService;
    }

    public synchronized AdvancedSSPService getHeaderService() {
        if (this.headerService == null) {
            this.headerService = new AdvancedSSPService(ds,
                                                        "PORTAL.HEADER",
                                                        1);
        }

        return this.headerService;
    }

    public synchronized AdvancedSSPService getDirectoryService() {
        if (this.directoryService == null) {
            this.directoryService = new AdvancedSSPService(ds,
                                                           "PORTAL.MATRIZ_SURF",
                                                           1);
        }

        return this.directoryService;
    }

    public synchronized AdvancedSSPService getAddApplicationFromDockService() {
        if (this.addApplicationFromDockService == null) {
            this.addApplicationFromDockService = new AdvancedSSPService(ds,
                                                                        "PORTAL.MATRIZ_ADD_APPL",
                                                                        1);
        }

        return this.addApplicationFromDockService;
    }

    public synchronized AdvancedSSPService getAddApplicationFromStackService() {
        if (this.addApplicationFromStackService == null) {
            this.addApplicationFromStackService = new AdvancedSSPService(ds,
                                                                         "PORTAL.STACK_MATRIZ",
                                                                         1);
        }

        return this.addApplicationFromStackService;
    }

    public synchronized AdvancedSSPService getMoveWidgetService() {
        if (this.moveWidgetService == null) {
            this.moveWidgetService = new AdvancedSSPService(ds,
                                                            "PORTAL.MATRIZ_MOVE",
                                                            1);
        }

        return this.moveWidgetService;
    }

    public synchronized AdvancedSSPService getCloseWidgetService() {
        if (this.closeWidgetService == null) {
            this.closeWidgetService = new AdvancedSSPService(ds,
                                                             "PORTAL.MATRIZ_DEL",
                                                             1);
        }

        return this.closeWidgetService;
    }

    public synchronized AdvancedSSPService getApplicationsService() {
        if (this.applicationsService == null) {
            this.applicationsService = new AdvancedSSPService(ds,
                                                              "PORTAL.MATRIZ",
                                                              2);
        }

        return this.applicationsService;
    }
}
