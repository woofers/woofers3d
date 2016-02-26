package com.jaxson.lib.io;

import com.jaxson.lib.util.exceptions.NullValueException;

/**
 * Default implementation of {@link Saveable}
 * Used to save any {@link Object}.
 * @param <F> the type of file used to save
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public abstract class SaveObject<F extends File> implements Saveable<F>
{
	private transient F saveFile;
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
	 * {@link Object}
	 */
	@Override
	public SaveBehavior getSaveBehavior()
	{
		return saveBehavior;
	}

	/**
	 * Gets the save file of the {@link Object}.
	 * @return {@link F} - The save file of the {@link Object}
	 */
	@Override
	public F getSaveFile()
	{
		if (saveFile == null) throw new NullValueException("saveFile");
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
	 * Gets whether the {@link Object} has save data.
	 * @return {@link boolean} - Whether the {@link Object} has a save file
	 */
	@Override
	public boolean hasSaveData()
	{
		return saveFile.exists();
	}

	/**
	 * Reads the save file and sets the {@link Object}'s instance to it's
	 * values.
	 * @return {@link String} - The save file as a string
	 */
	@Override
	public String read()
	{
		return saveFile.read();
	}

	/**
	 * Saves the current {@link Object} to a {@link File}.
	 */
	@Override
	public abstract void save();

	/**
	 * Saves the given data to the {@link File}.
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
	 * @param saveFile The {@link File} that the {@link Object} saves to
	 */
	@Override
	public void setSaveFile(F saveFile)
	{
		this.saveFile = saveFile;
	}

	/**
	 * Searches for a {@link File} and if one exists it reads it.
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
