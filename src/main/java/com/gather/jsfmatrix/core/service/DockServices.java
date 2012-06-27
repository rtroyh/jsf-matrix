package com.gather.jsfmatrix.core.service;

import com.gather.springcommons.services.AdvancedSSPService;

import javax.sql.DataSource;

public class DockServices {

    private final DataSource ds;
    private AdvancedSSPService list;

    public DockServices(DataSource ds) {
        super();
        this.ds = ds;
    }

    public AdvancedSSPService getList() {
        if (this.list == null) {
            this.list = new AdvancedSSPService(ds,
                                               "PORTAL.DOCK",
                                               1);
        }

        return this.list;
    }

}
