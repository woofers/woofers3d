package com.jaxson.lib.io.excel.workbook;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.ExtendedColor;
import org.apache.poi.ss.usermodel.IndexedColors;

public class MyColor
{
	public static final MyColor BLACK = new MyColor(IndexedColors.BLACK);
	public static final MyColor WHITE = new MyColor(IndexedColors.WHITE);
	public static final MyColor RED = new MyColor(IndexedColors.RED);
	public static final MyColor BRIGHT_GREEN
			= new MyColor(IndexedColors.BRIGHT_GREEN);
	public static final MyColor DARK_BLUE = new MyColor(IndexedColors.BLUE);
	public static final MyColor YELLOW = new MyColor(IndexedColors.YELLOW);
	public static final MyColor PINK = new MyColor(IndexedColors.PINK);
	public static final MyColor CYAN = new MyColor(IndexedColors.TURQUOISE);
	public static final MyColor MAROON = new MyColor(IndexedColors.DARK_RED);
	public static final MyColor GREEN = new MyColor(IndexedColors.GREEN);
	public static final MyColor PURPLE = new MyColor(IndexedColors.DARK_BLUE);
	public static final MyColor AMBER = new MyColor(IndexedColors.DARK_YELLOW);
	public static final MyColor VIOLET = new MyColor(IndexedColors.VIOLET);
	public static final MyColor TEAL = new MyColor(IndexedColors.TEAL);
	public static final MyColor LIGHT_GRAY
			= new MyColor(IndexedColors.GREY_25_PERCENT);
	public static final MyColor GRAY
			= new MyColor(IndexedColors.GREY_50_PERCENT);
	public static final MyColor LAVENDER
			= new MyColor(IndexedColors.CORNFLOWER_BLUE);
	public static final MyColor WINE = new MyColor(IndexedColors.MAROON);
	public static final MyColor LEMON
			= new MyColor(IndexedColors.LEMON_CHIFFON);
	public static final MyColor ORCHID = new MyColor(IndexedColors.ORCHID);
	public static final MyColor SALMON = new MyColor(IndexedColors.CORAL);
	public static final MyColor BLUE = new MyColor(IndexedColors.ROYAL_BLUE);
	public static final MyColor LIGHT_LAVENDER
			= new MyColor(IndexedColors.LIGHT_CORNFLOWER_BLUE);
	public static final MyColor SKY = new MyColor(IndexedColors.SKY_BLUE);
	public static final MyColor LIGHT_SKY
			= new MyColor(IndexedColors.LIGHT_TURQUOISE);
	public static final MyColor LIGHT_GREEN
			= new MyColor(IndexedColors.LIGHT_GREEN);
	public static final MyColor LIGHT_YELLOW
			= new MyColor(IndexedColors.LIGHT_YELLOW);
	public static final MyColor PALE_BLUE
			= new MyColor(IndexedColors.PALE_BLUE);
	public static final MyColor ROSE = new MyColor(IndexedColors.ROSE);
	public static final MyColor DARK_LAVENDER
			= new MyColor(IndexedColors.LAVENDER);
	public static final MyColor TAN = new MyColor(IndexedColors.TAN);
	public static final MyColor MILD_BLUE
			= new MyColor(IndexedColors.LIGHT_BLUE);
	public static final MyColor AQUA = new MyColor(IndexedColors.AQUA);
	public static final MyColor LIME = new MyColor(IndexedColors.LIME);
	public static final MyColor GOLD = new MyColor(IndexedColors.GOLD);
	public static final MyColor ORANGE
			= new MyColor(IndexedColors.LIGHT_ORANGE);
	public static final MyColor RED_ORANGE = new MyColor(IndexedColors.ORANGE);
	public static final MyColor DARK_PURPLE
			= new MyColor(IndexedColors.BLUE_GREY);
	public static final MyColor MILD_GRAY
			= new MyColor(IndexedColors.GREY_40_PERCENT);
	public static final MyColor DARK_TEAL
			= new MyColor(IndexedColors.DARK_TEAL);
	public static final MyColor SEA_GREEN
			= new MyColor(IndexedColors.SEA_GREEN);
	public static final MyColor DARK_GREEN
			= new MyColor(IndexedColors.DARK_GREEN);
	public static final MyColor OLIVE_GREEN
			= new MyColor(IndexedColors.OLIVE_GREEN);
	public static final MyColor ORANGE_BROWN = new MyColor(IndexedColors.BROWN);
	public static final MyColor PLUM = new MyColor(IndexedColors.PLUM);
	public static final MyColor INDIGO = new MyColor(IndexedColors.INDIGO);
	public static final MyColor CHARCOAL
			= new MyColor(IndexedColors.GREY_80_PERCENT);

	public static final MyColor CORRECT = LIGHT_GREEN;
	public static final MyColor INCORRECT = RED;
	public static final MyColor WARNING = ORANGE;

	private static final IndexedColors DEFAULT = IndexedColors.WHITE;

	private Object color;

	public MyColor()
	{
		this(DEFAULT);
	}

	public MyColor(Color color)
	{
		this((Object) color);
	}

	public MyColor(IndexedColors color)
	{
		this((Object) color);
	}

	public MyColor(int color)
	{
		this((Object) color);
	}

	private MyColor(Object color)
	{
		this.color = color;
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
		if (getColor() instanceof Integer)
		{
			int color = (Integer) getColor();
			return (short) color;
		}
		return DEFAULT.getIndex();
	}

	private Object getColor()
	{
		return color;
	}
}
