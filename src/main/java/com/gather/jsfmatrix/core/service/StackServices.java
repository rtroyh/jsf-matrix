package com.gather.jsfmatrix.core.service;

import javax.sql.DataSource;

import com.gather.springcommons.services.AdvancedSSPService;

public class StackServices {

    private DataSource ds;

    private AdvancedSSPService list;
    private AdvancedSSPService addService;

    public StackServices(DataSource ds) {
        super();
        this.ds = ds;
    }

    public synchronized AdvancedSSPService getListService() {
        if (this.list == null) {
            this.list = new AdvancedSSPService(ds,
                                               "PORTAL.STACK",
                                               1);
        }

        return this.list;
    }

    public synchronized AdvancedSSPService getAddService() {
        if (this.addService == null) {
            this.addService = new AdvancedSSPService(ds,
                                                     "PORTAL.STACK_ADD",
                                                     1);
        }

        return this.addService;
    }

}
