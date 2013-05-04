package me.majsky.lib.simpleConfig;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.plugin.Plugin;

/**
 * Simple text-based config file if you don't like yml's
 * @author majsky
 *
 */
public class SimpleConfigFile {
    
    public final Plugin owner;
    private List<ConfigCategory> categories;
    
    public SimpleConfigFile(Plugin plugin){
        owner = plugin;
        categories = new ArrayList<>();
    }
    
    /**
     * Creates new or returns existing {@link ConfigCategory}
     * @param name - {@link ConfigCategory} name
     * @return {@link ConfigCategory} with given name
     */
    public ConfigCategory getOrCreateCategory(String name){
        for(ConfigCategory cc:categories)
            if(cc.name.equalsIgnoreCase(name))
                return cc;
        
        ConfigCategory cat = new ConfigCategory(name);
        categories.add(cat);
        return cat;
    }
    /**
     * Sets value of given config field in given category
     * @param category - {@link ConfigCategory} to modify or create
     * @param name - Value name
     * @param value - Value
     */
    public void setValue(String category, String name, String value){
        getOrCreateCategory(category).addValue(name, value);
    }
    
    /**
     * Gets value of given config field
     * @param category - {@link ConfigCategory} to get from
     * @param name - Field name
     * @return Value of this field
     */
    public String getValue(String category, String name){
        if(!categories.contains(category))
            return null;
        
        return categories.get(categories.indexOf(category)).getValue(name);
    }
    
    /**
     * Loads config from disk
     */
    public void load() {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(new File(owner.getDataFolder(), "config.txt")));
            String line;
            while((line = reader.readLine()) != null){
                if(line.startsWith("#"))
                    continue;
                
                String[] data = line.split(";", 3);
                getOrCreateCategory(data[0]).addValue(data[1], data[2]);
            }
            reader.close();
        }catch(IOException e){}
    }
    
    /**
     * Saves all changes to disk
     */
    public void save(){
        if(!owner.getDataFolder().exists())
            owner.getDataFolder().mkdirs();
        
        try {
            BufferedWriter w = new BufferedWriter(new FileWriter(new File(owner.getDataFolder(), "config.txt")));
            w.write("# MajskyLib Simple Config File");
            w.newLine();
            w.write("# Generated at " + new SimpleDateFormat("HH:mm dd.MM.yyyy").format(new Date()));
            w.newLine();
            w.write("# Format:");
            w.newLine();
            w.write("# Category;key;value");
            w.newLine();
            for(ConfigCategory cc:categories){
                for(Object name:cc.values.keySet().toArray()){
                    w.write(cc.name + ";" + name + ";" + cc.values.get(name));
                    w.newLine();
                }  
            }
            w.flush();
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
