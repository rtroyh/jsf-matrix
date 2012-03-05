package com.gather.jsfmatrix.core;

import java.util.Map;

public interface IPopulable {

    public void populate(Map<Ingredients, Object> recipe);

    public void resetState();
}
