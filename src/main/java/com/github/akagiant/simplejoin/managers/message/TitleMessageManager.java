package com.github.akagiant.simplejoin.managers.message;

import com.github.akagiant.simplejoin.SimpleJoin;
import com.github.akagiant.simplejoin.util.ConfigUtil;
import org.bukkit.entity.Player;

import java.util.Collection;

public class TitleMessageManager {

	private TitleMessageManager() {
		//no instance
	}
	
	// Handle Title Message
	public static void sendTitleMessage(Collection<? extends Player> playerCollection, String path) {
		String title = ConfigUtil.getString(SimpleJoin.config, path + ".title");
		String subTitle = ConfigUtil.getString(SimpleJoin.config, path + ".sub-title");

		int fadeIn = ConfigUtil.getInt(SimpleJoin.config, path + ".settings.fade-in");
		int fadeOut = ConfigUtil.getInt(SimpleJoin.config, path + ".settings.fade-out");
		int stay = ConfigUtil.getInt(SimpleJoin.config, path + ".settings.stay");

		for (Player player : playerCollection) {
			player.sendTitle(title, subTitle, fadeIn * 20, stay * 20, fadeOut * 20);
		}

	}

	public static void sendTitleMessage(Player player, String path) {

		String title = ConfigUtil.getString(SimpleJoin.config, path + ".title");
		String subTitle = ConfigUtil.getString(SimpleJoin.config, path + ".sub-title");

		title = title.replace("%player_name%", player.getName());
		subTitle = subTitle.replace("%player_name%", player.getName());


		int fadeIn = ConfigUtil.getInt(SimpleJoin.config, path + ".settings.fade-in");
		int fadeOut = ConfigUtil.getInt(SimpleJoin.config, path + ".settings.fade-out");
		int stay = ConfigUtil.getInt(SimpleJoin.config, path + ".settings.stay");

		player.sendTitle(title, subTitle, fadeIn * 20, stay * 20, fadeOut * 20);
	}

}
