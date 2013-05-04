package me.majsky.lib.simpleConfig;

import java.util.HashMap;
import java.util.Map;

public class ConfigCategory {

    public final String name;
    protected Map<String, String> values;
    
    protected ConfigCategory(String name){
        this.name = name;
        values = new HashMap<>();
    }

    public void addValue(String name, String value) {
        addValue(name, value, false);
    }
    
    public void addValue(String name, String value, boolean override){
        if(!override && values.containsKey(name))
            return;
        values.put(name, value);
    }
    
    public boolean containsValue(String name){
        return values.containsKey(name);
    }
    
    public String getValue(String name){
        return values.get(name);
    }
}
