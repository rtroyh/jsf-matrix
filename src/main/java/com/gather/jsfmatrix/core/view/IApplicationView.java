package com.gather.jsfmatrix.core.view;

import java.util.Map;

public interface IApplicationView {

    public Map<String, IViewer> getViews();

    public IViewer getView(String key);

    public void removeView(String key);

    public void addView(String key,
                        IViewer ui);
}
