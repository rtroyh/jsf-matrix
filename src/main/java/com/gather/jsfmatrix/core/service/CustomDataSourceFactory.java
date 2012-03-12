package com.gather.jsfmatrix.core.service;

import javax.faces.context.FacesContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.gather.gathercommons.enums.DS;

final class CustomDataSourceFactory {

    public static synchronized DataSource getDriverManagerDataSource(WebApplicationContext ctx,
                                                                     DS conexion) {
        try {

            if (conexion == DS.APV) {
                return (DataSource) ctx.getBean("dataSourceAPV");
            } else if (conexion == DS.BFM) {
                return (DataSource) ctx.getBean("dataSourceBFM");
            } else if (conexion == DS.GESTION) {
                return (DataSource) ctx.getBean("dataSourceGestion");
            } else if (conexion == DS.MESA) {
                return (DataSource) ctx.getBean("dataSourceMESA");
            } else if (conexion == DS.PARTICIP) {
                return (DataSource) ctx.getBean("dataSourcePARTICIP");
            } else if (conexion == DS.DIN) {
                return (DataSource) ctx.getBean("dataSourceDIN");
            } else if (conexion == DS.SERVIPAG) {
                return (DataSource) ctx.getBean("dataSourceSERVIPAG");
            } else if (conexion == DS.FICHA) {
                return (DataSource) ctx.getBean("dataSourceFICHAS");
            } else if (conexion == DS.SYBASE) {
                return (DataSource) ctx.getBean("dataSourceSYBASE");
            } else if (conexion == DS.CLIENTE) {
                return (DataSource) ctx.getBean("dataSourceCLIENTE");
            } else if (conexion == DS.COLEGIOS) {
                return (DataSource) ctx.getBean("dataSourceCOLEGIOS");
            } else if (conexion == DS.WF) {
                return (DataSource) ctx.getBean("dataSourceWF");
            } else if (conexion == DS.CENTRAL) {
                return (DataSource) ctx.getBean("dataSourceCENTRAL");
            }
        } catch (BeansException e) {
            Logger.getLogger(CustomDataSourceFactory.class).error(e.getMessage());
        } catch (Exception e) {
            Logger.getLogger(CustomDataSourceFactory.class).error(e.getMessage());
        }

        return null;
    }

    public static synchronized DataSource getDriverManagerDataSource(FacesContext fc,
                                                                     DS conexion) {
        try {
            WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(fc);
            if (conexion == DS.APV) {
                return (DataSource) ctx.getBean("dataSourceAPV");
            } else if (conexion == DS.BFM) {
                return (DataSource) ctx.getBean("dataSourceBFM");
            } else if (conexion == DS.GESTION) {
                return (DataSource) ctx.getBean("dataSourceGestion");
            } else if (conexion == DS.MESA) {
                return (DataSource) ctx.getBean("dataSourceMESA");
            } else if (conexion == DS.PARTICIP) {
                return (DataSource) ctx.getBean("dataSourcePARTICIP");
            } else if (conexion == DS.DIN) {
                return (DataSource) ctx.getBean("dataSourceDIN");
            } else if (conexion == DS.SERVIPAG) {
                return (DataSource) ctx.getBean("dataSourceSERVIPAG");
            } else if (conexion == DS.FICHA) {
                return (DataSource) ctx.getBean("dataSourceFICHAS");
            } else if (conexion == DS.SYBASE) {
                return (DataSource) ctx.getBean("dataSourceSYBASE");
            } else if (conexion == DS.CLIENTE) {
                return (DataSource) ctx.getBean("dataSourceCLIENTE");
            } else if (conexion == DS.COLEGIOS) {
                return (DataSource) ctx.getBean("dataSourceCOLEGIOS");
            } else if (conexion == DS.WF) {
                return (DataSource) ctx.getBean("dataSourceWF");
            } else if (conexion == DS.CENTRAL) {
                return (DataSource) ctx.getBean("datasourceCENTRAL");
            }
        } catch (BeansException e) {
            Logger.getLogger(CustomDataSourceFactory.class).error(e.getMessage());
        } catch (Exception e) {
            Logger.getLogger(CustomDataSourceFactory.class).error(e.getMessage());
        }

        return null;
    }
}
