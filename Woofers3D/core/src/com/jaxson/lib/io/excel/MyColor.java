package com.jaxson.lib.io.excel;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.ExtendedColor;
import org.apache.poi.ss.usermodel.IndexedColors;

public class MyColor
{
	public static final MyColor GREEN = new MyColor(IndexedColors.LIGHT_GREEN);
	public static final MyColor RED = new MyColor(IndexedColors.RED);
	public static final MyColor ORANGE = new MyColor(IndexedColors.LIGHT_ORANGE);

	private static final IndexedColors WHITE = IndexedColors.WHITE;

	private Object color;

	public MyColor()
	{
		this(WHITE);
	}

	public MyColor(Color color)
	{
		this.color = color;
	}

	public MyColor(IndexedColors color)
	{
		this.color = color;
	}

	private Object getColor()
	{
		return color;
	}

	public short getIndex()
	{
		if (getColor() instanceof HSSFColor)
		{
			HSSFColor color = (HSSFColor) getColor();
			return color.getIndex();
		}
		if (getColor() instanceof ExtendedColor)
		{
			ExtendedColor color = (ExtendedColor) getColor();
			if (color.isIndexed()) return color.getIndex();
		}
		if (getColor() instanceof IndexedColors)
		{
			IndexedColors color = (IndexedColors) getColor();
			return color.getIndex();
		}
		return WHITE.getIndex();
	}
}
