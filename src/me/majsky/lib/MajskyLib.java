package me.majsky.lib;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import me.majsky.lib.simpleConfig.ConfigCategory;
import me.majsky.lib.simpleConfig.SimpleConfigFile;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class MajskyLib extends JavaPlugin{
    
    public static Logger logger;
    
    public static MajskyLib instance;

    public MajskyLib(){
        logger = Logger.getLogger("MajskyLib");
        instance = this;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!command.getName().equalsIgnoreCase("majskylib"))
            return false;
        if(args.length == 0){
            sender.sendMessage(ChatColor.RED + "Usage: " + label + " <command>");
            return true;
        }
        switch(args[0]){
            case "version":
                sender.sendMessage("API verison: ");
                sender.sendMessage(this.getDescription().getFullName());
                return true;
            case "help":
                sender.sendMessage(ChatColor.GOLD + "Avaiable commands:");
                sender.sendMessage(ChatColor.YELLOW + " version");
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onLoad() {
        SimpleConfigFile config = new SimpleConfigFile(this);
        config.load();
        ConfigCategory category = config.getOrCreateCategory("AllowedPlugins");
        
        File plFolder = new File(this.getDataFolder(), "plugins");
        if(!plFolder.exists())
            plFolder.mkdirs();
        String[] content = plFolder.list();
        for(String name:content){
            if(new File(plFolder, name).isDirectory())
                continue;
            if(category.containsValue(name) && category.getValue(name).equalsIgnoreCase("false")){
                logger.warning("Skipping plugin '" + name + "' because its disabled in config");
                continue;
            }else if(!category.containsValue(name))
                category.addValue(name, "true");
            try{
                Plugin plugin = Bukkit.getPluginManager().loadPlugin(new File(plFolder, name));
                if(plugin != null)
                    logger.info("[MajskyLib] Found and loaded subplugin '" + plugin.getName() + "'");
            }catch(Exception e){e.printStackTrace();}
        }
        config.save();
    }

    @Override
    public void onEnable() {
        logger = this.getLogger();
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> toReturn = new ArrayList<>();
        if(command.getName().equalsIgnoreCase("majskylib") && args.length == 0){
            toReturn.add("version");
            toReturn.add("help");
        }
        return toReturn;
    }
    
}
