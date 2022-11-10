package com.github.akagiant.simplejoin.managers;

import com.github.akagiant.simplejoin.SimpleJoin;
import com.github.akagiant.simplejoin.util.ConfigUtil;
import com.github.akagiant.simplejoin.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EffectsManager {

	public static void addEffects(Collection<? extends Player> playerCollection, String path) {
		for (Player player : playerCollection) {
			for (PotionEffect potionEffect : getEffects(path)) {
				player.addPotionEffect(potionEffect);
			}
		}
	}

	public static void addEffects(Player player, String path) {
		for (PotionEffect potionEffect : getEffects(path)) {
			player.addPotionEffect(potionEffect);
		}
	}

	private static List<PotionEffect> getEffects(String path) {
		List<PotionEffect> potionEffects = new ArrayList<>();

		for (String key : SimpleJoin.config.getConfig().getConfigurationSection(path).getKeys(false)) {

			String value = ConfigUtil.getString(SimpleJoin.config, path + "." + key + ".effect");
			int duration = ConfigUtil.getInt(SimpleJoin.config, path + "." + key + ".duration");
			int level = ConfigUtil.getInt(SimpleJoin.config, path + "." + key + "level");

			PotionEffectType effect = PotionEffectType.getByName(value);
			if (effect == null) {
				Logger.severe("&f" + path + "." + key + ".effect is not a valid type of effect.");
				continue;
			}

			if (duration == 0) {
				Logger.severe("&f" + path + "." + key + ".duration is not a valid duration.");
				continue;
			}

			if (level == 0) {
				Logger.severe("&f" + path + "." + key + ".duration is not a valid level.");
				continue;
			}
			potionEffects.add(new PotionEffect(effect, duration, level));
		}
		return potionEffects;
	}
}
