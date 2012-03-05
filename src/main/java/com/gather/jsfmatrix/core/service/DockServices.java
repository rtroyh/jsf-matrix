package com.gather.jsfmatrix.core.service;

import javax.sql.DataSource;

import com.gather.springcommons.services.AdvancedSSPService;

public class DockServices {

    public DataSource ds;
    private AdvancedSSPService list;

    public DockServices(DataSource ds) {
        super();
        this.ds = ds;
    }

    public synchronized AdvancedSSPService getList() {
        if (this.list == null) {
            this.list = new AdvancedSSPService(ds,
                                               "PORTAL.DOCK",
                                               1);
        }

        return this.list;
    }

}
