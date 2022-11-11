package com.github.akagiant.simplejoin;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class PlaceholderManager extends PlaceholderExpansion {
	@Override
	public @NotNull String getIdentifier() {
		return "PlanetaryCore";
	}

	@Override
	public @NotNull String getAuthor() {
		return "AkaGiant";
	}

	@Override
	public @NotNull String getVersion() {
		return "1.0.0";
	}

	@Override
	public String onRequest(OfflinePlayer offlinePlayer, @NotNull String params) {

		if (params.equals("player_name")) return String.valueOf(offlinePlayer.getName());
		if (params.equals("total_joined")) return String.valueOf(Bukkit.getServer().getOfflinePlayers().length);

		return null;
	}

}
