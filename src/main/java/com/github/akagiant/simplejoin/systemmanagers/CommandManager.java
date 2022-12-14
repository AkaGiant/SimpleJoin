package com.github.akagiant.simplejoin.systemmanagers;

import com.github.akagiant.simplejoin.SimpleJoin;
import com.github.akagiant.simplejoin.systemmanagers.message.MessageManager;
import com.github.akagiant.simplejoin.util.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;

public class CommandManager {

	private CommandManager() {
		//no instance
	}

	public static void runCommands(Collection<? extends Player> playerCollection, Player target, String path) {
		for (String stringCmd : ConfigUtil.getStringList(SimpleJoin.config, path)) {
			for (Player player : playerCollection) {
				if (player.getUniqueId().equals(target.getUniqueId())) continue;
				stringCmd = stringCmd.replace("/", "");
				stringCmd = MessageManager.formatPlaceholders(player, target, stringCmd);
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), stringCmd);
			}
		}
	}

	public static void runCommands(Player player, String path) {
		for (String stringCmd : ConfigUtil.getStringList(SimpleJoin.config, path)) {
			stringCmd = stringCmd.replace("/", "");
			stringCmd = MessageManager.formatPlaceholders(player, stringCmd);
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), stringCmd);
		}
	}

}
