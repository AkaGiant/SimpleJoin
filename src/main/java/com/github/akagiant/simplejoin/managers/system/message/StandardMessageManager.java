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
import com.github.akagiant.simplejoin.utility.internal.ConfigManager;
import com.github.akagiant.simplejoin.utility.internal.ConfigUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;


public class StandardMessageManager {

	private StandardMessageManager() {
		//no instance
	}
	
	// Handle Normal Messages
	public static void sendNormalMessage(Collection<? extends Player> playerCollection, Player target, String path) {
		List<String> messages = ConfigUtil.getStringList(ConfigManager.config, path);
		for (Player player : playerCollection) {
			if (player.getUniqueId().equals(target.getUniqueId())) continue;
			for (String str : messages) {
				str = str.replace("[total joined]", String.valueOf(ConfigUtil.getStringListSilent(ConfigManager.data, "total-joined.uuids").size()));
				if (SimpleJoin.isHasPlaceholderAPI()) {
					PlaceholderAPI.setPlaceholders(player, str);
				}
				player.sendMessage(str);
			}
		}
	}

	public static void sendNormalMessage(Player player, String path) {
		List<String> messages = ConfigUtil.getStringList(ConfigManager.config, path);
		for (String str : messages) {
			str = str.replace("[total joined]", String.valueOf(ConfigUtil.getStringListSilent(ConfigManager.data, "total-joined.uuids").size()));
			if (SimpleJoin.isHasPlaceholderAPI()) {
				PlaceholderAPI.setPlaceholders(player, str);
			}
			player.sendMessage(str);
		}
	}

}
