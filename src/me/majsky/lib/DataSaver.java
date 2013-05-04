package me.majsky.lib;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class DataSaver {
   
    /**
     * Saves object to file
     * @param obj - Object to save
     * @param file - File, which will contain this object
     * @param jpl - Plugin instance
     */
    public static void save(Object obj, String file, Plugin jpl){
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
    /**
     * Loads object from file
     * @param file - File to load from
     * @param jpl - Plugin instace
     * @return Object from file
     */
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
    
    public static boolean isPresent(String file, Plugin jpl){
        return new File(jpl.getDataFolder(), file).exists();
    }
    /**
     * Saves Map that have key and value strings
     * @param map - map to save
     * @param file - file to sore map
     * @param jpl - plugin instace
     */
    public static void saveReadable(Map<String, String> map, String file, Plugin jpl){
        if(!jpl.getDataFolder().exists())
            jpl.getDataFolder().mkdirs();
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(jpl.getDataFolder(), file)));
            bw.write("# MajskyLib Savefile");
            bw.newLine();
            bw.write("# Lines starting with # are comments");
            bw.newLine();
            bw.write(String.format("# SaveFile for %s's file %s", jpl.getName(), file));
            bw.newLine();
            bw.write("# Generated at " + new SimpleDateFormat("HH:mm dd.MM.yyyy").format(new Date()));
            bw.newLine();
            bw.write("# Format:");
            bw.newLine();
            bw.write("# key;value");
            bw.newLine();
            for(Object s:map.keySet().toArray()){
                bw.write(s+";"+map.get(s));
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }catch(IOException e){e.printStackTrace();};
    }
    /**
     * Loads saved readable map
     * @param file - File to read data from
     * @param jpl - plugin instance
     * @return Map from file
     */
    public static Map<String, String> loadReadable(String file, Plugin jpl){
        try{
            Map<String, String> toReturn = new HashMap<>();
            FileReader fileReader = new FileReader(new File(jpl.getDataFolder(), file));
            BufferedReader br = new BufferedReader(fileReader);
            String buff;
            while((buff = br.readLine()) != null){
                if(buff.startsWith("#"))
                    continue;
                String[] data = buff.split(";");
                toReturn.put(data[0], data[1]);
            }
            fileReader.close();
            br.close();
            return toReturn;
        }catch(IOException e){e.printStackTrace();}
        return null;
    }
}
