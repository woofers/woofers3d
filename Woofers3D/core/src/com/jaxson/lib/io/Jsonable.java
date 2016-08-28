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
	private File file;

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

	public T get()
	{
		return object;
	}

	public File getSaveFile()
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
		this.object = (T) getSaveFile().readObject();
	}

	public void save()
	{
		getSaveFile().write(get());
	}

	public void setSaveFile(File file)
	{
		this.file = file;
	}
}
