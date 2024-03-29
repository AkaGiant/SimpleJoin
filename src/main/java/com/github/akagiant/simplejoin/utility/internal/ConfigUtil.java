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

import com.github.akagiant.simplejoin.utility.MessageManager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ConfigUtil {

	private ConfigUtil() {
		//no instance
	}
	
	public static String string = "String ('your text here')";
	public static String[] strings = new String[]{"'line 1'", "'line 2'", "'line 3'"};
	public static String wholeNumber = "Whole Number E.G. 1, 2, 3";
	public static String decimalNumber = "Decimal or Whole Number E.G. 1, 1.0, 4.23";
	public static String valueMissing = "The value expected at the current path is missing.";
	public static String valueNotValid = "The value inputted at the current path is not valid.";
	public static String expectedStringList = "Hmm... I was expecting to find a list of string but found nothing...";
	public static String expectedString = "Hmm... I was expecting to find a string but found nothing...";
	public static String expectedSound = "Hmm... I was expecting to find a valid SOUND here...";

	/**
	 * finds a String value from a provided configuration file
	 *
	 * @param config {@link Config} the config to get the data from
	 * @param path {@link String} the path to the data within the configuration file.
	 *
	 * @return a {@link String} object, if one matching the path exists, or null if not
	 */
	public static Optional<String> getOptionalString(Config config, String path) {
		if (!isSetAndStringIsValid(config, path)) {
			logError(config, path, expectedString, null, string);
			return Optional.empty();
		}
		return Optional.of(ColorManager.formatColours(config.getConfig().getString(path)));
	}

	public static Optional<String> getStringOptional(Config config, String path) {
		if (!isSetAndStringIsValid(config, path)) {
			return Optional.empty();
		}
		return Optional.of(ColorManager.formatColours(config.getConfig().getString(path)));
	}

	public static String getString(Config config, String path) throws NullPointerException {
		if (!isSetAndStringIsValid(config, path)) {
			logError(config, path, expectedString, null, string);
			return MessageManager.getConfigMessageFailure();
		}
		return ColorManager.formatColours(config.getConfig().getString(path));
	}

	/**
	 * finds a List<String> value from a provided configuration file
	 *
	 * @param config {@link Config} the config to get the data from
	 * @param path {@link String} the path to the data within the configuration file.
	 *
	 * @return a {@link List <String>} object, if one matching the path exists, or null if not
	 * @throws NullPointerException if the path is null or value is not present.
	 */
	public static List<String> getStringList(Config config, String path) throws NullPointerException {
		if (!isSet(config, path)) {
			logError(config, path, expectedStringList, null, strings);
			return new ArrayList<>();
		}
		List<String> stringList = config.getConfig().getStringList(path);
		if (stringList.isEmpty()) {
			logError(config, path, expectedStringList,null, strings);
			return new ArrayList<>();
		}
		return ColorManager.formatColours(stringList);
	}

	public static List<String> getStringListSilent(Config config, String path) throws NullPointerException {
		if (!isSet(config, path)) {
			return new ArrayList<>();
		}
		List<String> stringList = config.getConfig().getStringList(path);
		if (stringList.isEmpty()) {
			logError(config, path, expectedStringList,null, strings);
			return new ArrayList<>();
		}
		return ColorManager.formatColours(stringList);
	}

	/**
	 * finds a boolean value from a provided configuration file
	 *
	 * @param config {@link Config} the config to get the data from
	 * @param path {@link String} the path to the data within the configuration file.
	 *
	 * @return a {@link Boolean} object, if one matching the path exists, or null if not
	 */
	public static boolean getBoolean(Config config, String path) {
		if (!isSet(config, path)) {
			config.getConfig().set(path, false);
			config.saveConfig();
			return false;
		}
		return config.getConfig().getBoolean(path);
	}

	/**
	 * Checks weather or not a path is valid and something is set.
	 *
	 * @param config {@link Config} the config to get the data from
	 * @param path {@link String} the path to the data within the configuration file.
	 *
	 * @return a {@link Boolean} object, if one matching the path exists, or null if not
	 */
	public static boolean isSet(Config config, String path) {
		return config.getConfig().isSet(path);
	}

	/**
	 * Checks weather or not a path is valid and a boolean is set to true.
	 *
	 * @param config {@link Config} the config to get the data from
	 * @param path {@link String} the path to the data within the configuration file.
	 *
	 * @return a {@link Boolean} object, if one matching the path exists, or null if not
	 */
	public static boolean isSetAndIsBoolean(Config config, String path) {
		return isSet(config, path) && getBoolean(config, path);
	}

	/**
	 * Checks weather or not a path is valid and something is set.
	 *
	 * @param config {@link Config} the config to get the data from
	 * @param path {@link String} the path to the data within the configuration file.
	 *
	 * @return a {@link Boolean} object, if one matching the path exists, or null if not
	 */
	public static boolean isSetAndStringIsValid(Config config, String path) {
		return config.getConfig().isSet(path) && config.getConfig().getString(path) != null;
	}

	public static Optional<Material> getMaterial(Config config, String path) throws NullPointerException {
		if (!isSet(config, path)) return Optional.empty();

		String stringOptional = getString(config, path);

		if (Material.matchMaterial(stringOptional) == null) return Optional.empty();
		return Optional.of(Material.valueOf(stringOptional));
	}


	/**
	 * returns a double value from a given path.
	 *
	 * @param config {@link Config} the config to get the data from
	 * @param path {@link String} the path to the data within the configuration file.
	 *
	 * @return a {@link Double} object, if one matching the path exists, or null if not
	 * @throws NullPointerException if the path is null or value is not present.
	 */
	public static Optional<Double> getDouble(Config config, String path) throws NullPointerException  {
		if (!isSet(config, path)) {
			logError(config, path, valueMissing, null, decimalNumber);
			return Optional.empty();
		}

		return Optional.of(config.getConfig().getDouble(path));
	}

	public static Optional<?> getNumber(Config config, String path) {
		if (!isSet(config, path)) {
			logError(config, path, valueMissing, null, decimalNumber);
			return Optional.empty();
		}

		if (config.getConfig().isDouble(path)) {
			return Optional.of(getDouble(config, path).get());
		}
		else if (config.getConfig().isInt(path)) {
			return Optional.of(getInt(config, path).get());
		}
		return Optional.empty();
	}

	public static Optional<?> getNumberNoLog(Config config, String path) {
		if (!isSet(config, path)) {
			return Optional.empty();
		}
		else if (config.getConfig().isDouble(path)) {
			return Optional.of(getDouble(config, path).get());
		}
		else if (config.getConfig().isInt(path)) {
			return Optional.of(getInt(config, path).get());
		}
		else {
			return Optional.empty();
		}
	}

	/**
	 * returns a integer value from a given path.
	 *
	 * @param config {@link Config} the config to get the data from
	 * @param path {@link String} the path to the data within the configuration file.
	 *
	 * @return a {@link Integer} object, if one matching the path exists, or null if not
	 * @throws NullPointerException if the path is null or value is not present.
	 */
	public static Optional<Integer> getInt(Config config, String path) throws NullPointerException {
		if (!isSet(config, path)) {
			logError(config, path, valueMissing, null, wholeNumber);
			return Optional.empty();
		}

		if (!config.getConfig().isInt(path)) {
			logError(config, path, valueNotValid, null, wholeNumber);
			return Optional.empty();
		}

		return Optional.of(config.getConfig().getInt(path));
	}

	public static Optional<Integer> getIntSilent(Config config, String path) throws NullPointerException {
		if (!isSet(config, path)) {
			return Optional.empty();
		}

		if (!config.getConfig().isInt(path)) {
			return Optional.empty();
		}

		return Optional.of(config.getConfig().getInt(path));
	}

	public static long getLong(Config config, String path) throws NullPointerException {
		if (!isSet(config, path)) {
			logError(config, path, valueMissing, null, wholeNumber);
			return -1;
		}

		if (!config.getConfig().isLong(path)) {
			logError(config, path, valueNotValid, null, wholeNumber);
			return -1;
		}

		return config.getConfig().getLong(path);
	}

	public static float getFloat(Config config, String path) {
		double db = config.getConfig().getDouble(path);
		return (float) db;
	}

	/**
	 * returns a configuration in a configuration file at a given path.
	 *
	 * @param config {@link Config} the config to get the data from
	 * @param path {@link String} the path to the data within the configuration file.
	 *
	 * @return a {@link ConfigurationSection} object, if one matching the path exists, or null if not
	 * @throws NullPointerException if the path is null or value is not present.
	 */
	public static ConfigurationSection getConfigurationSection(Config config, String path) throws NullPointerException  {
		if (config.getConfig().getConfigurationSection(path) == null) {
			logError(config, path, valueNotValid, null, "A Valid Key");
			return null;
		}
		return config.getConfig().getConfigurationSection(path);
	}

	public static Optional<Sound> getSound(Config config, String path) {
		String stringOptional = getString(config, path);
		Sound sound;

		try {
			sound = Sound.valueOf(stringOptional);
		} catch (IllegalArgumentException e) {
			logError(config, path, expectedSound, "https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html");
			return Optional.empty();
		}
		return Optional.of(sound);
	}

	public static Optional<Sound> getSoundOptional(Config config, String path) {
		String stringOptional = getStringOptional(config, path).orElse(null);
		if (stringOptional == null) return Optional.empty();
		Sound sound;

		try {
			sound = Sound.valueOf(stringOptional);
		} catch (IllegalArgumentException e) {
			return Optional.empty();
		}
		return Optional.of(sound);
	}

	public static void logError(Config config, String path, String errorMessage, @Nullable String url, String... expected) {
		Logger.severe("&m------------------------------------");
		Logger.severe("&cConfiguration Error");
		Logger.severe("&m------------------------------------");
		Logger.severe("&fError: &c" + errorMessage);
		Logger.severe("&fFile: &c" + config.getFile().getName());
		Logger.severe("&fPath: ");
		printPath(path, expected);
		if (url != null) { Logger.severe("&b" + url + " &ffor more information"); }

		Logger.severe("&m------------------------------------");
	}

	private static void printPath(String path, String... expected) {
		String[] splitPath = path.split("\\.");
		for (int i = 0; i < splitPath.length; i++) {

			final String whiteSpace = new String(new char[i]).replace("\0", "  ");

			if (i != splitPath.length - 1) {
				Logger.severe("&f" + whiteSpace + splitPath[i] + ":");
				continue;
			}

			Logger.severe("&f" + whiteSpace + splitPath[i] + ": <- Expected " + Arrays.toString(expected));
			if (expected.length <= 1) return;
			for (String str : expected) {
				Logger.severe("&f" + whiteSpace + " - " + str);
			}
		}
	}
}

