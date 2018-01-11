package com.icodehaven.entityrender;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.plugin.java.JavaPlugin;

import com.icodehaven.entityrender.cmds.Render;
import com.icodehaven.entityrender.utils.EntityHider;
import com.icodehaven.entityrender.utils.EntityHider.Policy;

public class Core extends JavaPlugin {
	
	public static Core instance;
	public static EntityHider entityHider;
	public static ArrayList<Player> playersTNT = new ArrayList<Player>();

	@Override
	public void onEnable() {
		getCommand("render").setExecutor(new Render(this));
		entityHider = new EntityHider(this, Policy.BLACKLIST);
		getConfig().options().copyHeader(true);
		getConfig().options().copyDefaults(true);
		saveConfig();
		instance = this;
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				for (Player players : Bukkit.getServer().getOnlinePlayers()) {
					for (Entity entity : players.getNearbyEntities(getConfig().getInt("settings.x"), getConfig().getInt("settings.y"), getConfig().getInt("settings.z"))) {
						if (!(entity instanceof TNTPrimed || entity instanceof FallingBlock)) {
							continue;
						}
						if (playersTNT.contains(players)) {
							getEntityHider().hideEntity(players, entity);
						}
					}
				}
			}
		}, 0, 20 * getConfig().getInt("settings.time"));
		consoleMessage("&8[&cEntityRender&8] &fThe plugin has been enabled");
	}

	@Override
	public void onDisable() {
		consoleMessage("&8[&cEntityRender&8] &fThe plugin has been disabled");
		instance = null;
	}

	public static Core getInstance() {
		return instance;
	}

	public static EntityHider getEntityHider() {
		return entityHider;
	}

	public static ArrayList<Player> getPlayersTNTToggled() {
		return playersTNT;
	}

	public void consoleMessage(String msg) {
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}

}
