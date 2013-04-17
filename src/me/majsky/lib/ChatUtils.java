package me.majsky.lib;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class ChatUtils {
    
    public static void sendMessaegeAndSound(Player p, String msg, Sound sound){
        ChatUtils.sendMesseageAndSound(p, msg, sound, 1f, 1f);
    }
    
    
    public static void sendMesseageAndSound(Player p, String msg, Sound sound, float pitch, float volume){
        p.sendMessage(msg);
        p.playSound(p.getLocation(), sound, pitch, volume);
    }
}
