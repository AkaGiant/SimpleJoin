package com.github.akagiant.simplejoin.listeners;

import com.github.akagiant.simplejoin.JoinManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Developed by AkaGiant
 * https://github.com/AkaGiant
 */

public class OnJoin implements Listener {

	@EventHandler (priority = EventPriority.HIGHEST)
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
//		if (!player.hasPlayedBefore()) {
			JoinManager.firstJoin(player, e);
			return;
//		}
	}

	@EventHandler (priority = EventPriority.HIGHEST)
	public void onQuit(PlayerQuitEvent e) {

	}

}
