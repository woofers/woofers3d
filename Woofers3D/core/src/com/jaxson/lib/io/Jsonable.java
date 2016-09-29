package com.jaxson.lib.io;

import com.jaxson.lib.util.Unwrapable;

/**
 * Decorator to save any {@link Object}.
 * @param <T> the type of {@link Object} being saved
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public class Jsonable<T> implements Unwrapable<T>
{
	private T object;
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
		this.object = object;
		setSaveFile(file);
		obtain();
	}

	public T unwarp()
	{
		return object;
	}

	public JsonFile<T> saveFile()
	{
		return file;
	}

	public boolean isPresent()
	{
		return unwarp() != null;
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
		T newObject = saveFile().readObject();
		if (newObject != null) this.object = newObject;
	}

	public void save()
	{
		saveFile().write(unwarp());
	}

	public void setSaveFile(JsonFile<T> file)
	{
		this.file = file;
	}

	@Override
	public T unwrap()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
