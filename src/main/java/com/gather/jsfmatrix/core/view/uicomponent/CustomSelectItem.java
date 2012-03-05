package com.gather.jsfmatrix.core.view.uicomponent;

import javax.faces.model.SelectItem;

public class CustomSelectItem extends SelectItem {

    /**
     *
     */
    private static final long serialVersionUID = -8459823043951484216L;

    public CustomSelectItem() {
        super();
        // TODO Auto-generated constructor stub
    }

    public CustomSelectItem(Object value,
                            String label,
                            String description,
                            boolean disabled,
                            boolean escape,
                            boolean noSelectionOption) {
        super(value,
              label,
              description,
              disabled,
              escape,
              noSelectionOption);
        // TODO Auto-generated constructor stub
    }

    public CustomSelectItem(Object value,
                            String label,
                            String description,
                            boolean disabled,
                            boolean escape) {
        super(value,
              label,
              description,
              disabled,
              escape);
        // TODO Auto-generated constructor stub
    }

    public CustomSelectItem(Object value,
                            String label,
                            String description,
                            boolean disabled) {
        super(value,
              label,
              description,
              disabled);
        // TODO Auto-generated constructor stub
    }

    public CustomSelectItem(Object value,
                            String label,
                            String description) {
        super(value,
              label,
              description);
        // TODO Auto-generated constructor stub
    }

    public CustomSelectItem(Object value,
                            String label) {
        super(value,
              label);
        // TODO Auto-generated constructor stub
    }

    public CustomSelectItem(Object value) {
        super(value);
    }

    @Override
    public String toString() {
        return this.getLabel();
    }

}
