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

package com.github.akagiant.simplejoin.utility.internal;

import com.github.akagiant.simplejoin.SimpleJoin;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.RegisteredCommand;
import org.bukkit.Material;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class Util {

	private Util() {
		//no instance
	}
	
	public static int getPermissionsCount() {
		return SimpleJoin.getPlugin().getDescription().getPermissions().size();
	}

	public static int getCommandAliasesCount() {
		int amount = 0;
		for (RegisteredCommand cmd : CommandAPI.getRegisteredCommands()) {
			if (cmd.aliases() != null) { amount += cmd.aliases().length; }
		}
		return amount;
	}

	public static final String PERMISSION_KEY = "simplesolutions";

	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public static double random(double min, double max) {
		Random r = new Random();
		return min + (max - min) * r.nextDouble();
	}

	public static String capitalize(String str)
	{
		if (str == null || str.length() == 0) return str;
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}

	public static String capitaliseMaterial(Material material) {
		return capitaliseMultiWord(material.toString());
	}

	public static String capitaliseMultiWord(String string) {
		String[] split = string.split("_");

		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < split.length; i++) {
			if (i + 1 == split.length) {
				// No space
				stringBuilder.append(capitalize(split[i]));
			} else {
				// With Space
				stringBuilder.append(capitalize(split[i] + " "));
			}
		}

		return stringBuilder.toString();
	}

}
