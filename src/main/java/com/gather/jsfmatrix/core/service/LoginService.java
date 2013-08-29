package com.gather.jsfmatrix.core.service;

import com.gather.springcommons.services.AdvancedSSPService;
import com.gather.springcommons.services.IResultSetProvider;
import org.springframework.dao.DataAccessException;

import javax.sql.DataSource;

public class LoginService {
    private final DataSource ds;

    private AdvancedSSPService seguridadService;
    private AdvancedSSPService sesionService;
    private AdvancedSSPService claveUsuarioService;

    public LoginService(DataSource ds) {
        this.ds = ds;
    }

    private AdvancedSSPService getSeguridadService() {
        if (this.seguridadService == null) {
            this.seguridadService = new AdvancedSSPService(ds,
                                                           "SEG.ACCESO_SP",
                                                           1);
        }

        return seguridadService;
    }

    private AdvancedSSPService getSesionService() {
        if (this.sesionService == null) {
            this.sesionService = new AdvancedSSPService(ds,
                                                        "DIN.GENERAR_SESION",
                                                        1);
        }

        return this.sesionService;
    }

    private AdvancedSSPService getClaveUsuarioService() {
        if (this.claveUsuarioService == null) {
            this.claveUsuarioService = new AdvancedSSPService(ds,
                                                              "DIN.SESION_USUARIO",
                                                              1);
        }

        return this.claveUsuarioService;
    }

    public final IResultSetProvider sesionService(Object userName) throws
                                                                   DataAccessException {
        final AdvancedSSPService service = this.getSesionService();
        service.resetParameter();
        service.addParameter(userName);
        service.executeQuery();

        return service;
    }

    public final IResultSetProvider claveUsuarioService(Object sesion) throws
                                                                       DataAccessException {
        final AdvancedSSPService service = this.getClaveUsuarioService();
        service.resetParameter();
        service.addParameter(sesion);
        service.executeQuery();

        return service;
    }

    public final IResultSetProvider seguridadService(Object puerta,
                                                     Object instancia,
                                                     Object usuario) throws
                                                                     DataAccessException {
        final AdvancedSSPService service = this.getSeguridadService();
        service.resetParameter();
        service.addParameter(puerta);
        service.addParameter(instancia);
        service.addParameter(usuario);
        service.executeQuery();

        return service;
    }
}
