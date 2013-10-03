package escapecraft.echosting.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import escapecraft.echosting.ECHosting;

public class ToggleCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2,
			String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("toggle")) {
			if ((sender.hasPermission("echosting.toggle") || sender instanceof ConsoleCommandSender)) {
				if (args.length > 0) {
					World w = null;
					
					if (args.length > 2) {
						if (args[0].equalsIgnoreCase("-w")) {
							String wName = args[1];
							w = Bukkit.getWorld(wName);
							if (w == null) {
								sendMessage(sender, ChatColor.RED + "World " + wName + " not found!");
								return true;
							}
						}
					}
					
					switch (args[0].toLowerCase()) {
					case "fire":
						boolean on = false;
						String path = "settings." + (w == null ? "global" : w.getName().toLowerCase()) + ".fire-spread";
						ECHosting.pl.reloadConfig();
						if (ECHosting.pl.getConfig().contains(path)) {
							on = ECHosting.pl.getConfig().getBoolean(path);
						}
						sendMessage(sender, ChatColor.GOLD + "Fire spread is now " + (!on ? ChatColor.GREEN + "on" : ChatColor.RED + "off") + ChatColor.GOLD + (w == null ? " globally" : " in world " + ChatColor.WHITE + w.getName()));
						ECHosting.pl.getConfig().set(path, !on);
						if (w == null) {
							for (World wa : Bukkit.getWorlds()) {
								wa.setGameRuleValue("doFireTick", Boolean.toString(!on));
							}
						} else {
							w.setGameRuleValue("doFireTick", Boolean.toString(!on));
						}
						ECHosting.pl.saveConfig();
						return true;
					case "mob-griefing":
						String type = "all";
						int tArg = 0;
						if (w == null && args.length > 1) {
							tArg = 1;
						} else if (w != null && args.length > 3) {
							tArg = 3;
						}
						if (tArg > 0) {
							type = args[tArg].toLowerCase();
							switch (type) {
							case "creeper":
							case "enderman":
							case "wither":
								break;
							default:
								sendMessage(sender, ChatColor.RED + "Types: creeper, enderman, wither");
							}
						}
						on = false;
						path = "settings." + (w == null ? "global" : w.getName().toLowerCase()) + ".mob-griefing." + type;
						ECHosting.pl.reloadConfig();
						if (ECHosting.pl.getConfig().contains(path)) {
							on = ECHosting.pl.getConfig().getBoolean(path);
						}
						sendMessage(sender, ChatColor.GOLD + "Mob griefing is now " + (!on ? ChatColor.GREEN + "on" : ChatColor.RED + "off") + ChatColor.GOLD + (w == null ? " globally" : " in world " + ChatColor.WHITE + w.getName()) + ChatColor.GOLD + " for " + (tArg == 0 ? "all mobs" : ChatColor.WHITE + type));
						ECHosting.pl.getConfig().set(path, !on);
						ECHosting.pl.saveConfig();
						return true;
					case "weather":
						on = false;
						path = "settings." + (w == null ? "global" : w.getName().toLowerCase()) + ".weather";
						ECHosting.pl.reloadConfig();
						if (ECHosting.pl.getConfig().contains(path)) {
							on = ECHosting.pl.getConfig().getBoolean(path);
						}
						sendMessage(sender, ChatColor.GOLD + "Weather is now " + (!on ? ChatColor.GREEN + "on" : ChatColor.RED + "off") + ChatColor.GOLD + (w == null ? " globally" : " in world " + ChatColor.WHITE + w.getName()));
						ECHosting.pl.getConfig().set(path, !on);
						ECHosting.pl.saveConfig();
						return true;
					case "lightning-fire":
						on = false;
						path = "settings." + (w == null ? "global" : w.getName().toLowerCase()) + ".lightning-fire";
						ECHosting.pl.reloadConfig();
						if (ECHosting.pl.getConfig().contains(path)) {
							on = ECHosting.pl.getConfig().getBoolean(path);
						}
						sendMessage(sender, ChatColor.GOLD + "Lightning fire is now " + (!on ? ChatColor.GREEN + "on" : ChatColor.RED + "off") + ChatColor.GOLD + (w == null ? " globally" : " in world " + ChatColor.WHITE + w.getName()));
						ECHosting.pl.getConfig().set(path, !on);
						ECHosting.pl.saveConfig();
						return true;
					case "crop-trample":
						on = false;
						path = "settings." + (w == null ? "global" : w.getName().toLowerCase()) + ".crop-trample";
						ECHosting.pl.reloadConfig();
						if (ECHosting.pl.getConfig().contains(path)) {
							on = ECHosting.pl.getConfig().getBoolean(path);
						}
						sendMessage(sender, ChatColor.GOLD + "Crop trampling is now " + (!on ? ChatColor.GREEN + "on" : ChatColor.RED + "off") + ChatColor.GOLD + (w == null ? " globally" : " in world " + ChatColor.WHITE + w.getName()));
						ECHosting.pl.getConfig().set(path, !on);
						ECHosting.pl.saveConfig();
						return true;
					default:
						sendMessage(sender, ChatColor.RED + "Usage ([] = optional, <> = required): /toggle [-w [world1]] -t <fire|mob-griefing|weather|lightning-fire|crop-trample>");
						return true;
					}
				} else {
					sendMessage(sender, ChatColor.RED + "Usage ([] = optional, <> = required): /toggle [-w [world1]] -t <fire|mob-griefing|weather|lightning-fire|crop-trample>");
					return true;
				}
			} else {
				sendMessage(sender, ChatColor.RED + "No permission!");
				return true;
			}
			
		}
		
		return false;
	}
	
	public void sendMessage(CommandSender sender, String message) {
		if (sender instanceof Player) {
			sender.sendMessage(message);
		} else if (sender instanceof ConsoleCommandSender) {
			ECHosting.log.info("[ECHosting] " + message);
		}
	}

}
