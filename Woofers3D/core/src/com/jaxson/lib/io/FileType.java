package com.jaxson.lib.io;

import java.util.HashMap;

public class FileType
{
	private static HashMap<String, String> exceptions = new HashMap<>();

	static
	{
		addException("jpeg", "jpg");
		addException("htm", "html");
	}

	public static final FileType NONE = new FileType("");
	public static final FileType DIB = new FileType("dib");
	public static final FileType EMF = new FileType("emf");
	public static final FileType JPG = new FileType("jpg");
	public static final FileType PICT = new FileType("pict");
	public static final FileType PNG = new FileType("png");
	public static final FileType WMP = new FileType("wmp");
	public static final FileType G3DJ = new FileType("g3dj");
	public static final FileType G3DB = new FileType("g3db");
	public static final FileType OBJ = new FileType("obj");
	public static final FileType XLS = new FileType("xls");
	public static final FileType XLSX = new FileType("xlsx");
	public static final FileType HTML = new FileType("html");

	private static final int MAX_CHARACTER = 55;
	private static final String MAX_CHARACTER_EXCEEDED =
			"Max character limit exceeded";

	private final String extension;

	public FileType(String extension)
	{
		this.extension = formatExtension(extension);
		if (length() >= MAX_CHARACTER)
			throw new IllegalArgumentException(MAX_CHARACTER_EXCEEDED);
	}

	public boolean equals(FileType other)
	{
		return getExtension().equals(other.getExtension());
	}

	@Override
	public boolean equals(Object other)
	{
		if (other instanceof FileType) return equals((FileType) other);
		return false;
	}

	public String getExtension()
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

	public int length()
	{
		return getExtension().length();
	}

	@Override
	public String toString()
	{
		return getExtension();
	}

	public static void addException(String original, String translated)
	{
		if (original != null) exceptions.put(original, translated);
	}

	public static void removeException(String exception)
	{
		if (!exceptions.containsKey(exception)) return;
		exceptions.remove(exceptions);
	}

	private static String findStandardExtension(String exception)
	{
		if (!exceptions.containsKey(exception)) return exception;
		return exceptions.get(exception);
	}

	private static String formatExtension(String extension)
	{
		DefaultFile file = new DefaultFile(extension);
		String newExtension = file.getExtension();
		if (newExtension.isEmpty()) newExtension = file.getName();
		newExtension = findStandardExtension(newExtension);
		return newExtension;
	}
}
