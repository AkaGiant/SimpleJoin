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

import com.github.akagiant.simplejoin.utility.internal.ConfigManager;
import com.github.akagiant.simplejoin.utility.internal.ConfigUtil;
import org.bukkit.entity.Player;

import java.util.List;

public class JoinManager {

	private JoinManager() {
		//no instance
	}
	
	public static void tryIncreaseTotalJoined(Player player) {
		if (!ConfigUtil.isSet(ConfigManager.data, "total-joined")) {
			ConfigManager.data.getConfig().set("total-joined.total", 0);
			ConfigManager.data.saveConfig();
		}
		List<String> joinedValues = ConfigManager.data.getConfig().getStringList("total-joined.uuids");
		if (!joinedValues.contains(player.getUniqueId().toString())) {
			joinedValues.add(player.getUniqueId().toString());
			ConfigManager.data.getConfig().set("total-joined.uuids", joinedValues);
			ConfigManager.data.getConfig().set("total-joined.total", joinedValues.size());
			ConfigManager.data.saveConfig();
		}
	}
	
	public static int getTotalJoined() {
		return ConfigUtil.getInt(ConfigManager.data, "total-joined.total").orElse(getTotalJoinedManual());
	}
	
	public static int getTotalJoinedManual() {
		return ConfigManager.data.getConfig().getStringList("total-joined.uuids").size();
	}
	
}
