package com.gather.jsfmatrix.core.service;

import com.gather.gathercommons.enums.DS;
import com.gather.gathercommons.util.Printer;
import com.gather.gathercommons.util.Validator;
import com.gather.springcommons.dataAccess.rowmappers.ListMapper;
import com.gather.springcommons.services.AdvancedSSPService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.faces.context.FacesContext;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class QueryHandler {
    private AdvancedSSPService advancedSSPService;
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private Printer mapPrinter;

    public Printer getMapPrinter() {
        if (mapPrinter == null) {
            mapPrinter = new Printer();
        }

        return mapPrinter;
    }

    public void setMapPrinter(Printer mapPrinter) {
        this.mapPrinter = mapPrinter;
    }

    public QueryHandler(FacesContext fc,
                        DS conexion) {
        super();
        this.init(fc,
                  conexion);
    }

    public QueryHandler(DataSource dataSource) {
        super();
        this.setDataSource(dataSource);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private void init(FacesContext fc,
                      DS conexion) {
        try {
            this.setDataSource(CustomDataSourceFactory.getDriverManagerDataSource(fc,
                                                                                  conexion));
        } catch (BeansException e) {
            Logger.getLogger(QueryHandler.class).error(e.getMessage());
        } catch (Exception e) {
            Logger.getLogger(QueryHandler.class).error(e.getMessage());
        }
    }

    private JdbcTemplate getJdbcTemplate() {
        if (this.jdbcTemplate == null) {
            this.jdbcTemplate = new JdbcTemplate(this.getDataSource());
        }

        return jdbcTemplate;
    }

    public List<Object> selectSQL(String query) throws
                                                DataAccessException {
        Logger.getLogger(this.getClass()).debug(query);

        if (Validator.validateString(query)) {
            return this.getJdbcTemplate().query(query.trim(),
                                                new ListMapper());
        } else {
            Logger.getLogger(this.getClass()).error("(QUERY) = NULL");
        }

        return Collections.emptyList();
    }

    public List<Object> selectSQLList(String query,
                                      Object[] param) throws
                                                      DataAccessException {
        Logger.getLogger(this.getClass()).debug(query +
                                                        " " +
                                                        Arrays.toString(param));

        if (Validator.validateString(query) && param != null) {
            return this.getJdbcTemplate().queryForList(query.trim(),
                                                       param,
                                                       Object.class);
        } else {
            Logger.getLogger(this.getClass()).error("(QUERY o/y PARAMETROS) = NULL");
        }

        return Collections.emptyList();
    }

    public List<Object> selectSQL(String query,
                                  Object[] param) throws
                                                  DataAccessException {
        Logger.getLogger(this.getClass()).debug(query +
                                                        " " +
                                                        Arrays.toString(param));

        if (Validator.validateString(query) && param != null) {
            return this.getJdbcTemplate().query(query.trim(),
                                                param,
                                                new ListMapper());
        } else {
            Logger.getLogger(this.getClass()).error("(QUERY o/y PARAMETROS) = NULL");
        }

        return Collections.emptyList();
    }

    public Map<String, Object> executeSP(String sp,
                                         Object[] param,
                                         Integer resultsets) throws
                                                             DataAccessException {
        advancedSSPService = new AdvancedSSPService(this.getDataSource(),
                                                    sp,
                                                    resultsets);

        return advancedSSPService.executeQuery(param);
    }

    public Map<String, Object> executeSP(String sp,
                                         Object[] param) throws
                                                         DataAccessException {
        advancedSSPService = new AdvancedSSPService(this.getDataSource(),
                                                    sp,
                                                    0);

        return advancedSSPService.executeQuery(param);
    }

    public Map<String, Object> executeSP(String sp,
                                         Map<String, Object> param,
                                         Integer mapperCount) throws
                                                              DataAccessException {
        advancedSSPService = new AdvancedSSPService(this.getDataSource(),
                                                    sp,
                                                    mapperCount);

        return advancedSSPService.executeQuery(param);
    }

    public Map<String, Object> executeSP(String sp,
                                         List<Object> param,
                                         Integer mapperCount) throws
                                                              DataAccessException {
        advancedSSPService = new AdvancedSSPService(this.getDataSource(),
                                                    sp,
                                                    mapperCount);

        return advancedSSPService.executeQuery(param);
    }

    public Map<String, Object> executeSP(String sp,
                                         Integer mapperCount) throws
                                                              DataAccessException {
        advancedSSPService = new AdvancedSSPService(this.getDataSource(),
                                                    sp,
                                                    mapperCount);

        return advancedSSPService.executeQuery(new Object[]{});
    }

    public void updateSQL(String query,
                          Object[] param) throws
                                          DataAccessException {
        Logger.getLogger(this.getClass()).debug(query +
                                                        " " +
                                                        Arrays.toString(param));

        if (Validator.validateString(query) && param != null) {
            this.getJdbcTemplate().update(query.trim(),
                                          param);
        } else {
            Logger.getLogger(this.getClass()).error("(QUERY o/y PARAMETROS) = NULL");
        }
    }

    public void updateSQL(String query) throws
                                        DataAccessException {
        Logger.getLogger(this.getClass()).debug(query);

        if (Validator.validateString(query)) {
            this.getJdbcTemplate().update(query.trim(),
                                          new Object[]{});
        } else {
            Logger.getLogger(this.getClass()).error("(QUERY) = NULL");
        }
    }
}
