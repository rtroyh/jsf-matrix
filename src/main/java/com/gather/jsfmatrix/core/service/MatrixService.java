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
        final AdvancedSSPService ssp = this.getTitleRenameService();
        ssp.resetParameter();
        ssp.addParameter("1",
                         sesion);
        ssp.addParameter("2",
                         matrixID);
        ssp.addParameter("3",
                         title);
        ssp.executeQuery();

        return ssp;
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
        final AdvancedSSPService ssp = this.getHeaderService();
        ssp.resetParameter();
        ssp.addParameter("1",
                         sesion);
        ssp.executeQuery();

        return ssp;
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
        final AdvancedSSPService ssp = this.getDirectoryService();
        ssp.resetParameter();
        ssp.addParameter("1",
                         sesion);
        ssp.addParameter("2",
                         matrixID);
        ssp.addParameter("3",
                         javaID);
        ssp.executeQuery();

        return ssp;
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
        final AdvancedSSPService ssp = this.getAddApplicationFromDockService();
        ssp.resetParameter();
        ssp.addParameter("1",
                         sesion);
        ssp.addParameter("2",
                         matrixID);
        ssp.addParameter("3",
                         paquete);
        ssp.executeQuery();

        return ssp;
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
        final AdvancedSSPService ssp = this.getAddApplicationFromStackService();
        ssp.resetParameter();
        ssp.addParameter("1",
                         sesion);
        ssp.addParameter("2",
                         stack);
        ssp.executeQuery();

        return ssp;
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
        final AdvancedSSPService ssp = this.getMoveWidgetService();
        ssp.resetParameter();
        ssp.addParameter("1",
                         sesion);
        ssp.addParameter("2",
                         matriz);
        ssp.addParameter("3",
                         posicion);
        ssp.executeQuery();

        return ssp;
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
        final AdvancedSSPService ssp = this.getCloseWidgetService();
        ssp.resetParameter();
        ssp.addParameter("1",
                         sesion);
        ssp.addParameter("2",
                         matriz);
        ssp.executeQuery();

        return ssp;
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
        final AdvancedSSPService ssp = this.getApplicationsService();
        ssp.resetParameter();
        ssp.addParameter("1",
                         sesion);
        ssp.executeQuery();

        return ssp;
    }
}
