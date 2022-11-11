package com.github.akagiant.simplejoin.managers;

import com.github.akagiant.simplejoin.SimpleJoin;
import com.github.akagiant.simplejoin.util.ConfigUtil;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Collection;

public class SoundManager {

	private SoundManager() {
		//no instance
	}

	public static void playSound(Player player, String path) {
		String sound = ConfigUtil.getString(SimpleJoin.config, path + ".sound");
		Sound snd;

		try {
			snd = Sound.valueOf(sound);
		} catch (IllegalArgumentException exception) {
			ConfigUtil.logError(SimpleJoin.config, path, sound + " is not a valid sound", "https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html", "Valid Sound");
			return;
		}
		double volume = ConfigUtil.getInt(SimpleJoin.config, path + ".settings.volume");
		double pitch = ConfigUtil.getInt(SimpleJoin.config, path + ".settings.pitch");

		player.playSound(player.getLocation(), snd, (float) volume, (float) pitch);
	}

	public static void playSound(Collection<? extends Player> playerCollection, Player target, String path) {
		String sound = ConfigUtil.getString(SimpleJoin.config, path + ".sound");
		Sound snd;

		try {
			snd = Sound.valueOf(sound);
		} catch (IllegalArgumentException exception) {
			ConfigUtil.logError(SimpleJoin.config, path, sound + " is not a valid sound", "https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html", "Valid Sound");
			return;
		}
		int volume = ConfigUtil.getInt(SimpleJoin.config, path + ".settings.volume");
		int pitch = ConfigUtil.getInt(SimpleJoin.config, path + ".settings.pitch");

		for (Player player : playerCollection) {
			if (player.getUniqueId().equals(target.getUniqueId())) continue;
			player.playSound(player.getLocation(), snd, (float) volume, (float) pitch);
		}


	}

}
