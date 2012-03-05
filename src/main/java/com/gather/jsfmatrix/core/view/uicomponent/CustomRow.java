package com.gather.jsfmatrix.core.view.uicomponent;

import java.util.List;

import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import com.gather.jsfcommons.component.datatable.ISelectedRowHolder;
import com.gather.jsfcommons.component.datatable.Row;
import com.gather.jsfmatrix.core.IValueHolder;
import com.gather.jsfmatrix.core.Property;
import com.gather.jsfmatrix.core.model.IApplicationModel;

@SuppressWarnings("unchecked")
public class CustomRow extends Row implements IValueHolder {

    private IApplicationModel applicationModel;

    public CustomRow(List<Object> data,
                     ISelectedRowHolder<?> selectedRowHolder,
                     IApplicationModel applicationModel) {
        super(data,
              selectedRowHolder);
        this.applicationModel = applicationModel;
    }

    public CustomRow(CustomRow customRow) {
        super(customRow);
        this.applicationModel = customRow.applicationModel;
    }

    @Override
    public IApplicationModel getApplicationModel() {
        return this.applicationModel;
    }

    @Override
    public void setApplicationModel(IApplicationModel applicationModel) {
        this.applicationModel = applicationModel;
    }

    @Override
    public Object getPropertyValue(Property property) {
        return this.getApplicationModel().getPropertyValue(property);
    }

    @Override
    public void select(ActionEvent event) {
        Logger.getLogger(CustomRow.class).info("INICIO EVENTO SELECCION REGISTRO");
        this.getSelectedRowHolder().setSelectedRow(this);
    }
}
