package escapecraft.echosting;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class ECEventListener implements Listener {
	
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent evt) {
		World world = evt.getWorld();
		ECHosting.pl.reloadConfig();
		FileConfiguration fc = ECHosting.pl.getConfig();
		if (fc.contains("settings." + world.getName() + ".weather")) {
			boolean weather = fc.getBoolean("settings." + world.getName() + ".weather");
			if (weather) {
				if (evt.toWeatherState() != false) {
					evt.setCancelled(true);
					return;
				}
			}
		}
		if (fc.contains("settings.global")) {
			boolean weather = fc.getBoolean("settings.global.weather");
			if (weather) {
				if (evt.toWeatherState() != false) {
					evt.setCancelled(true);
					return;
				}
			}
		}
	}
	
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent evt) {
		Entity e = evt.getEntity();
		World world = e.getWorld();
		String eType = e.getType().toString().toLowerCase();
		ECHosting.pl.reloadConfig();
		FileConfiguration fc = ECHosting.pl.getConfig();
		if (fc.contains("settings." + world.getName() + ".mob-griefing." + eType)) {
			boolean allowed = fc.getBoolean("settings." + world.getName() + ".mob-griefing." + eType);
			if (!allowed) {
				evt.setCancelled(true);
				return;
			}
		}
		if (fc.contains("settings." + world.getName() + ".mob-griefing.all")) {
			boolean allowed = fc.getBoolean("settings." + world.getName() + ".mob-griefing.all");
			if (!allowed) {
				evt.setCancelled(true);
				return;
			}
		}
		if (fc.contains("settings.global.mob-griefing." + eType)) {
			boolean allowed = fc.getBoolean("settings." + world.getName() + ".mob-griefing." + eType);
			if (!allowed) {
				evt.setCancelled(true);
				return;
			}
		}
		if (fc.contains("settings.global.mob-griefing.all")) {
			boolean allowed = fc.getBoolean("settings." + world.getName() + ".mob-griefing.all");
			if (!allowed) {
				evt.setCancelled(true);
				return;
			}
		}
	}
	
	
	@EventHandler
	public void onLightningStrike(LightningStrikeEvent evt) {
		World world = evt.getWorld();
		ECHosting.pl.reloadConfig();
		FileConfiguration fc = ECHosting.pl.getConfig();
		if (fc.contains("settings." + world.getName() + ".lightning-fire")) {
			boolean light = fc.getBoolean("settings." + world.getName() + ".lightning-fire");
			if (!light) {
				evt.getLightning().setFireTicks(0);
				return;
			}
		}
		if (fc.contains("settings.global")) {
			boolean light = fc.getBoolean("settings.global.lightning-fire");
			if (!light) {
				evt.getLightning().setFireTicks(0);
				return;
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent evt) {
		if (evt.getAction() == Action.PHYSICAL && evt.hasBlock()) {
			if (evt.getClickedBlock().getType() == Material.CROPS || evt.getMaterial() == Material.CROPS) {
				evt.setCancelled(true);
			}
		}
	}
}
