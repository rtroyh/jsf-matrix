package com.gather.jsfmatrix.core.service;

import java.io.Serializable;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;

import com.gather.springcommons.services.AdvancedSSPService;
import com.gather.springcommons.services.IResultSetProvider;

public class LoginService implements Serializable {

    private static final long serialVersionUID = -7628409413703647022L;

    private DataSource ds;

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
                                                             DataAccessException,
                                                             Exception {

        this.getSesionService().resetParameter();
        this.getSesionService().addParameter("1",
                                             userName);
        this.getSesionService().executeQuery();

        return this.getSesionService();
    }

    public IResultSetProvider claveUsuarioService(Object sesion) throws
                                                                 DataAccessException,
                                                                 Exception {
        this.getClaveUsuarioService().resetParameter();
        this.getClaveUsuarioService().addParameter("1",
                                                   sesion);
        this.getClaveUsuarioService().executeQuery();

        return this.getClaveUsuarioService();
    }

    public IResultSetProvider seguridadService(Object puerta,
                                               Object instancia,
                                               Object usuario) throws
                                                               DataAccessException,
                                                               Exception {
        this.getSeguridadService().resetParameter();
        this.getSeguridadService().addParameter("1",
                                                puerta);
        this.getSeguridadService().addParameter("2",
                                                instancia);
        this.getSeguridadService().addParameter("3",
                                                usuario);
        this.getSeguridadService().executeQuery();

        return this.getSeguridadService();
    }

}
