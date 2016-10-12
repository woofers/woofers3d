package com.jaxson.lib.io;

import com.jaxson.lib.util.Unwrapable;
import com.jaxson.lib.util.Optional;
import com.jaxson.lib.util.Uncertainty;

/**
 * Decorator to save any {@link Object}.
 * @param <T> the type of {@link Object} being saved
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public class Jsonable<T> implements Unwrapable<T>
{
	private Optional<T> object;
	private JsonFile<T> file;

	public Jsonable(File file, Class<T> type)
	{
		this(file, type, null);
	}

	public Jsonable(File file, Class<T> type, T object)
	{
		this(new JsonFile<>(file, type), object);
	}

	public Jsonable(JsonFile<T> file, T object)
	{
		this.object = new Optional<>(object);
		setSaveFile(file);
		obtain();
	}

	public T unwrap()
	{
		return object.unwrap();
	}

	public JsonFile<T> saveFile()
	{
		return file;
	}

	public void obtain()
	{
		if (saveFile().exists())
			read();
		else
			save();
	}

	public void read()
	{
		Optional<T> newObject = new Optional<>(saveFile().readObject());
		if (newObject.exists()) this.object = newObject;
	}

	public void save()
	{
		saveFile().write(unwrap());
	}

	public void setSaveFile(JsonFile<T> file)
	{
		this.file = file;
	}
}
