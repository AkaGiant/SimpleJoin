package com.github.akagiant.simplejoin.listeners;

import com.github.akagiant.simplejoin.joinmanagers.onjoin.FirstJoinManager;
import com.github.akagiant.simplejoin.joinmanagers.onjoin.StandardJoinManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnJoin implements Listener {

	@EventHandler (priority = EventPriority.HIGHEST)
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();

		// Only activates if the player has joined for
		if (!player.hasPlayedBefore()) {
			FirstJoinManager.firstJoin(player, e);
			return;
		}

		// Handle none first time join.
		StandardJoinManager.execute(player, e);
	}

	@EventHandler (priority = EventPriority.HIGHEST)
	public void onQuit(PlayerQuitEvent e) {

	}

}
