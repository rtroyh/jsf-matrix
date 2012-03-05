package com.gather.jsfmatrix.core.view;

public class ViewerFactory {

    public static Viewer createDefaultViewer(ViewType viewType) {
        if (viewType == null) {
            return null;
        } else if (ViewType.APPLICATION.equals(viewType)) {
            return new DefaultJSFView(null);
        } else if (ViewType.DOCK.equals(viewType)) {
            return new DefaultDockView(null);
        } else if (ViewType.WIDGET.equals(viewType)) {
            return new DefaultWidgetView();
        }

        return null;
    }
}
