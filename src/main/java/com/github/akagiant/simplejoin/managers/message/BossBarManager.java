package com.github.akagiant.simplejoin.managers.message;

import com.github.akagiant.simplejoin.SimpleJoin;
import com.github.akagiant.simplejoin.util.ConfigUtil;
import com.github.akagiant.simplejoin.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
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
	public static void sendBossBarMessage(Collection<? extends Player> playerCollection, String path) {
		int duration = ConfigUtil.getInt(SimpleJoin.config, path + ".duration");

		org.bukkit.boss.BossBar bossBar = getBossBar(path);
		if (bossBar == null) return;

		for (Player player : playerCollection) {
			bossBar.addPlayer(player);
		}

		new BukkitRunnable() {
			@Override
			public void run() {
				bossBar.removeAll();
			}
		}.runTaskLater(SimpleJoin.getPlugin(), duration);


	}

	public static void sendBossBarMessage(Player player, String path) {
		int duration = ConfigUtil.getInt(SimpleJoin.config, path + ".duration");

		org.bukkit.boss.BossBar bossBar = getBossBar(path);
		if (bossBar == null) return;

		bossBar.addPlayer(player);
		new BukkitRunnable() {
			@Override
			public void run() {
				bossBar.removePlayer(player);
			}
		}.runTaskLater(SimpleJoin.getPlugin(), duration);
	}

	private static org.bukkit.boss.BossBar getBossBar(String path) {
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
