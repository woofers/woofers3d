package com.jaxson.lib.io;

import com.jaxson.lib.util.exceptions.FileExtensionNotFoundException;

public enum FileType
{
	DIB, EMF, JPEG, PICT, PNG, WMP, G3DJ, G3DB, OBJ, XLS, XLSX;

	private static final String DIB_EXTENSION = "dib";
	private static final String EMF_EXTENSION = "emf";
	private static final String JPEG_EXTENSION = "jpeg";
	private static final String JPG_EXTENSION = "jpg";
	private static final String PICT_EXTENSION = "pict";
	private static final String PNG_EXTENSION = "png";
	private static final String WMP_EXTENSION = "wmp";

	private static final String G3DJ_EXTENSION = "g3dj";
	private static final String G3DB_EXTENSION = "g3db";
	private static final String OBJ_EXTENSION = "obj";

	private static final String XLS_EXTENSION = "xls";
	private static final String XLSX_EXTENSION = "xlsx";

	public String getExtension()
	{
		switch (this)
		{
			case DIB:
				return DIB_EXTENSION;
			case EMF:
				return EMF_EXTENSION;
			case JPEG:
				return JPEG_EXTENSION;
			case PICT:
				return PICT_EXTENSION;
			case PNG:
				return PNG_EXTENSION;
			case WMP:
				return WMP_EXTENSION;
			case G3DJ:
				return G3DJ_EXTENSION;
			case G3DB:
				return G3DB_EXTENSION;
			case OBJ:
				return OBJ_EXTENSION;
			case XLS:
				return XLS_EXTENSION;
		}
		return XLSX_EXTENSION;
	}

	public boolean isImage()
	{
		return !isSpreadSheet() && !isModel();
	}

	public boolean isModel()
	{
		return this == G3DJ || this == G3DB || this == OBJ;
	}

	public boolean isSpreadSheet()
	{
		return this == XLS || this == XLSX;
	}

	public static FileType getType(String extension)
	{
		extension = extension.toLowerCase();
		if (extension.equals(DIB_EXTENSION)) return DIB;
		if (extension.equals(EMF_EXTENSION)) return EMF;
		if (extension.equals(JPEG_EXTENSION) || extension.equals(JPG_EXTENSION)) return JPEG;
		if (extension.equals(PICT_EXTENSION)) return PICT;
		if (extension.equals(PNG_EXTENSION)) return PNG;
		if (extension.equals(WMP_EXTENSION)) return WMP;
		if (extension.equals(G3DJ_EXTENSION)) return G3DJ;
		if (extension.equals(G3DB_EXTENSION)) return G3DB;
		if (extension.equals(OBJ_EXTENSION)) return OBJ;
		if (extension.equals(XLS_EXTENSION)) return XLS;
		if (extension.equals(XLSX_EXTENSION)) return XLSX;
		throw new FileExtensionNotFoundException(extension);
	}
}
