package com.gather.jsfmatrix.core.model;

import com.gather.jsfmatrix.core.Property;

public class ApplicationModelFactory {

    public static IApplicationModel createDefaultApplicationModel(Long id) {
        IApplicationModel am = ApplicationModelFactory.createDefaultApplicationModel();
        am.addProperty(Property.ID,
                       id);

        return am;
    }

    public static IApplicationModel createDefaultApplicationModel() {
        return new DefaultApplicationModel();
    }
}
