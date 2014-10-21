package com.gather.jsfmatrix.util;

import javax.faces.context.FacesContext;

import org.primefaces.component.contextmenu.ContextMenu;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.panel.Panel;

import com.gather.jsfmatrix.core.IMatrixApplication;
import com.gather.jsfmatrix.core.Property;
import com.gather.jsfmatrix.core.listener.CMDeleteWidgetListener;
import com.gather.jsfmatrix.core.view.PrimeFacesUIComponentsFactory;

public class MatrixApplicationUtil {

    public static ContextMenu createContextMenuforPanelWidget(Panel item,
                                                              IMatrixApplication ma,
                                                              FacesContext fc) {
        ContextMenu cm = PrimeFacesUIComponentsFactory.createContextMenu(fc);
        cm.setId("cm_" + fc.getViewRoot().createUniqueId());
        cm.setFor(item.getClientId(fc));

        MenuItem cmi = PrimeFacesUIComponentsFactory.createMenuItem(fc);
        cmi.setId("cmi_" + fc.getViewRoot().createUniqueId());
        cmi.setValue("Copiar");
        cmi.getAttributes().put("ma",
                                ma);
        cm.getChildren().add(cmi);

        MenuItem cmi2 = PrimeFacesUIComponentsFactory.createMenuItem(fc);
        cmi2.setId("cmi_" + fc.getViewRoot().createUniqueId());
        cmi2.setValue("Pegar");
        cmi.getAttributes().put("ma",
                                ma);
        cm.getChildren().add(cmi2);

        if (ma.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.BORRABLE).equals(1)) {
            MenuItem cmi3 = PrimeFacesUIComponentsFactory.createMenuItem(fc);
            cmi3.setId("cmi_" + fc.getViewRoot().createUniqueId());
            cmi3.setValue("Borrar");
            cmi.getAttributes().put("ma",
                                    ma);
            cmi3.addActionListener(new CMDeleteWidgetListener());
            cm.getChildren().add(cmi3);
        }

        return cm;
    }
}
