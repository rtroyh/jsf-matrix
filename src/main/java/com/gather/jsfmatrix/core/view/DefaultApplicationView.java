package com.gather.jsfmatrix.core.view;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class DefaultApplicationView implements IApplicationView {

    private Map<String, Viewer> views;

    @Override
    public void addView(String key,
                        Viewer ui) {
        Logger.getLogger(DefaultApplicationView.class).debug("INICIO ADDVIEW CON KEY = " +
                                                                     key +
                                                                     " EN UN TOTAL DE " +
                                                                     this.getViews().size());

        this.getViews().put(key,
                            ui);
    }

    @Override
    public Viewer getView(String key) {
        Logger.getLogger(DefaultApplicationView.class).debug("INICIO GETVIEW CON KEY = " +
                                                                     key +
                                                                     " EN UN TOTAL DE " +
                                                                     this.getViews().size());
        try {
            if (this.getViews().get(key) == null) {
                Logger.getLogger(DefaultApplicationView.class).debug("VIEW NO ENCONTRADA...");
                Logger.getLogger(DefaultApplicationView.class).debug("AGREGANDO NUEVO VIEW CON KEY = " +
                                                                             key);
                this.getViews().put(key,
                                    this.getViews().get("0").getClass().newInstance());
                return this.getView(key);
            } else {
                Logger.getLogger(DefaultApplicationView.class).debug("VIEW CON KEY = " +
                                                                             key +
                                                                             " ENCONTRADA");
                return this.getViews().get(key);
            }
        } catch (InstantiationException e) {
            Logger.getLogger(DefaultApplicationView.class).error(e.getMessage());
        } catch (IllegalAccessException e) {
            Logger.getLogger(DefaultApplicationView.class).error(e.getMessage());
        } catch (Exception e) {
            Logger.getLogger(DefaultApplicationView.class).error(e.getMessage());
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
