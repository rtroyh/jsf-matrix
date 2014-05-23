package com.gather.jsfmatrix.core.service;

import com.gather.springcommons.services.AdvancedSSPService;

import javax.sql.DataSource;

public class BreadCrumbService {
    private DataSource ds;
    private AdvancedSSPService historialService;

    public BreadCrumbService(DataSource ds) {
        super();
        this.ds = ds;
    }

    public final AdvancedSSPService getHistorialService() {
        if (this.historialService == null) {
            this.historialService = new AdvancedSSPService(ds,
                                                           "PORTAL.HISTORIAL",
                                                           1);
        }

        return this.historialService;
    }
}
