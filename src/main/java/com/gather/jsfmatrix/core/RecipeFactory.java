package com.gather.jsfmatrix.core;

import java.util.HashMap;
import java.util.Map;

public class RecipeFactory {

    public static Map<Ingredients, Object> createRecipe(Ingredients ingredient,
                                                        Object o) {
        Map<Ingredients, Object> map = RecipeFactory.createRecipe();
        map.put(ingredient,
                o);

        return map;
    }

    public static Map<Ingredients, Object> createRecipe() {
        Map<Ingredients, Object> map = new HashMap<Ingredients, Object>();

        return map;
    }
}
