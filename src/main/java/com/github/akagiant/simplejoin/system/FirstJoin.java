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
import org.bukkit.event.player.PlayerJoinEvent;

public class FirstJoin {

	private FirstJoin() {
		//no instance
	}

	public FirstJoin(Player player, PlayerJoinEvent e) {
		e.setJoinMessage("");

		if (!firstJoinEnabled()) {
			return;
		}

		handleToPlayer(player);
		handleToEveryone(player);
	}


	public void handleToPlayer(Player player) {
		String path = "first-join.to-player";
		Config config = ConfigManager.config;
		
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
	
	private void handleToEveryone(Player player) {
		String path = "first-join.to-everyone-online";
		Config config = ConfigManager.config;
		
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
	
	private boolean firstJoinEnabled() {
		return ConfigUtil.getBoolean(ConfigManager.config, "first-join.enabled");
	}
	
}
