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

package com.github.akagiant.simplejoin.managers.system.message;

import com.github.akagiant.simplejoin.SimpleJoin;
import com.github.akagiant.simplejoin.utility.internal.ColorManager;
import com.github.akagiant.simplejoin.utility.internal.ConfigManager;
import com.github.akagiant.simplejoin.utility.internal.ConfigUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Optional;

public class TitleMessageManager {

	private TitleMessageManager() {
		//no instance
	}

	
	// Handle Title Message
	public static void sendTitleMessage(Collection<? extends Player> playerCollection, Player target, String path) {
		String title = ConfigUtil.getString(ConfigManager.config, path + ".title");
		String subTitle = ConfigUtil.getString(ConfigManager.config, path + ".sub-title");

		Optional<Integer> optionalFadeIn = ConfigUtil.getInt(ConfigManager.config, path + ".settings.fade-in");

		if (!optionalFadeIn.isPresent()) return;
		int fadeIn = optionalFadeIn.get();
		
		Optional<Integer> optionalFadeOut = ConfigUtil.getInt(ConfigManager.config, path + ".settings.fade-out");
		if (!optionalFadeOut.isPresent()) return;
		int fadeOut = optionalFadeOut.get();
		
		Optional<Integer> optionalStay = ConfigUtil.getInt(ConfigManager.config, path + ".settings.stay");
		if (!optionalStay.isPresent()) return;
		int stay = optionalStay.get();

		for (Player player : playerCollection) {
			if (player.getUniqueId().equals(target.getUniqueId())) continue;
			if (SimpleJoin.isHasPlaceholderAPI()) {
				PlaceholderAPI.setPlaceholders(player, title);
				PlaceholderAPI.setPlaceholders(player, subTitle);
			}
			player.sendTitle(title, subTitle, fadeIn * 20, stay * 20, fadeOut * 20);
		}

	}

	public static void sendTitleMessage(Player player, String path) {
		
		String title = ConfigUtil.getString(ConfigManager.config, path + ".title");
		String subTitle = ConfigUtil.getString(ConfigManager.config, path + ".sub-title");

		if (SimpleJoin.isHasPlaceholderAPI()) {
			PlaceholderAPI.setPlaceholders(player, title);
			PlaceholderAPI.setPlaceholders(player, subTitle);
		}

		Optional<Integer> optionalFadeIn = ConfigUtil.getInt(ConfigManager.config, path + ".settings.fade-in");
		if (!optionalFadeIn.isPresent()) return;
		int fadeIn = optionalFadeIn.get();
		
		Optional<Integer> optionalFadeOut = ConfigUtil.getInt(ConfigManager.config, path + ".settings.fade-out");
		if (!optionalFadeOut.isPresent()) return;
		int fadeOut = optionalFadeOut.get();
		
		Optional<Integer> optionalStay = ConfigUtil.getInt(ConfigManager.config, path + ".settings.stay");
		if (!optionalStay.isPresent()) return;
		int stay = optionalStay.get();

		player.sendTitle(ColorManager.formatColours(title), ColorManager.formatColours(subTitle), fadeIn * 20, stay * 20, fadeOut * 20);
	}

}
