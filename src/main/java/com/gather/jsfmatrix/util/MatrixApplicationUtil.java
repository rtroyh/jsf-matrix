package com.gather.jsfmatrix.util;

import javax.faces.context.FacesContext;

import org.primefaces.component.contextmenu.ContextMenu;
import org.primefaces.component.panel.Panel;

import com.gather.jsfmatrix.core.IMatrixApplication;
import com.gather.jsfmatrix.core.Property;
import com.gather.jsfmatrix.core.listener.CMDeleteWidgetListener;
import com.gather.jsfmatrix.core.view.PrimeFacesUIComponentsFactory;
import com.gather.jsfmatrix.core.view.uicomponent.CustomMenuItem;

public class MatrixApplicationUtil {

    public static ContextMenu createContextMenuforPanelWidget(Panel item,
                                                              IMatrixApplication ma,
                                                              FacesContext fc) {
        ContextMenu cm = PrimeFacesUIComponentsFactory.createContextMenu(fc);
        cm.setId("cm_" +
                         fc.getViewRoot().createUniqueId());
        cm.setFor(item.getClientId(fc));

        CustomMenuItem cmi = new CustomMenuItem();
        cmi.setId("cmi_" +
                          fc.getViewRoot().createUniqueId());
        cmi.setValue("Copiar");
        cmi.setUserObject(ma);
        cm.getChildren().add(cmi);

        CustomMenuItem cmi2 = new CustomMenuItem();
        cmi2.setId("cmi_" +
                           fc.getViewRoot().createUniqueId());
        cmi2.setValue("Pegar");
        cmi2.setUserObject(ma);
        cm.getChildren().add(cmi2);

        if (ma.getMatrixApplicationHandler().getApplicationModel().getPropertyValue(Property.BORRABLE).equals(1)) {
            CustomMenuItem cmi3 = new CustomMenuItem();
            cmi3.setId("cmi_" +
                               fc.getViewRoot().createUniqueId());
            cmi3.setValue("Borrar");
            cmi3.setUserObject(ma);
            cmi3.addActionListener(new CMDeleteWidgetListener());
            cm.getChildren().add(cmi3);
        }

        return cm;
    }
}
