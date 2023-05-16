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
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Optional;

public class SoundManager {

	private SoundManager() {
		//no instance
	}

	public static void playSound(Player player, String path) {
		Optional<Sound> optionalSound = ConfigUtil.getSound(ConfigManager.config, path + ".sound");
		if (!optionalSound.isPresent()) return;
		Sound sound = optionalSound.get();
		
		Optional<?> optionalVolume = ConfigUtil.getNumber(ConfigManager.config, path + ".settings.volume");
		if (!optionalVolume.isPresent()) return;
		double volume = (int) optionalVolume.get();
		
		Optional<?> optionalPitch = ConfigUtil.getNumber(ConfigManager.config, path + ".settings.pitch");
		if (!optionalPitch.isPresent()) return;
		int pitch = (int) optionalPitch.get();

		player.playSound(player.getLocation(), sound, (float) volume, (float) pitch);
	}

	public static void playSound(Collection<? extends Player> playerCollection, Player target, String path) {
		Optional<Sound> optionalSound = ConfigUtil.getSound(ConfigManager.config, path + ".sound");
		if (!optionalSound.isPresent()) return;
		Sound sound = optionalSound.get();
		
		Optional<?> optionalVolume = ConfigUtil.getNumber(ConfigManager.config, path + ".settings.volume");
		if (!optionalVolume.isPresent()) return;
		int volume = (int) optionalVolume.get();
		
		Optional<?> optionalPitch = ConfigUtil.getNumber(ConfigManager.config, path + ".settings.pitch");
		if (!optionalPitch.isPresent()) return;
		int pitch = (int) optionalPitch.get();

		for (Player player : playerCollection) {
			if (player.getUniqueId().equals(target.getUniqueId())) continue;
			player.playSound(player.getLocation(), sound, (float) volume, (float) pitch);
		}
	}

}
