package me.majsky.lib.util;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;

public class ParticleUtils {
    
    public static void createParticlesWithSound(World world, Location location, Effect effect, int data, Sound sound){
        ParticleUtils.createParticlesWithSound(world, location, effect, data, sound, 1f, 1f);
    }
    
    public static void createParticlesWithSound(World world, Location location, Effect effect, int data, Sound sound, float pitch, float volume){
        world.playEffect(location, effect, data, 30);
        world.playSound(location, sound, volume, pitch);
    }
}
