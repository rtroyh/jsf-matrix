package com.gather.jsfmatrix.core.listener;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.apache.log4j.Logger;

public class StackListener implements ActionListener {

    public StackListener() {
    }

    public void processAction(ActionEvent event) throws
                                                 AbortProcessingException {
        Logger.getLogger(StackListener.class).info("INICIO EVENTO CLICK EN STACK");
  }
}