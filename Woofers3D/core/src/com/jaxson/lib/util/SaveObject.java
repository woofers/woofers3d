package com.jaxson.lib.util;

/**
 * Default implementation of {@link Saveable}
 * Used to save any {@link Object}.
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public abstract class SaveObject implements Saveable
{
	private transient File saveFile;
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
	 * Gets the save saveFile of the {@link Object}.
	 * @return {@link saveFile} - The save saveFile of the {@link Object}
	 */
	@Override
	public File getSaveFile()
	{
		if (saveFile == null) saveFile = new File(toString().replace("@", "_"));
		return saveFile;
	}

	/**
	 * Sets the save path where the {@link Object} saves.
	 * @return {@link String} - The save path where the {@link Object} saves
	 */
	public String getSavePath()
	{
		return getSaveFile().getPath();
	}

	/**
	 * Gets whether the {@link Object} has a save saveFile.
	 * @return {@link boolean} - Whether the {@link Object} has a save saveFile
	 */
	@Override
	public boolean hasSaveData()
	{
		return saveFile.exists();
	}

	/**
	 * Reads the save saveFile and sets the {@link Object}'s instance to it's
	 * values.
	 * @return {@link String} - The save saveFile as a string
	 */
	@Override
	public String read()
	{
		return saveFile.read();
	}

	/**
	 * Saves the current {@link Object} to a saveFile.
	 */
	@Override
	public abstract void save();

	/**
	 * Saves the given data to the save saveFile.
	 * @param data The data to write
	 */
	protected void save(String data)
	{
		saveFile.write(data);

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
	 * @param saveFile The saveFile that the {@link Object} saves to
	 */
	@Override
	public void setSaveFile(File saveFile)
	{
		this.saveFile = saveFile;
	}

	/**
	 * Sets the save path where the {@link Object} saves.
	 * @param savePath The save path where the {@link Object} saves
	 */
	public void setSavePath(String savePath)
	{
		this.saveFile = new File(savePath);
	}

	/**
	 * Searches for a save saveFile and if one exists it reads it.
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
