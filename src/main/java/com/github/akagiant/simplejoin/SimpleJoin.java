package com.github.akagiant.simplejoin;

import com.github.akagiant.simplejoin.util.Logger;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIConfig;
import dev.jorel.commandapi.RegisteredCommand;
import lombok.Getter;
import me.akagiant.giantapi.util.Config;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.C;

public final class SimpleJoin extends JavaPlugin {

	@Getter
	private static Plugin plugin;

	@Getter
	public static Config config;

	@Override
	public void onLoad() {
		CommandAPI.onLoad(new CommandAPIConfig().silentLogs(true));
	}

	@Override
	public void onEnable() {
		plugin = this;

		// Plugin startup logic
		Logger.toConsole("&m————————————————————————————————————");
		Logger.toConsole("&fPlugin is loading...");
		Logger.toConsole("&m——————————————&r &fCore &m&8————————————————");

		CommandAPI.onEnable(this);

		registerConfigurations();
		registerCommands();
		registerEvents();

		Logger.toConsole("&fCommands Loaded (&a" + (CommandAPI.getRegisteredCommands().size())+ "&f) &8| &fAliases: (&a" + getCommandAliasesCount() + "&f)");
		Logger.toConsole("&fPermissions Loaded (&a" + (getPermissionsCount())+ "&f)");

		Logger.toConsole("&m————————————————————————————————————");
		Logger.toConsole("&ahas been Enabled");
		Logger.toConsole("&m————————————————————————————————————");
		Logger.toConsole("&fDeveloped by &aAkaGiant");
		Logger.toConsole("&fVersion: &a" + getPlugin().getDescription().getVersion());
		Logger.toConsole("&m————————————————————————————————————");
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
		CommandAPI.onDisable();
	}

	private void registerConfigurations() {
		config = new Config(this, "config");
	}

	private void registerCommands() {
	}

	private void registerEvents() {

	}

	private int getPermissionsCount() {
		int amount = 0;
		for (RegisteredCommand cmd : CommandAPI.getRegisteredCommands()) {
			if (cmd.permission() != null) { amount += 1; }
		}
		return amount;
	}

	private int getCommandAliasesCount() {
		int amount = 0;
		for (RegisteredCommand cmd : CommandAPI.getRegisteredCommands()) {
			if (cmd.aliases() != null) { amount += cmd.aliases().length; }
		}
		return amount;
	}
}
