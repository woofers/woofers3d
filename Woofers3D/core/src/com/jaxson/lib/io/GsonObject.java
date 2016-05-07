package com.jaxson.lib.io;

import com.google.gson.Gson;

/**
 * Class to serialize any {@link Object} into a string.
 * @param <T> the type of {@link Object} being saved
 * @param <F> the type of file used to save
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public abstract class GsonObject<T> extends SaveObject
{
	/**
	 * Stores the class type for obect parsing.
	 */
	private transient Class<T> type;

	/**
	 * Constructs an GsonObject containing the class type of the object.
	 * @param type The generic class
	 */
	public GsonObject(Class<T> type)
	{
		this.type = type;
	}

	/**
	 * Parses the json string into the object.
	 * @param json The json to parse
	 * @return {@link T} - Newly parsed object
	 */
	public T fromJson(String json)
	{
		return new Gson().fromJson(json, type);
	}

	/**
	 * Saves the current {@link Object} to a file.
	 */
	@Override
	public void save()
	{
		save(toJson());
	}

	/**
	 * Serializes the object into a json string.
	 * @return {@link String} - Newly serialized json string
	 */
	public String toJson()
	{
		return new Gson().toJson(this);
	}
}
