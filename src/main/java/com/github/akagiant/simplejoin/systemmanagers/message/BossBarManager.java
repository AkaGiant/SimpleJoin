package com.github.akagiant.simplejoin.systemmanagers.message;

import com.github.akagiant.simplejoin.SimpleJoin;
import com.github.akagiant.simplejoin.util.ConfigUtil;
import com.github.akagiant.simplejoin.util.Logger;
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

public class BossBarManager {

	private BossBarManager() {
		//no instance
	}
	
	// Handle Boss Bar Messages
	public static void sendBossBarMessage(Collection<? extends Player> playerCollection, Player target, String path) {
		int duration = ConfigUtil.getInt(SimpleJoin.config, path + ".duration");

		BossBar bossBar = getBossBar(path);
		if (bossBar == null) return;

		bossBar.setTitle(MessageManager.formatPlaceholders(target, bossBar.getTitle()));

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
		int duration = ConfigUtil.getInt(SimpleJoin.config, path + ".duration");

		BossBar bossBar = getBossBar(path);
		if (bossBar == null) return;

		bossBar.setTitle(MessageManager.formatPlaceholders(target, bossBar.getTitle()));
		bossBar.addPlayer(receiver);
		new BukkitRunnable() {
			@Override
			public void run() {
				bossBar.removePlayer(receiver);
			}
		}.runTaskLater(SimpleJoin.getPlugin(), (duration * 20L));
	}

	private static BossBar getBossBar(String path) {
		String title = ConfigUtil.getString(SimpleJoin.config, path + ".title");
		String stringBarColor = ConfigUtil.getString(SimpleJoin.config, path + ".color");
		String stringBarStyle = ConfigUtil.getString(SimpleJoin.config, path + ".style");
		List<String> stringBarFlags = ConfigUtil.getStringList(SimpleJoin.config, path + ".flags");

		BarColor barColor;
		try {
			barColor = BarColor.valueOf(stringBarColor);
		} catch (IllegalArgumentException e) {
			Logger.severe("&f" + path + ".color is not a valid bar color");
			return null;
		}

		BarStyle barStyle;
		try {
			barStyle = BarStyle.valueOf(stringBarStyle);
		} catch (IllegalArgumentException e) {
			Logger.severe("&f" + path + ".color is not a valid bar color");
			return null;
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

		return Bukkit.getServer().createBossBar(title, barColor, barStyle, barFlags.toArray(new BarFlag[0]));
	}

}
