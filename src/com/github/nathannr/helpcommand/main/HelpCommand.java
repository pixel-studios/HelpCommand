package com.github.nathannr.helpcommand.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.nathannr.helpcommand.listener.CommandPreprocess;
import com.github.nathannr.helpcommand.listener.Join;

public class HelpCommand extends JavaPlugin {

	public static final String consoleprefix = "[HelpCommand] ";
	public static final int resource = 23444;
	
	@Override
	public void onEnable() {
		metrics();
		regEvents();
		initConfig();
		if(this.getConfig().getBoolean("HelpCommand.UpdateCheckOnStart")) {
			checkUpdate();
		} else {
			System.out.println(consoleprefix + "The update check on start is disabled in the config file");
		}
		System.out.println(consoleprefix + "Plugin by Nathan_N version " + getDescription().getVersion() + " enabled!");
	}
	
	@Override
	public void onDisable() {
		System.out.println(consoleprefix + "Plugin by Nathan_N version " + getDescription().getVersion() + " disabled!");
	}
	
	private void regEvents() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new CommandPreprocess(this), this);
		pm.registerEvents(new Join(this), this);
	}
	
	private void metrics() {
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initConfig() {
		this.reloadConfig();
		
		this.getConfig().options().header("HelpCommand plugin by Nathan_N (https://www.spigotmc.org/resources/" + resource + "/)");
		if(this.getConfig().getStringList("HelpCommand.HelpMessage.Default").isEmpty()) {
			List<String> list = this.getConfig().getStringList("HelpCommand.HelpMessage.Default");
			list.add("&6------------------ Help -----------------------&r");
			list.add("&e- Type &o/hub&e to get back to the hub&r");
			list.add("&e- Type &o/report <Player>&e to report a player&r");
			list.add("&e- Visit our website for more help: &nhttps://www.spigotmc.org/resources/" + resource + "/&r");
			list.add("&6------------------ Help -----------------------&r");
			this.getConfig().set("HelpCommand.HelpMessage.Default", list);
			
			List<String> list2 = this.getConfig().getStringList("HelpCommand.HelpMessage.World.hub");
			list2.add("&6------------------ Hub-help ------------------&r");
			list2.add("&e- Type &o/plotworld&e to get to the plotworld&r");
			list2.add("&e- Type &o/skypvp&e to get to skypvp&r");
			list2.add("&e- Visit our website for more help: &nhttps://www.spigotmc.org/resources/" + resource + "/&r");
			list2.add("&6------------------ Help -----------------------&r");
			this.getConfig().set("HelpCommand.HelpMessage.World.hub", list2);
			
			List<String> list3 = this.getConfig().getStringList("HelpCommand.HelpMessage.World.skypvp");
			list3.add("&6----------------- SkyPvP-help -----------------&r");
			list3.add("&e- Type &o/hub&e to get back to the hub&r");
			list3.add("&e- Type &o/kit&e to choose a kit&r");
			list3.add("&e- Type &o/report <Player>&e to report a hacker&r");
			list3.add("&e- Visit our website for more help: &nhttps://www.spigotmc.org/resources/" + resource + "/&r");
			list3.add("&6------------------ Help -----------------------&r");
			this.getConfig().set("HelpCommand.HelpMessage.World.skypvp", list3);
			
			List<String> list4 = this.getConfig().getStringList("HelpCommand.HelpMessage.World.plotworld");
			list4.add("&6---------------- Plotworld-help ----------------&r");
			list4.add("&e- Type &o/plotme auto&e to claim a plot&r");
			list4.add("&e- Type &o/plotme home&e to get back to your plot&r");
			list4.add("&e- Type &o/hub&e to get back to the hub&r");
			list4.add("&e- Visit our website for more help: &nhttps://www.spigotmc.org/resources/" + resource + "/&r");
			list4.add("&6------------------ Help -----------------------&r");
			this.getConfig().set("HelpCommand.HelpMessage.World.plotworld", list4);
		}
		this.getConfig().addDefault("HelpCommand.UpdateNotification", true);
		this.getConfig().addDefault("HelpCommand.UpdateCheckOnStart", true);
		
		
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
	}
	
	
	
	public void checkUpdate() {
		try {
			System.out.println(consoleprefix + "Checking for updates...");
            HttpURLConnection con = (HttpURLConnection) new URL(
                    "http://www.spigotmc.org/api/general.php").openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.getOutputStream()
                    .write(("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=" + resource)
                            .getBytes("UTF-8"));
            String version = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            if (!version.equalsIgnoreCase(this.getDescription().getVersion())) {
                System.err.println(consoleprefix + "A new update is aviable: version " + version + ", download it here: " + "https://www.spigotmc.org/resources/" + resource + "/");
            } else {
            	System.out.println(consoleprefix + "You're running the newest plugin version!");
            }
        } catch (Exception ex) {
           System.err.println(consoleprefix + "Failed to check for updates on spigotmc.org");
        }
	}
	public void playerJoinCheckUpdate(Player p) {
		try {
            HttpURLConnection con = (HttpURLConnection) new URL(
                    "http://www.spigotmc.org/api/general.php").openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.getOutputStream()
                    .write(("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=" + resource)
                            .getBytes("UTF-8"));
            String version = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            if (!version.equalsIgnoreCase(this.getDescription().getVersion())) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "§8[§2HelpCommand§8] §r" + "§6Version §e" + version + "§6 of HelpCommand is aviable, download it here: §n" + "https://www.spigotmc.org/resources/" + resource + "/" + "§r"));
            }
        } catch (Exception ex) {}
	}
	
}
