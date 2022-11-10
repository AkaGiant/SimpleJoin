package com.github.akagiant.simplejoin.listeners;

import com.github.akagiant.simplejoin.JoinManager;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
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
		if (!player.hasPlayedBefore()) {
			JoinManager.firstJoin(player, e);
			return;
		}
	}

	@EventHandler (priority = EventPriority.HIGHEST)
	public void onQuit(PlayerQuitEvent e) {

	}

}
