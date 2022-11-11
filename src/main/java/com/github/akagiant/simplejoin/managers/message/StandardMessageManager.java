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
	public static void sendNormalMessage(Collection<? extends Player> playerCollection, String path) {
		List<String> messages = ConfigUtil.getStringList(SimpleJoin.config, path);
		for (Player player : playerCollection) {
			for (String str : messages) {
				str = str.replace("%player_name%", player.getName());
				player.sendMessage(str);
			}
		}
	}

	public static void sendNormalMessage(Player player, String path) {
		List<String> messages = ConfigUtil.getStringList(SimpleJoin.config, path);
		for (String str : messages) {
			str = str.replace("%player_name%", player.getName());
			player.sendMessage(str);
		}
	}

}
