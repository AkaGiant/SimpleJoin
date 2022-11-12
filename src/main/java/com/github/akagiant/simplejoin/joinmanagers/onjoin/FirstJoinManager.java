package com.github.akagiant.simplejoin.joinmanagers.onjoin;

import com.github.akagiant.simplejoin.SimpleJoin;
import com.github.akagiant.simplejoin.systemmanagers.CommandManager;
import com.github.akagiant.simplejoin.systemmanagers.EffectsManager;
import com.github.akagiant.simplejoin.systemmanagers.SoundManager;
import com.github.akagiant.simplejoin.systemmanagers.message.ActionBarManager;
import com.github.akagiant.simplejoin.systemmanagers.message.BossBarManager;
import com.github.akagiant.simplejoin.systemmanagers.message.StandardMessageManager;
import com.github.akagiant.simplejoin.systemmanagers.message.TitleMessageManager;
import com.github.akagiant.simplejoin.util.ConfigUtil;
import me.akagiant.giantapi.util.Config;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

public class FirstJoinManager {

	private FirstJoinManager() {
		//no instance
	}
	
	public static void firstJoin(Player player, PlayerJoinEvent e) {
		if (!firstJoinEnabled()) {
			e.setJoinMessage("");
			return;
		}

		handleToPlayer(player, e);
		handleToEveryone(player, e);
	}

	private static void handleToPlayer(Player player, PlayerJoinEvent e) {
		String path = "global-settings.first-join.to-player";
		Config config = SimpleJoin.config;

		if (ConfigUtil.isSet(config, path + ".sound-effect")) {
			SoundManager.playSound(player, path + ".sound-effect");
		}

		if (ConfigUtil.isSet(config, path + ".effects")) {
			EffectsManager.addEffects(player, path + ".effects");
		}

		if (ConfigUtil.isSet(config, path + ".commands")) {
			CommandManager.runCommands(player, path + ".commands");
		}

		if (ConfigUtil.isSet(config, path + ".title-message")) {
			Bukkit.getLogger().severe("Sending Title Message");
			TitleMessageManager.sendTitleMessage(player, path + ".title-message");
		}

		if (ConfigUtil.isSet(config, path + ".boss-bar")) {
			BossBarManager.sendBossBarMessage(player, player, path + ".boss-bar");
		}

		if (ConfigUtil.isSet(config, path + ".message")) {
			StandardMessageManager.sendNormalMessage(player, path + ".message");
		}

		if (ConfigUtil.isSet(config, path + ".action-bar")) {
			ActionBarManager.sendActionBarMessage(player, path + ".action-bar");
		}
	}

	private static void handleToEveryone(Player player, PlayerJoinEvent e) {
		String path = "global-settings.first-join.to-everyone-online";
		Config config = SimpleJoin.config;

		if (ConfigUtil.isSet(config, path + ".sound-effect")) {
			SoundManager.playSound(Bukkit.getOnlinePlayers(), player, path + ".sound-effect");
		}

		if (ConfigUtil.isSet(config, path + ".effects")) {
			EffectsManager.addEffects(Bukkit.getOnlinePlayers(), player, path + ".effects");
		}

		if (ConfigUtil.isSet(config, path + ".commands")) {
			CommandManager.runCommands(Bukkit.getOnlinePlayers(), player, path + ".commands");
		}

		if (ConfigUtil.isSet(config, path + ".title-message")) {
			TitleMessageManager.sendTitleMessage(Bukkit.getOnlinePlayers(), player,  path + ".title-message");
		}

		if (ConfigUtil.isSet(config, path + ".boss-bar")) {
			BossBarManager.sendBossBarMessage(Bukkit.getOnlinePlayers(), player, path + ".boss-bar");
		}

		if (ConfigUtil.isSet(config, path + ".message")) {
			StandardMessageManager.sendNormalMessage(Bukkit.getOnlinePlayers(), player, path + ".message");
		}

		if (ConfigUtil.isSet(config, path + ".action-bar")) {
			ActionBarManager.sendActionBarMessage(Bukkit.getOnlinePlayers(), player, path + ".action-bar");
		}
	}

	private static boolean firstJoinEnabled() {
		return ConfigUtil.getBoolean(SimpleJoin.config, "global-settings.first-join.enabled");
	}

}
