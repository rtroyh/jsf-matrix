package com.gather.jsfmatrix.core.view;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class DefaultApplicationView implements IApplicationView {
    private static final Logger LOG = Logger.getLogger(DefaultApplicationView.class);
    private Map<String, Viewer> views;

    @Override
    public void addView(String key,
                        Viewer ui) {
        LOG.debug("INICIO ADDVIEW CON KEY = " +
                         key +
                         " EN UN TOTAL DE " +
                         this.getViews().size());

        this.getViews().put(key,
                            ui);
    }

    @Override
    public Viewer getView(String key) {
        LOG.debug("INICIO GETVIEW CON KEY = " +
                         key +
                         " EN UN TOTAL DE " +
                         this.getViews().size());
        try {
            if (this.getViews().get(key) == null) {
                LOG.debug("VIEW NO ENCONTRADA...");
                LOG.debug("AGREGANDO NUEVO VIEW CON KEY = " +
                                 key);
                this.getViews().put(key,
                                    this.getViews().get("0").getClass().newInstance());
                return this.getView(key);
            } else {
                LOG.debug("VIEW CON KEY = " +
                                 key +
                                 " ENCONTRADA");
                return this.getViews().get(key);
            }
        } catch (InstantiationException e) {
            LOG.error(e.getMessage());
        } catch (IllegalAccessException e) {
            LOG.error(e.getMessage());
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

        return null;
    }

    @Override
    public Map<String, Viewer> getViews() {
        if (this.views == null) {
            this.views = new LinkedHashMap<String, Viewer>();
        }

        return views;
    }

    @Override
    public void removeView(String key) {
        this.getViews().remove(key);
    }

}
