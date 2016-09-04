package com.jaxson.lib.io;

/**
 * Decorator to save any {@link Object}.
 * @param <T> the type of {@link Object} being saved
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public class Jsonable<T>
{
	private T object;
	private JsonFile<T> file;

	public Jsonable(File file, Class<T> type)
	{
		this(file, type, null);
	}

	public Jsonable(File file, Class<T> type, T object)
	{
		this(new JsonFile<T>(file, type), object);
	}

	public Jsonable(JsonFile<T> file, T object)
	{
		this.object = object;
		setSaveFile(file);
		obtain();
	}

	public T get()
	{
		return object;
	}

	public JsonFile<T> getSaveFile()
	{
		return file;
	}

	public boolean isPresent()
	{
		return get() != null;
	}

	public void obtain()
	{
		if (getSaveFile().exists())
			read();
		else
			save();
	}

	public void read()
	{
		this.object = getSaveFile().readObject();
	}

	public void save()
	{
		getSaveFile().write(get());
	}

	public void setSaveFile(JsonFile<T> file)
	{
		this.file = file;
	}
}
