package com.github.akagiant.simplejoin.managers.message;

import com.github.akagiant.simplejoin.SimpleJoin;
import com.github.akagiant.simplejoin.util.ConfigUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.Collection;

public class ActionBarManager {

	private ActionBarManager() {
		//no instance
	}
	
	// Handle Action Bar Messages
	public static void sendActionBarMessage(Collection<? extends Player> playerCollection, String path) {
		String message = ConfigUtil.getString(SimpleJoin.config, path);
		for (Player player : playerCollection) {
			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
		}
	}

	public static void sendActionBarMessage(Player player, String path) {
		String message = ConfigUtil.getString(SimpleJoin.config, path);
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
	}
}
