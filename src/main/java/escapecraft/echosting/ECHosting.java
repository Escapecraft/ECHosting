package escapecraft.echosting;

import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import escapecraft.echosting.commands.ToggleCommand;

public class ECHosting extends JavaPlugin {
	
	public static Logger log;
	public static Plugin pl;
	
	public void onEnable() {
		log = Logger.getLogger("Minecraft");
		pl = this;
		getServer().getPluginManager().registerEvents(new ECEventListener(), this);
		getCommand("toggle").setExecutor(new ToggleCommand());
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	public void onDisable() {
		reloadConfig();
		saveConfig();
	}
	
}
