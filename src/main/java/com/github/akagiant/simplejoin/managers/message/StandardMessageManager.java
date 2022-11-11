package com.github.akagiant.simplejoin.managers.message;

import com.github.akagiant.simplejoin.SimpleJoin;
import com.github.akagiant.simplejoin.util.ConfigUtil;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;

public class StandardMessageManager {

	private StandardMessageManager() {
		//no instance
	}
	
	// Handle Normal Messages
	public static void sendNormalMessage(Collection<? extends Player> playerCollection, Player target, String path) {
		List<String> messages = ConfigUtil.getStringList(SimpleJoin.config, path);
		for (Player player : playerCollection) {
			if (player.getUniqueId().equals(target.getUniqueId())) continue;
			for (String str : messages) {
				player.sendMessage(MessageManager.formatPlaceholders(target, str));
			}
		}
	}

	public static void sendNormalMessage(Player player, String path) {
		List<String> messages = ConfigUtil.getStringList(SimpleJoin.config, path);
		for (String str : messages) {
			player.sendMessage(MessageManager.formatPlaceholders(player, str));
		}
	}

}
