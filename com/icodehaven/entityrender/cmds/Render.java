package com.icodehaven.entityrender.cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.icodehaven.entityrender.Core;

public class Render implements CommandExecutor {

	public Core plugin;

	public Render(Core instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		if (cmd.getName().equalsIgnoreCase("render")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("[ERROR] This command is only usable by players");
				return false;
			}
			Player player = (Player) sender;
			if(!player.hasPermission("entityrender.cmd.render")) {
				sendMessage(player, plugin.getConfig().getString("messages.no-permission"));
				return false;
			}
			if(Core.getPlayersTNTToggled().contains(player)) {
				Core.getPlayersTNTToggled().remove(player);
				sendMessage(player, plugin.getConfig().getString("messages.disabled"));
				return false;
			}
			Core.getPlayersTNTToggled().add(player);
			sendMessage(player, plugin.getConfig().getString("messages.enabled"));
		}
		return true;
	}
	
	public void sendMessage(Player player, String msg) {
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}

}
