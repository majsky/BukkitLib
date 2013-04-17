package me.majsky.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.bukkit.plugin.java.JavaPlugin;

public class DataSaver {
   
    public static void save(Object obj, String file, JavaPlugin jpl){
        ObjectOutputStream oos;
        if(!jpl.getDataFolder().exists())
            jpl.getDataFolder().mkdirs();
        try {
            oos = new ObjectOutputStream(new FileOutputStream(new File(jpl.getDataFolder(), file), true));
            oos.writeObject(obj);
            oos.flush();
            oos.close();
        } catch (IOException e) {e.printStackTrace();} 
    }
    
    public static Object load(String file, JavaPlugin jpl){
        ObjectInputStream ois;
        Object result = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(new File(jpl.getDataFolder(), file)));
            result = ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {e.printStackTrace();}
        return result;
    }
    
    public static boolean isPresent(String file, JavaPlugin jpl){
        return new File(jpl.getDataFolder(), file).exists() && new File(jpl.getDataFolder(), file).isFile();
    }
}
