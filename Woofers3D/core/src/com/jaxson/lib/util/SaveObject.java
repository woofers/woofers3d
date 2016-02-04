package com.jaxson.lib.util;

/**
 * Default implementation of {@link Saveable}
 * Used to save any {@link Object}.
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public abstract class SaveObject implements Saveable
{
	private transient String savePath;
	private SaveBehavior saveBehavior = SaveBehavior.Manual;

	/**
	 * Saves when called if the {@link Object} uses auto saving.
	 */
	protected void autoSave()
	{
		if (autoSaves()) save();
	}

	/**
	 * Gets whether the {@link Object} auto saves.
	 * @return {@link boolean} - Whether the {@link Object} auto saves
	 */
	@Override
	public boolean autoSaves()
	{
		return getSaveBehavior() == SaveBehavior.Automatic;
	}

	/**
	 * Gets the {@link SaveBehavior} of the {@link Object}.
	 * @return {@link SaveBehavior} - The {@link SaveBehavior} of the
	 * {@link Object};
	 */
	@Override
	public SaveBehavior getSaveBehavior()
	{
		return saveBehavior;
	}

	/**
	 * Sets the save path where the {@link Object} saves.
	 * @return {@link String} - The save path where the {@link Object} saves
	 */
	@Override
	public String getSavePath()
	{
		if (savePath == null) return toString().replace("@", "_");
		return savePath;
	}

	/**
	 * Gets whether the {@link Object} has a save file.
	 * @return {@link boolean} - Whether the {@link Object} has a save file
	 */
	@Override
	public boolean hasSaveData()
	{
		return MyFileReader.exists(getSavePath());
	}

	/**
	 * Reads the save file and sets the {@link Object}'s instance to it's
	 * values.
	 * @return {@link String} - The save file as a string
	 */
	@Override
	public String read()
	{
		return MyFileReader.read(getSavePath());
	}

	/**
	 * Saves the current {@link Object} to a file.
	 */
	@Override
	public abstract void save();

	/**
	 * Saves the given data to the save file.
	 * @param data The data to write
	 */
	protected void save(String data)
	{
		MyFileReader.write(getSavePath(), data);

	}

	/**
	 * Sets the {@link SaveBehavior} of the {@link Object}.
	 * @param saveBehavior The {@link SaveBehavior} of the {@link Object}
	 */
	@Override
	public void setSaveBehavior(SaveBehavior saveBehavior)
	{
		this.saveBehavior = saveBehavior;
	}

	/**
	 * Sets the save path where the {@link Object} saves.
	 * @param savePath The save path where the {@link Object} saves
	 */
	@Override
	public void setSavePath(String savePath)
	{
		this.savePath = savePath;
	}

	/**
	 * Searches for a save file and if one exists it reads it.
	 * Otherwise it creates one.
	 */
	@Override
	public void smartRead()
	{
		if (hasSaveData())
		{
			read();
			return;
		}
		save();
	}
}
