package com.gather.jsfmatrix.core.view;

import java.util.Map;

public interface IApplicationView {

    public Map<String, Viewer> getViews();

    public Viewer getView(String key);

    public void removeView(String key);

    public void addView(String key,
                        Viewer ui);
}
