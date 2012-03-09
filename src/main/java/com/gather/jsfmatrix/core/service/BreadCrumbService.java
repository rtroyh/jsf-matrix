package com.gather.jsfmatrix.core.service;

import javax.sql.DataSource;

import com.gather.springcommons.services.AdvancedSSPService;

public class BreadCrumbService {

    private final DataSource ds;
    private AdvancedSSPService historialService;

    public BreadCrumbService(DataSource ds) {
        super();
        this.ds = ds;
    }

    public synchronized AdvancedSSPService getHistorialService() {
        if (this.historialService == null) {
            this.historialService = new AdvancedSSPService(ds,
                                                           "PORTAL.HISTORIAL",
                                                           1);
        }

        return this.historialService;
    }
}
