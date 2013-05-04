package me.majsky.lib;

import org.bukkit.Location;
import org.bukkit.World;

public class LocationConverter{
    
    public static final int VERSION = 1;
    
    /**
     * Encodes location into string, so it can be easily saved
     * 
     * @param location - Location object to encode
     * @return Encoded Location object
     * 
     * @see org.bukkit.Location
     */
    public static String encode(Location location){
        StringBuilder s = new StringBuilder();
        
        if(location == null)
            return null;
        
        //Add header to string
        s.append("Location:");
        s.append(VERSION);
        s.append(":");
        
        //Add world name to string
        s.append(location.getWorld().getName());
        s.append(":");
        
        //Add x, y and z to string
        s.append(location.getX());
        s.append(":");
        s.append(location.getY());
        s.append(":");
        s.append(location.getZ());
        s.append(":");
        
        //Add yaw and pitch to string
        s.append(location.getPitch());
        s.append(":");
        s.append(location.getY());
        
        return s.toString();
    }
    
    /**
     * Decodes previously encoded Location
     * 
     * @param s - String object to decode
     * @return Location contained in s
     * 
     * @see org.bukkit.Location
     */
    public static Location decode(String s){
        double x, y, z;
        float pitch, yaw;
        World w = null;
        
        x = y = z = pitch = yaw = 0;
        
        String[] msg = s.split(":");
        
        //Read header from string
        if(!msg[0].equalsIgnoreCase("Location")){
            MajskyLib.logger.warning("Something tried to decode Non-Location string!");
            return null;
        }
        
        int version = Integer.parseInt(msg[1]);
        if(version > VERSION){
            MajskyLib.logger.warning("Something tried to decode Location that is newer than me! (Outdated LIB?)");
            return null;
        }
        //Read world from string
        w = MajskyLib.instance.getServer().getWorld(msg[2]);
        
        //Read x, y and z from string
        x = Double.parseDouble(msg[3]);
        y = Double.parseDouble(msg[4]);
        z = Double.parseDouble(msg[5]);
        
        //Read pitch and yaw from string
        yaw = Float.parseFloat(msg[6]);
        pitch = Float.parseFloat(msg[7]);
        
        return new Location(w, x, y, z, yaw, pitch);
    }
}
