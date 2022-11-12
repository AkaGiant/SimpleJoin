package com.github.akagiant.simplejoin.systemmanagers;

import com.github.akagiant.simplejoin.SimpleJoin;
import com.github.akagiant.simplejoin.util.ConfigUtil;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EffectsManager {

	private EffectsManager() {
		//no instance
	}
	
	public static void addEffects(Collection<? extends Player> playerCollection, Player target, String path) {
		for (Player player : playerCollection) {
			if (player.getUniqueId().equals(target.getUniqueId())) continue;
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
			int level = ConfigUtil.getInt(SimpleJoin.config, path + "." + key + ".level");

			PotionEffectType effect = PotionEffectType.getByName(value);
			if (effect == null) {
				ConfigUtil.logError(SimpleJoin.config, path + "." + key + ".effect", value + " is not a valid type of effect.", "https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/potion/PotionEffectType.html", "Effect");
				continue;
			}

			if (duration == 0) {
				ConfigUtil.logError(SimpleJoin.config, path + "." + key + ".duration", duration + " is not a valid duration.", null, ConfigUtil.wholeNumber);
				continue;
			}

			if (level == 0) {
				ConfigUtil.logError(SimpleJoin.config, path + "." + key + ".level", level + " is not a valid number.", ConfigUtil.wholeNumber);
				continue;
			}
			potionEffects.add(new PotionEffect(effect, duration * 20, level));
		}
		return potionEffects;
	}
}
