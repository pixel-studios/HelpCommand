package com.github.nathannr.helpcommand.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.github.nathannr.helpcommand.main.HelpCommand;

public class Join implements Listener {

	private HelpCommand plugin;
	public Join(HelpCommand plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(plugin.getConfig().getBoolean("HelpCommand.UpdateNotification")) {
			if(p.isOp()) {
				plugin.playerJoinCheckUpdate(p);
			}
		}
	}
	
}
