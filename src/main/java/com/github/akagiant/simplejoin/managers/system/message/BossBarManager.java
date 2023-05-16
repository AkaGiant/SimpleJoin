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
import com.github.akagiant.simplejoin.utility.internal.Logger;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class BossBarManager {
	
	private BossBarManager() {
		//no instance
		
	}
	
	// Handle Boss Bar Messages
	public static void sendBossBarMessage(Collection<? extends Player> playerCollection, Player target, String path) {
		Optional<Integer> optionalDuration = ConfigUtil.getInt(ConfigManager.config, path + ".duration");
		if (!optionalDuration.isPresent()) return;
		int duration = optionalDuration.get();
		
		Optional<BossBar> optionalBossBar = getBossBar(path);
		if (!optionalBossBar.isPresent()) return;
		BossBar bossBar = optionalBossBar.get();

		bossBar.setTitle(PlaceholderAPI.setPlaceholders(target, bossBar.getTitle()));

		for (Player player : playerCollection) {
			if (player.getUniqueId().equals(target.getUniqueId())) continue;
			bossBar.addPlayer(player);
		}

		new BukkitRunnable() {
			@Override
			public void run() {
				bossBar.removeAll();
			}
		}.runTaskLater(SimpleJoin.getPlugin(), (duration * 20L));
	}

	public static void sendBossBarMessage(Player receiver, Player target, String path) {
		Optional<Integer> optionalDuration = ConfigUtil.getInt(ConfigManager.config, path + ".duration");
		if (!optionalDuration.isPresent()) return;
		int duration = optionalDuration.get();

		Optional<BossBar> optionalBossBar = getBossBar(path);
		if (!optionalBossBar.isPresent()) return;
		BossBar bossBar = optionalBossBar.get();

		String title = bossBar.getTitle();

		if (SimpleJoin.isHasPlaceholderAPI()) {
			bossBar.setTitle(PlaceholderAPI.setPlaceholders(target, title));
		}

		bossBar.addPlayer(receiver);
		new BukkitRunnable() {
			@Override
			public void run() {
				bossBar.removePlayer(receiver);
			}
		}.runTaskLater(SimpleJoin.getPlugin(), (duration * 20L));
	}

	private static Optional<BossBar> getBossBar(String path) {
		String title = ConfigUtil.getString(ConfigManager.config, path + ".title");
		String color = ConfigUtil.getString(ConfigManager.config, path + ".color");
		String style = ConfigUtil.getString(ConfigManager.config, path + ".style");
		
		List<String> stringBarFlags = ConfigUtil.getStringList(ConfigManager.config, path + ".flags");

		BarColor barColor;
		try {
			barColor = BarColor.valueOf(color);
		} catch (IllegalArgumentException e) {
			Logger.severe("&f" + path + ".color is not a valid bar color");
			return Optional.empty();
		}

		BarStyle barStyle;
		try {
			barStyle = BarStyle.valueOf(style);
		} catch (IllegalArgumentException e) {
			Logger.severe("&f" + path + ".color is not a valid bar color");
			return Optional.empty();
		}

		List<BarFlag> barFlags = new ArrayList<>();
		for (String stringFlag : stringBarFlags) {
			BarFlag flag;

			try {
				flag = BarFlag.valueOf(stringFlag);
			} catch (IllegalArgumentException e) {
				Logger.severe("&f" + stringFlag + " is not a valid bar flag @ " + path + ".flags");
				continue;
			}

			if (barFlags.contains(flag)) continue;
			barFlags.add(flag);
		}

		return Optional.of(Bukkit.getServer().createBossBar(title, barColor, barStyle, barFlags.toArray(new BarFlag[0])));
	}

}
