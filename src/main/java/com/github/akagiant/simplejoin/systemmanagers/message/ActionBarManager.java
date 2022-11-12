package com.github.akagiant.simplejoin.systemmanagers.message;

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

	public static void sendActionBarMessage(Collection<? extends Player> playerCollection, Player target, String path) {
		String message = ConfigUtil.getString(SimpleJoin.config, path + ".message");
		int duration = ConfigUtil.getInt(SimpleJoin.config, path + ".duration");

		// If the duration or message is null, stop. Config Util will notify the user.
		if (duration == 0 || message.equals("")) { return; }

		for (Player player : playerCollection) {
			// Don't run for the target as they have their own.
			if (player.getUniqueId().equals(target.getUniqueId())) continue;
			sendActionbar(player, target, message, duration);
		}
	}

	public static void sendActionBarMessage(Player player, String path) {
		String message = ConfigUtil.getString(SimpleJoin.config, path + ".message");
		int duration = ConfigUtil.getInt(SimpleJoin.config, path + ".duration");

		// If the duration or message is null, stop. Config Util will notify the user.
		if (duration == 0 || message.equals("")) { return; }

		sendActionbar(player, player, message, duration);
	}

	static int task;

	private static void sendActionbar(Player player, Player target, String message, int duration) {
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(SimpleJoin.getPlugin(), new Runnable() {
			int time = 0;

			@Override
			public void run() {
				if (time == duration) {
					Bukkit.getScheduler().cancelTask(task);
					return;
				}

				time++;
				player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
						TextComponent.fromLegacyText(MessageManager.formatPlaceholders(target, message))
				);
			}
		},0, 20);
	}

}
