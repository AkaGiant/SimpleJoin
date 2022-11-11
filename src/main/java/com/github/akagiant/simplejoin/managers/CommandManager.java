package com.github.akagiant.simplejoin.managers;

import com.github.akagiant.simplejoin.SimpleJoin;
import com.github.akagiant.simplejoin.util.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;

public class CommandManager {

	private CommandManager() {
		//no instance
	}

	public static void runCommands(Collection<? extends Player> playerCollection, String path) {
		for (String stringCmd : ConfigUtil.getStringList(SimpleJoin.config, path)) {
			for (Player player : playerCollection) {
				stringCmd = stringCmd.replace("/", "");
				stringCmd = stringCmd.replace("%player_name%", player.getName());
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), stringCmd);
			}
		}
	}

	public static void runCommands(Player player, String path) {
		for (String stringCmd : ConfigUtil.getStringList(SimpleJoin.config, path)) {
			stringCmd = stringCmd.replace("/", "");
			stringCmd = stringCmd.replace("%player_name%", player.getName());
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), stringCmd);
		}
	}

}
