package com.github.akagiant.simplejoin.managers.message;

import com.github.akagiant.simplejoin.SimpleJoin;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class MessageManager {

	private MessageManager() {
		//no instance
	}
	
	public static String formatPlaceholders(Player target, String message) {
		if (SimpleJoin.hasPAPI) {
			message = PlaceholderAPI.setPlaceholders(target, message);
		}

		message = message.replace("%total_joined%", String.valueOf(Bukkit.getServer().getOfflinePlayers().length));
		message = message.replace("%player_name%", target.getName());

		return message;
	}

	public static String formatPlaceholders(Player receiver, Player target, String message) {
		if (SimpleJoin.hasPAPI) {
			message = PlaceholderAPI.setPlaceholders(target, message);
		}

		message = message.replace("%total_joined%", String.valueOf(Bukkit.getServer().getOfflinePlayers().length));
		message = message.replace("%target_name%", target.getName());
		message = message.replace("%player_name%", receiver.getName());

		return message;
	}

}
