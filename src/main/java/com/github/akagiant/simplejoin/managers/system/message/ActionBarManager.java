/*
 * This file is part of SimpleJoin, licensed under the MIT License.
 *
 *  Copyright (c) AkaGiant
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package com.github.akagiant.simplejoin.managers.system.message;

import com.github.akagiant.simplejoin.SimpleJoin;
import com.github.akagiant.simplejoin.utility.internal.ConfigManager;
import com.github.akagiant.simplejoin.utility.internal.ConfigUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Optional;

public class ActionBarManager {
	
	private ActionBarManager() {
		//no instance
	}
	
	public static void sendActionBarMessage(Collection<? extends Player> playerCollection, Player target, String path) {
		String message = ConfigUtil.getString(ConfigManager.config, path + ".message");
		
		Optional<Integer> optionalInteger = ConfigUtil.getInt(ConfigManager.config, path + ".duration");
		if (!optionalInteger.isPresent()) return;
		int duration = optionalInteger.get();

		// If the duration or message is null, stop. Config Util will notify the user.
		if (duration == 0 || message.equals("")) { return; }

		for (Player player : playerCollection) {
			// Don't run for the target as they have their own.
			if (player.getUniqueId().equals(target.getUniqueId())) continue;
			sendActionbar(player, target, message, duration);
		}
	}

	public static void sendActionBarMessage(Player player, String path) {
		String message = ConfigUtil.getString(ConfigManager.config, path + ".message");

		Optional<Integer> optionalInteger = ConfigUtil.getInt(ConfigManager.config, path + ".duration");
		if (!optionalInteger.isPresent()) return;
		int duration = optionalInteger.get();
		
		// If the duration or message is null, stop. Config Util will notify the user.
		if (duration == 0 || message.equals("")) return;

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
				if (SimpleJoin.isHasPlaceholderAPI()) {
					PlaceholderAPI.setPlaceholders(target, message);
				}
				player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
			}
		},0, 20);
	}

}
