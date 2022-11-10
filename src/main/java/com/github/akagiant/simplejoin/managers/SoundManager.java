package com.github.akagiant.simplejoin.managers;

import com.github.akagiant.simplejoin.SimpleJoin;
import com.github.akagiant.simplejoin.util.ConfigUtil;
import com.github.akagiant.simplejoin.util.Logger;
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
			Logger.severe("&f" + path + ".sound is not a valid type of sound.");
			return;
		}
		double volume = ConfigUtil.getDouble(SimpleJoin.config, path + ".settings.volume");
		double pitch = ConfigUtil.getDouble(SimpleJoin.config, path + ".settings.pitch");

		player.playSound(player.getLocation(), snd, (float) volume, (float) pitch);
	}

	public static void playSound(Collection<? extends Player> playerCollection, String path) {
		String sound = ConfigUtil.getString(SimpleJoin.config, path + ".sound");
		Sound snd;

		try {
			snd = Sound.valueOf(sound);
		} catch (IllegalArgumentException exception) {
			Logger.severe("&f" + path + ".sound is not a valid type of sound.");
			return;
		}
		double volume = ConfigUtil.getDouble(SimpleJoin.config, path + ".settings.volume");
		double pitch = ConfigUtil.getDouble(SimpleJoin.config, path + ".settings.pitch");

		for (Player player : playerCollection) {
			player.playSound(player.getLocation(), snd, (float) volume, (float) pitch);
		}


	}

}
