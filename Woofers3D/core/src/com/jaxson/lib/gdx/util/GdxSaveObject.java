package com.jaxson.lib.gdx.util;

import com.jaxson.lib.util.SaveObject;
import com.jaxson.lib.util.Saveable;

/**
 * Default implementation of {@link Saveable}
 * Used to save any {@link Object}.
 * Uses the {@link GdxFile} instead of {@link MyFileReader}
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public abstract class GdxSaveObject extends SaveObject
{
	/**
	 * Gets whether the {@link Object} has a save file.
	 * @return {@link boolean} - Whether the {@link Object} has a save file
	 */
	@Override
	public boolean hasSaveData()
	{
		return getSaveFile().exists();
	}

	/**
	 * Reads the save file and sets the {@link Object}'s instance to it's
	 * values.
	 * @return {@link String} - The save file as a string
	 */
	@Override
	public String read()
	{
		return getSaveFile().read();
	}

	/**
	 * Saves the given data to the save file.
	 * @param data The data to write
	 */
	@Override
	protected void save(String data)
	{
		getSaveFile().write(data);
	}
}
