package com.gather.jsfmatrix.core.service;

import com.gather.gathercommons.model.IDataTableModel;
import com.gather.springcommons.dataAccess.resultsetMapper.datatable.DefaultDataTableMapper;
import com.gather.springcommons.dataAccess.resultsetMapper.datatable.IDataTableMapper;
import com.gather.springcommons.dataAccess.resultsetMapper.datatable.RareCharacterDataTableMapperDecorator;
import com.gather.springcommons.services.AdvancedSSPService;
import com.gather.springcommons.services.IResultSetProvider;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import javax.sql.DataSource;

public class MatrixService {
    private static final Logger LOG = Logger.getLogger(MatrixService.class);
    private DataSource ds;

    private AdvancedSSPService headerService;
    private AdvancedSSPService directoryService;
    private AdvancedSSPService closeWidgetService;
    private AdvancedSSPService titleRenameService;
    private AdvancedSSPService applicationsService;
    private AdvancedSSPService addApplicationFromDockService;

    public MatrixService(DataSource ds) {
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
        ssp.addParameter(sesion);
        ssp.addParameter(matrixID);
        ssp.addParameter(title);
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
        ssp.addParameter(sesion);
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
        ssp.addParameter(sesion);
        ssp.addParameter(matrixID);
        ssp.addParameter(javaID);
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
        ssp.addParameter(sesion);
        ssp.addParameter(matrixID);
        ssp.addParameter(paquete);
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
        ssp.addParameter(sesion);
        ssp.addParameter(matriz);
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

    private IResultSetProvider getApplications(Object sesion) throws
                                                              DataAccessException {
        final AdvancedSSPService ssp = this.getApplicationsService();
        ssp.resetParameter();
        ssp.addParameter(sesion);
        ssp.executeQuery();

        return ssp;
    }

    public final IDataTableModel getApplicationsModel(Object sesion) throws
                                                                     DataAccessException {
        try {
            IResultSetProvider rp = this.getApplications(sesion);

            IDataTableMapper mapper = new RareCharacterDataTableMapperDecorator(new DefaultDataTableMapper());
            return mapper.executeMapper(rp);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

        return null;
    }

}
