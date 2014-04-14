package com.gather.jsfmatrix.core.view;

import java.util.Map;

public interface IApplicationView {

    public Map<String, JSFViewer> getViews();

    public JSFViewer getView(String key);

    public void removeView(String key);

    public void addView(String key,
                        JSFViewer ui);
}
