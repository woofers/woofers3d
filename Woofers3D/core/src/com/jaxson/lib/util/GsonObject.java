package com.jaxson.lib.util;

import com.google.gson.Gson;

/**
 * Class to serialize any {@code Object} into a string.
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public abstract class GsonObject<T>
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
		return fromJson(json, type);
	}

	/**
	 * Parses the json string into the object.
	 * @param json The json to parse
	 * @param type The class of the object
	 * @return {@link T} - Newly parsed object
	 */
	private T fromJson(String json, Class<T> type)
	{
		return new Gson().fromJson(json, type);
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
