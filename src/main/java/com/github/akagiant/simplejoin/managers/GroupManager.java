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

package com.github.akagiant.simplejoin.managers;

import com.github.akagiant.simplejoin.utility.internal.ConfigManager;
import com.github.akagiant.simplejoin.utility.internal.ConfigUtil;
import com.github.akagiant.simplejoin.utility.internal.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class GroupManager {
	private GroupManager() {
		//no instance
	}
	
	public static Collection<? extends Player> getPlayersVisibleBy(Player target) {
		Optional<String> highestGroup = getHighestGroup(target);
		if (!highestGroup.isPresent()) {
			return Collections.emptyList();
		}
		if (!isValid(highestGroup.get())) {
			return Collections.emptyList();
		}
		
		List<String> visibleGroups = getGroupsVisibleBy(highestGroup.get());
		List<Player> playerList = new ArrayList<>();
		for (String visibleGroup : visibleGroups) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.getUniqueId().equals(target.getUniqueId())) continue;
				if (player.hasPermission(Util.PERMISSION_KEY + ".join." + visibleGroup) && !playerList.contains(player)) {
					playerList.add(player);
				}
			}
		}
		return playerList;
	}
	
	
	public static List<String> getGroupsVisibleBy(String highestGroup) {
		if (!isValid(highestGroup) || !hasVisibleGroups(highestGroup)) return Collections.emptyList();
		
		List<String> validGroups = new ArrayList<>();
		
		
		for (String group : ConfigUtil.getConfigurationSection(ConfigManager.config, "groups." + highestGroup + ".visible-by").getKeys(false)) {
			if (ConfigUtil.getBoolean(ConfigManager.config, "groups." + group + ".visible-by." + group) && !validGroups.contains(group)) {
				validGroups.add(group);
			}
		}
		return validGroups;
	}

	public static boolean hasVisibleGroups(String groupName) {
		return ConfigUtil.isSet(ConfigManager.config, "groups." + groupName + ".visible-by");
	}
	
	
	public static boolean isValid(String groupName) {
		for (String group : ConfigUtil.getConfigurationSection(ConfigManager.config, "groups").getKeys(false)) {
			if (groupName.equals(group)) {
				return true;
			}
		}
		return false;
	}
	
	public static Optional<String> getHighestGroup(Player target) {
		for (String group : ConfigManager.config.getConfig().getConfigurationSection("groups").getKeys(false)) {
			if (target.hasPermission(Util.PERMISSION_KEY + ".join." + group)) {
				return Optional.of(group);
			}
		}
		return Optional.empty();
	}
}
