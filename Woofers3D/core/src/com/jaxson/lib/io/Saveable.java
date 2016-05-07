package com.jaxson.lib.io;

/**
 * Interface to save any {@link Object}.
 * @param <F> the type of file used to save.
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public interface Saveable
{
	/**
	 * Indicate the {@link SaveBehavior} of a {@link Object}.
	 * @author Jaxson Van Doorn
	 * @since 1.0
	 */
	public static enum SaveBehavior
	{
		/**
		 * Saved automaticly when a field is changed.
		 */
		Automatic,

		/**
		 * Saved on a interval.
		 */
		Timer,

		/**
		 * Never saved unless the user calls {@link #save()}.
		 */
		Manual;
	}

	/**
	 * Gets whether the {@link Object} auto saves.
	 * @return {@link boolean} - Whether the {@link Object} auto saves
	 */
	public boolean autoSaves();

	/**
	 * Gets the {@link SaveBehavior} of the {@link Object}.
	 * @return {@link SaveBehavior} - The {@link SaveBehavior} of the
	 * {@link Object}
	 */
	public SaveBehavior getSaveBehavior();

	/**
	 * Gets the save file of the {@link Object}.
	 * @return {@link File} - The save file of the {@link Object}
	 */
	public File getSaveFile();

	/**
	 * Gets whether the {@link Object} has save data.
	 * @return {@link boolean} - Whether the {@link Object} has a save file
	 */
	public boolean hasSaveData();

	/**
	 * Reads the save file and sets the {@link Object}'s instance to it's
	 * values.
	 * @return {@link String} - The save file as a string
	 */
	public String read();

	/**
	 * Saves the current {@link Object} to a file.
	 */
	public void save();

	/**
	 * Sets the {@link SaveBehavior} of the {@link Object}.
	 * @param saveBehavior The {@link SaveBehavior} of the {@link Object}
	 */
	public void setSaveBehavior(SaveBehavior saveBehavior);

	/**
	 * Sets the save path where the {@link Object} saves.
	 * @param saveFile The file that the {@link Object} saves to
	 */
	public void setSaveFile(File saveFile);

	/**
	 * Searches for a save file and if one exists it reads it.
	 * Otherwise it creates one.
	 */
	public void smartRead();
}
