package me.majsky.lib.util;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
/**
 * Simple chat utilities
 * @author majsky
 *
 */
public class ChatUtils {
    /**
     * @see ChatUtils#sendMesseageAndSound(Player, String, Sound, float, float)
     */
    public static void sendMessaegeAndSound(Player p, String msg, Sound sound){
        ChatUtils.sendMesseageAndSound(p, msg, sound, 1f, 1f);
    }
    
    /**
     * Sends message to player and plays sound to him
     * @param p - Message target
     * @param msg - Message itself
     * @param sound - Sound to play
     * @param pitch - Sound pitch
     * @param volume - Sound volume
     */
    public static void sendMesseageAndSound(Player p, String msg, Sound sound, float pitch, float volume){
        p.sendMessage(msg);
        p.playSound(p.getLocation(), sound, pitch, volume);
    }
}
