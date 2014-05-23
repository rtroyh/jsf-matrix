package com.gather.jsfmatrix.core.service;

import com.gather.springcommons.services.AdvancedSSPService;
import com.gather.springcommons.services.IResultSetProvider;
import org.springframework.dao.DataAccessException;

import javax.sql.DataSource;

public class DockServices {

    private DataSource ds;
    private AdvancedSSPService list;

    public DockServices(DataSource ds) {
        super();
        this.ds = ds;
    }

    private AdvancedSSPService getList() {
        if (this.list == null) {
            this.list = new AdvancedSSPService(ds,
                                               "PORTAL.DOCK",
                                               1);
        }

        return this.list;
    }

    public final IResultSetProvider getListResultSetProvider(Object sesion) throws
                                                                            DataAccessException {
        final AdvancedSSPService service = this.getList();
        service.resetParameter();
        service.addParameter(sesion);
        service.executeQuery();

        return service;
    }
}
