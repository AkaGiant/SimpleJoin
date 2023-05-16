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

package com.github.akagiant.simplejoin.listeners;

import com.github.akagiant.simplejoin.system.FirstJoin;
import com.github.akagiant.simplejoin.system.JoinManager;
import com.github.akagiant.simplejoin.system.StandardJoin;
import com.github.akagiant.simplejoin.system.StandardLeave;
import com.github.akagiant.simplejoin.utility.internal.ConfigManager;
import com.github.akagiant.simplejoin.utility.internal.ConfigUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class JoinLeaveListener implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void onJoin(PlayerJoinEvent e) {
		List<String> joinedUUIDs = ConfigUtil.getStringList(ConfigManager.data, "total-joined.uuids");
		if (!joinedUUIDs.contains(e.getPlayer().getUniqueId().toString())) {
			JoinManager.tryIncreaseTotalJoined(e.getPlayer());
			new FirstJoin(e.getPlayer(), e);
			return;
		}

		// Handle none first time join.
		StandardJoin.execute(e.getPlayer(), e);
	}
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		StandardLeave.execute(e.getPlayer(), e);
	}


}
