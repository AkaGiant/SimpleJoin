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

package com.github.akagiant.simplejoin.system;

import com.github.akagiant.simplejoin.managers.GroupManager;
import com.github.akagiant.simplejoin.managers.system.CommandManager;
import com.github.akagiant.simplejoin.managers.system.EffectsManager;
import com.github.akagiant.simplejoin.managers.system.SoundManager;
import com.github.akagiant.simplejoin.managers.system.message.ActionBarManager;
import com.github.akagiant.simplejoin.managers.system.message.BossBarManager;
import com.github.akagiant.simplejoin.managers.system.message.StandardMessageManager;
import com.github.akagiant.simplejoin.managers.system.message.TitleMessageManager;
import com.github.akagiant.simplejoin.utility.internal.Config;
import com.github.akagiant.simplejoin.utility.internal.ConfigManager;
import com.github.akagiant.simplejoin.utility.internal.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Optional;

public class StandardLeave {
	
	private StandardLeave() {
		//no instance
	}
	
	public static void execute(Player player, PlayerQuitEvent e) {
		Optional<String> optionalHighestGroup = GroupManager.getHighestGroup(player);
		if (optionalHighestGroup.isEmpty()) return;
		
		String highestGroup = optionalHighestGroup.get();

		e.setQuitMessage("");
		
		handleToEveryone(player, highestGroup);
	}
	private static void handleToEveryone(Player player, String primaryGroup) {
		// Execute for the target.
		String primaryPath = "join-system.groups." + primaryGroup + ".on-quit.global";

		Config config = ConfigManager.config;
		
		if (ConfigUtil.isSet(config, primaryPath + ".sound-effect")) {
			SoundManager.playSound(Bukkit.getOnlinePlayers(), player, primaryPath + ".sound-effect");
		}
		
		if (ConfigUtil.isSet(config, primaryPath + ".effects")) {
			EffectsManager.addEffects(Bukkit.getOnlinePlayers(), player, primaryPath + ".effects");
		}
		
		if (ConfigUtil.isSet(config, primaryPath + ".commands")) {
			CommandManager.runCommands(Bukkit.getOnlinePlayers(), player, primaryPath + ".commands");
		}
		
		if (ConfigUtil.isSet(config, primaryPath + ".title-message")) {
			TitleMessageManager.sendTitleMessage(Bukkit.getOnlinePlayers(), player, primaryPath + ".title-message");
		}
		
		if (ConfigUtil.isSet(config, primaryPath + ".boss-bar")) {
			BossBarManager.sendBossBarMessage(Bukkit.getOnlinePlayers(), player, primaryPath + ".boss-bar");
		}

		if (ConfigUtil.isSet(config, primaryPath + ".message")) {
			StandardMessageManager.sendNormalMessage(Bukkit.getOnlinePlayers(), player, primaryPath + ".message");
		}
		
		if (ConfigUtil.isSet(config, primaryPath + ".action-bar")) {
			ActionBarManager.sendActionBarMessage(player, primaryPath + ".action-bar");
		}
	}
	
}
