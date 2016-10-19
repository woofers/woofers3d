package com.jaxson.lib.io;

import java.util.HashMap;
import com.jaxson.lib.util.Measurable;
import com.jaxson.lib.util.Printer;

public class FileExtension implements Measurable
{
	private static HashMap<String, String> exceptions = new HashMap<>();

	static
	{
		addException("jpeg", "jpg");
		addException("htm", "html");
	}

	public static final FileExtension NONE = new FileExtension("");
	public static final FileExtension DIB = new FileExtension("dib");
	public static final FileExtension EMF = new FileExtension("emf");
	public static final FileExtension JPG = new FileExtension("jpg");
	public static final FileExtension PICT = new FileExtension("pict");
	public static final FileExtension PNG = new FileExtension("png");
	public static final FileExtension WMP = new FileExtension("wmp");
	public static final FileExtension G3DJ = new FileExtension("g3dj");
	public static final FileExtension G3DB = new FileExtension("g3db");
	public static final FileExtension OBJ = new FileExtension("obj");
	public static final FileExtension XLS = new FileExtension("xls");
	public static final FileExtension XLSX = new FileExtension("xlsx");
	public static final FileExtension HTML = new FileExtension("html");

	private static final int MAX_CHARACTER = 55;
	private static final String MAX_CHARACTER_EXCEEDED
			= "Max character limit exceeded";

	private final String extension;

	public FileExtension(String extension)
	{
		this.extension = formatExtension(extension);
		System.out.println(toString());
		if (size() >= MAX_CHARACTER)
			throw new IllegalArgumentException(MAX_CHARACTER_EXCEEDED);
	}

	public boolean equals(FileExtension other)
	{
		return extension().equals(other.extension());
	}

	@Override
	public boolean equals(Object other)
	{
		if (other instanceof FileExtension)
			return equals((FileExtension) other);
		return false;
	}

	public String extension()
	{
		return extension;
	}

	public boolean isImage()
	{
		return equals(JPG) || equals(PICT) || equals(PNG);
	}

	public boolean isModel()
	{
		return equals(G3DJ) || equals(G3DB) || equals(OBJ);
	}

	public boolean isSpreadSheet()
	{
		return equals(XLS) || equals(XLSX);
	}

	@Override
	public int size()
	{
		return extension().length();
	}

	@Override
	public String toString()
	{
		return new Printer(getClass(),
				new Printer.Label(extension())).toString();
	}

	public static void addException(String original, String translated)
	{
		exceptions.put(original, translated);
	}

	private static String findStandardExtension(String exception)
	{
		if (!exceptions.containsKey(exception)) return exception;
		return exceptions.get(exception);
	}

	private static String formatExtension(String extension)
	{
		DataFile file = new DataFile(extension);
		String newExtension = file.extension();
		if (newExtension.isEmpty()) newExtension = file.name();
		newExtension = findStandardExtension(newExtension);
		return newExtension;
	}

	public static void removeException(String exception)
	{
		if (!exceptions.containsKey(exception)) return;
		exceptions.remove(exceptions);
	}
}
