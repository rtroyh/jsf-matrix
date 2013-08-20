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
        super();
        this.ds = ds;
    }

    private AdvancedSSPService getSeguridadService() {
        if (this.sesionService == null) {
            this.sesionService = new AdvancedSSPService(ds,
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

    public IResultSetProvider sesionService(Object userName) throws
                                                             DataAccessException {
        this.getSesionService().resetParameter();
        this.getSesionService().addParameter(userName);
        this.getSesionService().executeQuery();

        return this.getSesionService();
    }

    public IResultSetProvider claveUsuarioService(Object sesion) throws
                                                                 DataAccessException {
        this.getClaveUsuarioService().resetParameter();
        this.getClaveUsuarioService().addParameter(sesion);
        this.getClaveUsuarioService().executeQuery();

        return this.getClaveUsuarioService();
    }

    public IResultSetProvider seguridadService(Object puerta,
                                               Object instancia,
                                               Object usuario) throws
                                                               DataAccessException {
        this.getSeguridadService().resetParameter();
        this.getSeguridadService().addParameter(puerta);
        this.getSeguridadService().addParameter(instancia);
        this.getSeguridadService().addParameter(usuario);
        this.getSeguridadService().executeQuery();

        return this.getSeguridadService();
    }

}
