package com.gather.jsfmatrix.core.service;

import com.gather.springcommons.services.AdvancedSSPService;
import com.gather.springcommons.services.IResultSetProvider;
import org.springframework.dao.DataAccessException;

import javax.sql.DataSource;

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

    private AdvancedSSPService getTitleRenameService() {
        if (this.titleRenameService == null) {
            this.titleRenameService = new AdvancedSSPService(ds,
                                                             "PORTAL.MATRIZ_RENAME",
                                                             1);
        }

        return titleRenameService;
    }

    public final IResultSetProvider titleRename(Object sesion,
                                                Object matrixID,
                                                Object title) throws
                                                              DataAccessException {

        this.getTitleRenameService().resetParameter();
        this.getTitleRenameService().addParameter("1",
                                                  sesion);
        this.getTitleRenameService().addParameter("2",
                                                  matrixID);
        this.getTitleRenameService().addParameter("3",
                                                  title);
        this.getTitleRenameService().executeQuery();

        return this.getTitleRenameService();
    }

    private AdvancedSSPService getHeaderService() {
        if (this.headerService == null) {
            this.headerService = new AdvancedSSPService(ds,
                                                        "PORTAL.HEADER",
                                                        1);
        }

        return this.headerService;
    }

    public final IResultSetProvider getHeader(Object sesion) throws
                                                             DataAccessException {
        this.getHeaderService().resetParameter();
        this.getHeaderService().addParameter("1",
                                             sesion);
        this.getHeaderService().executeQuery();

        return this.getHeaderService();
    }

    private AdvancedSSPService getDirectoryService() {
        if (this.directoryService == null) {
            this.directoryService = new AdvancedSSPService(ds,
                                                           "PORTAL.MATRIZ_SURF",
                                                           1);
        }

        return this.directoryService;
    }

    public final IResultSetProvider updateThroughBreadCrumb(Object sesion,
                                                            Object matrixID,
                                                            Object javaID) throws
                                                                           DataAccessException {
        this.getDirectoryService().resetParameter();
        this.getDirectoryService().addParameter("1",
                                                sesion);
        this.getDirectoryService().addParameter("2",
                                                matrixID);
        this.getDirectoryService().addParameter("3",
                                                javaID);
        this.getDirectoryService().executeQuery();

        return this.getDirectoryService();
    }

    private AdvancedSSPService getAddApplicationFromDockService() {
        if (this.addApplicationFromDockService == null) {
            this.addApplicationFromDockService = new AdvancedSSPService(ds,
                                                                        "PORTAL.MATRIZ_ADD_APPL",
                                                                        1);
        }

        return this.addApplicationFromDockService;
    }

    public final IResultSetProvider addApplicationFromDock(Object sesion,
                                                           Object matrixID,
                                                           Object paquete) throws
                                                                           DataAccessException {
        this.getAddApplicationFromDockService().resetParameter();
        this.getAddApplicationFromDockService().addParameter("1",
                                                             sesion);
        this.getAddApplicationFromDockService().addParameter("2",
                                                             matrixID);
        this.getAddApplicationFromDockService().addParameter("3",
                                                             paquete);
        this.getAddApplicationFromDockService().executeQuery();

        return this.getAddApplicationFromDockService();
    }


    private AdvancedSSPService getAddApplicationFromStackService() {
        if (this.addApplicationFromStackService == null) {
            this.addApplicationFromStackService = new AdvancedSSPService(ds,
                                                                         "PORTAL.STACK_MATRIZ",
                                                                         1);
        }

        return this.addApplicationFromStackService;
    }

    public final IResultSetProvider addApplicationFromStack(Object sesion,
                                                            Object stack) throws
                                                                          DataAccessException {
        this.getAddApplicationFromStackService().resetParameter();
        this.getAddApplicationFromStackService().addParameter("1",
                                                              sesion);
        this.getAddApplicationFromStackService().addParameter("2",
                                                              stack);
        this.getAddApplicationFromStackService().executeQuery();

        return this.getAddApplicationFromStackService();
    }

    private AdvancedSSPService getMoveWidgetService() {
        if (this.moveWidgetService == null) {
            this.moveWidgetService = new AdvancedSSPService(ds,
                                                            "PORTAL.MATRIZ_MOVE",
                                                            1);
        }

        return this.moveWidgetService;
    }

    public final IResultSetProvider moveWidget(Object sesion,
                                               Object matriz,
                                               Object posicion) throws
                                                                DataAccessException {
        this.getMoveWidgetService().resetParameter();
        this.getMoveWidgetService().addParameter("1",
                                                 sesion);
        this.getMoveWidgetService().addParameter("2",
                                                 matriz);
        this.getMoveWidgetService().addParameter("3",
                                                 posicion);
        this.getMoveWidgetService().executeQuery();

        return this.getMoveWidgetService();
    }

    private AdvancedSSPService getCloseWidgetService() {
        if (this.closeWidgetService == null) {
            this.closeWidgetService = new AdvancedSSPService(ds,
                                                             "PORTAL.MATRIZ_DEL",
                                                             1);
        }

        return this.closeWidgetService;
    }

    public final IResultSetProvider closeWidget(Object sesion,
                                                Object matriz) throws
                                                               DataAccessException {
        this.getCloseWidgetService().resetParameter();
        this.getCloseWidgetService().addParameter("1",
                                                  sesion);
        this.getCloseWidgetService().addParameter("2",
                                                  matriz);
        this.getCloseWidgetService().executeQuery();

        return this.getCloseWidgetService();
    }

    private AdvancedSSPService getApplicationsService() {
        if (this.applicationsService == null) {
            this.applicationsService = new AdvancedSSPService(ds,
                                                              "PORTAL.MATRIZ",
                                                              3);
        }

        return this.applicationsService;
    }

    public final IResultSetProvider getApplications(Object sesion) throws
                                                                   DataAccessException {
        this.getApplicationsService().resetParameter();
        this.getApplicationsService().addParameter("1",
                                                   sesion);
        this.getApplicationsService().executeQuery();

        return this.getApplicationsService();
    }
}
