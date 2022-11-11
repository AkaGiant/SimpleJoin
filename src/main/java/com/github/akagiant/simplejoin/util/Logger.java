package com.github.akagiant.simplejoin.util;

import com.github.akagiant.simplejoin.SimpleJoin;
import me.akagiant.giantapi.util.ColorManager;
import org.bukkit.Bukkit;

public class Logger {

	private Logger() {
		//no instance
	}

	public static void info(String msg) {
		Bukkit.getConsoleSender().sendMessage(
				ColorManager.formatColours("&8[&b" + SimpleJoin.getPlugin().getName() + " &b&lINFO&8] " + msg)
		);
	}

	public static void warn(String msg) {
		Bukkit.getConsoleSender().sendMessage(
				ColorManager.formatColours("&8[&6" + SimpleJoin.getPlugin().getName() + " &6&lWARN&8] " + msg)
		);
	}

	public static void severe(String msg) {
		Bukkit.getConsoleSender().sendMessage(
				ColorManager.formatColours("&8[&c" + SimpleJoin.getPlugin().getName() + " &c&lSEVERE&8] " + msg)
		);
	}

	public static void toConsole(String msg) {
		Bukkit.getConsoleSender().sendMessage(
				ColorManager.formatColours("&8[&b" + SimpleJoin.getPlugin().getName() + "&8] " + msg)
		);
	}

	public static void dev(String msg) {
		Bukkit.getConsoleSender().sendMessage(
				ColorManager.formatColours("&8[&d" + SimpleJoin.getPlugin().getName() + " &lDEV&8] &f" + msg)
		);
	}

}
