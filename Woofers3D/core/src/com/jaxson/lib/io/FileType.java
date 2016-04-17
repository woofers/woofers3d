package com.jaxson.lib.io;

public class FileType
{
	public static final FileType NONE = new FileType("");
	public static final FileType DIB = new FileType("dib");
	public static final FileType EMF = new FileType("emf");
	public static final FileType JPEG = new FileType("jpg");
	public static final FileType JPG = JPEG;
	public static final FileType PICT = new FileType("pict");
	public static final FileType PNG = new FileType("png");
	public static final FileType WMP = new FileType("wmp");
	public static final FileType G3DJ = new FileType("g3dj");
	public static final FileType G3DB = new FileType("g3db");
	public static final FileType OBJ = new FileType("obj");
	public static final FileType XLS = new FileType("xls");
	public static final FileType XLSX = new FileType("xlsx");

	private static final int MAX_CHARACTER = 55;
	private static final String MAX_CHARACTER_EXCEEDED = "Max character limit exceeded";

	private String extension;

	public FileType(String extension)
	{
		this.extension = new DefaultFile(extension).getExtension().toLowerCase();
		if (extension.equals("jpeg")) this.extension = "jpg";
		if (extension.length() >= MAX_CHARACTER) throw new IllegalArgumentException(MAX_CHARACTER_EXCEEDED);
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
		return equals(JPEG) || equals(PICT) || equals(PNG);
	}

	public boolean isModel()
	{
		return equals(G3DJ) || equals(G3DB) || equals(OBJ);
	}

	public boolean isSpreadSheet()
	{
		return equals(XLS) || equals(XLSX);
	}
}
