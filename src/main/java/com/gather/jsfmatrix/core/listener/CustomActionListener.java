package com.gather.jsfmatrix.core.listener;

import javax.faces.component.UIComponent;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

public class CustomActionListener implements ActionListener {

    public CustomActionListener() {
    }

    public void processAction(ActionEvent event) throws
                                                 AbortProcessingException {
        System.out.println("Custom Action Listener called..");

        UIComponent source = event.getComponent();

        System.out.println("Source of the Event is " +
                                   source.getClass().getName());
    }
}