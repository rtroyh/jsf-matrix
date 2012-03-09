package com.gather.jsfmatrix.util;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.gather.jsfmatrix.core.Property;

public class MapPropertyUtil {

    @SuppressWarnings("unchecked")
    public static synchronized void copyProperties(EnumMap<Property, Object> origin,
                                                   EnumMap<Property, Object> destiny) {
        try {
            if ((origin != null && destiny != null) &&
                    origin != destiny) {
                destiny.clear();
                Set<?> set = origin.entrySet();

                for (Object next : set) {

                    if (next != null) {
                        Map.Entry<Property, Object> me = (Map.Entry<Property, Object>) next;
                        destiny.put(me.getKey(),
                                    me.getValue());
                    }
                }
            }
        } catch (Exception e) {
            Logger.getLogger(MapPropertyUtil.class).error(e.getMessage());
        }
    }
}
