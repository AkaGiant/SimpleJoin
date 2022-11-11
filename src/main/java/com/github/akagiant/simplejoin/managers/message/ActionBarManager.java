package com.github.akagiant.simplejoin.managers.message;

import com.github.akagiant.simplejoin.SimpleJoin;
import com.github.akagiant.simplejoin.util.ConfigUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;

public class ActionBarManager {

	private ActionBarManager() {
		//no instance
	}
	
	// Handle Action Bar Messages
	public static void sendActionBarMessage(Collection<? extends Player> playerCollection, String path) {
		String message = ConfigUtil.getString(SimpleJoin.config, path + ".message");
		int duration = ConfigUtil.getInt(SimpleJoin.config, path + ".duration");

		if (duration == 0) { return; }

		for (Player player : playerCollection) {
			sendActionbar(player, message, duration);
		}
	}

	public static void sendActionBarMessage(Player player, String path) {
		String message = ConfigUtil.getString(SimpleJoin.config, path + ".message");
		int duration = ConfigUtil.getInt(SimpleJoin.config, path + ".duration");

		if (duration == 0) { return; }

		sendActionbar(player, message, duration);

	}

	static int task;

	private static void sendActionbar(Player player, String message, int duration){
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(SimpleJoin.getPlugin(), new Runnable() {

			int time = 0;

			@Override
			public void run() {
				if (time == duration) {
					Bukkit.getScheduler().cancelTask(task);
				} else {
					time++;
					player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
				}

			}
		},0, 20);
	}

}
