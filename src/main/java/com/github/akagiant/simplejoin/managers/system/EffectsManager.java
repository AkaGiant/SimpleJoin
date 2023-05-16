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

package com.github.akagiant.simplejoin.managers.system;

import com.github.akagiant.simplejoin.utility.internal.ConfigManager;
import com.github.akagiant.simplejoin.utility.internal.ConfigUtil;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

		for (String key : ConfigManager.config.getConfig().getConfigurationSection(path).getKeys(false)) {

			String value = ConfigUtil.getString(ConfigManager.config, path + "." + key + ".effect");
			
			Optional<Integer> optionalDuration = ConfigUtil.getInt(ConfigManager.config, path + "." + key + ".duration");
			if (!optionalDuration.isPresent()) continue;
			int duration = optionalDuration.get();
			
			Optional<Integer> optionalLevel = ConfigUtil.getInt(ConfigManager.config, path + "." + key + ".level");
			if (!optionalLevel.isPresent()) continue;
			int level = optionalLevel.get();

			PotionEffectType effect = PotionEffectType.getByName(value);
			if (effect == null) {
				ConfigUtil.logError(
					ConfigManager.config, path + "." + key + ".effect",
					value + " is not a valid type of effect.",
					"EffectsManager.java @ getEffects()",
					"https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/potion/PotionEffectType.html",
					"Effect");
				continue;
			}

			if (duration == 0) {
				ConfigUtil.logError(
					ConfigManager.config, path + "." + key + ".duration",
					duration + " is not a valid duration.",
					"EffectsManager.java @ getEffects()",
					null,
					ConfigUtil.wholeNumber);
				continue;
			}

			if (level == 0) {
				ConfigUtil.logError(
					ConfigManager.config,
					path + "." + key + ".level",
					level + " is not a valid number.",
					"EffectsManager.java @ getEffects()",
					ConfigUtil.wholeNumber);
				continue;
			}
			potionEffects.add(new PotionEffect(effect, duration * 20, level));
		}
		return potionEffects;
	}
}
