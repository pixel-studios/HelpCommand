package com.github.nathannr.helpcommand.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.github.nathannr.helpcommand.main.HelpCommand;

public class CommandPreprocess implements Listener {

	private HelpCommand plugin;
	public CommandPreprocess(HelpCommand plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) { 
		Player p = event.getPlayer();
		String cmd = event.getMessage();
		if(cmd.equalsIgnoreCase("/help") || cmd.equalsIgnoreCase("/?")) {
			if(plugin.getConfig().getString("HelpCommand.HelpMessage.World." + p.getWorld().getName()) != null) {
				String string0 = String.join("\n", plugin.getConfig().getStringList("HelpCommand.HelpMessage.World." + p.getWorld().getName()));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', string0));
			} else {
				plugin.getConfig().set("HelpCommand.HelpMessage.World." + p.getWorld().getName(), plugin.getConfig().getStringList("HelpCommand.HelpMessage.Default"));
				plugin.saveConfig();
				String string0 = String.join("\n", plugin.getConfig().getStringList("HelpCommand.HelpMessage.World." + p.getWorld().getName()));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', string0));
				
			}
		}
	}
}
