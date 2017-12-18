package com.jaxson.lib.io.excel.workbook;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

public class MyCellStyle implements Cloneable
{
	public final static short ALIGN_GENERAL = CellStyle.ALIGN_GENERAL;
	public final static short ALIGN_LEFT = CellStyle.ALIGN_LEFT;
	public final static short ALIGN_CENTER = CellStyle.ALIGN_CENTER;
	public final static short ALIGN_RIGHT = CellStyle.ALIGN_RIGHT;
	public final static short ALIGN_FILL = CellStyle.ALIGN_FILL;
	public final static short ALIGN_JUSTIFY = CellStyle.ALIGN_JUSTIFY;
	public final static short ALIGN_CENTER_SELECTION
			= CellStyle.ALIGN_CENTER_SELECTION;
	public final static short VERTICAL_TOP = CellStyle.VERTICAL_TOP;
	public final static short VERTICAL_CENTER = CellStyle.VERTICAL_CENTER;
	public final static short VERTICAL_BOTTOM = CellStyle.VERTICAL_BOTTOM;
	public final static short VERTICAL_JUSTIFY = CellStyle.VERTICAL_JUSTIFY;

	public final static short BORDER_NONE = CellStyle.BORDER_NONE;
	public final static short BORDER_THIN = CellStyle.BORDER_THIN;
	public final static short BORDER_MEDIUM = CellStyle.BORDER_MEDIUM;
	public final static short BORDER_DASHED = CellStyle.BORDER_DASHED;
	public final static short BORDER_HAIR = CellStyle.BORDER_HAIR;
	public final static short BORDER_THICK = CellStyle.BORDER_THICK;
	public final static short BORDER_DOUBLE = CellStyle.BORDER_DOUBLE;
	public final static short BORDER_DOTTED = CellStyle.BORDER_DOTTED;
	public final static short BORDER_MEDIUM_DASHED
			= CellStyle.BORDER_MEDIUM_DASHED;
	public final static short BORDER_DASH_DOT = CellStyle.BORDER_DASH_DOT;
	public final static short BORDER_MEDIUM_DASH_DOT
			= CellStyle.BORDER_MEDIUM_DASH_DOT;
	public final static short BORDER_DASH_DOT_DOT
			= CellStyle.BORDER_DASH_DOT_DOT;
	public final static short BORDER_MEDIUM_DASH_DOT_DOT
			= CellStyle.BORDER_MEDIUM_DASH_DOT_DOT;
	public final static short BORDER_SLANTED_DASH_DOT
			= CellStyle.BORDER_SLANTED_DASH_DOT;

	public final static short NO_FILL = CellStyle.NO_FILL;
	public final static short SOLID_FOREGROUND = CellStyle.SOLID_FOREGROUND;
	public final static short FINE_DOTS = CellStyle.FINE_DOTS;
	public final static short ALT_BARS = CellStyle.ALT_BARS;
	public final static short SPARSE_DOTS = CellStyle.SPARSE_DOTS;
	public final static short THICK_HORZ_BANDS = CellStyle.THICK_HORZ_BANDS;
	public final static short THICK_VERT_BANDS = CellStyle.THICK_VERT_BANDS;
	public final static short THICK_BACKWARD_DIAG
			= CellStyle.THICK_BACKWARD_DIAG;

	public final static short THICK_FORWARD_DIAG = CellStyle.THICK_FORWARD_DIAG;
	public final static short BIG_SPOTS = CellStyle.BIG_SPOTS;
	public final static short BRICKS = CellStyle.BRICKS;
	public final static short THIN_HORZ_BANDS = CellStyle.THIN_HORZ_BANDS;
	public final static short THIN_VERT_BANDS = CellStyle.THIN_VERT_BANDS;
	public final static short THIN_BACKWARD_DIAG = CellStyle.THIN_BACKWARD_DIAG;
	public final static short THIN_FORWARD_DIAG = CellStyle.THIN_FORWARD_DIAG;
	public final static short SQUARES = CellStyle.SQUARES;
	public final static short DIAMONDS = CellStyle.DIAMONDS;
	public final static short LESS_DOTS = CellStyle.LESS_DOTS;
	public final static short LEAST_DOTS = CellStyle.LEAST_DOTS;

	private final static short FILL_PATTERN = SOLID_FOREGROUND;

	private CellStyle style;

	public MyCellStyle(CellStyle style)
	{
		this.style = style;
		setFillPattern(FILL_PATTERN);
	}

	public short alignment()
	{
		return style().getAlignment();
	}

	public short bottomBorder()
	{
		return style().getBorderBottom();
	}

	public short bottomBorderColor()
	{
		return style().getBottomBorderColor();
	}

	@Override
	public MyCellStyle clone() throws CloneNotSupportedException
	{
		XSSFCellStyle style = null;
		if (style() instanceof XSSFCellStyle)
		{
			style = (XSSFCellStyle) style();
			style = (XSSFCellStyle) style.clone();
			return new MyCellStyle(style);
		}
		throw new CloneNotSupportedException(
				"HSSFWorkbook clones not supported");
	}

	public short dataFormat()
	{
		return style().getDataFormat();
	}

	public String dataFormatAsString()
	{
		return style().getDataFormatString();
	}

	public Color fillBackgroundColor()
	{
		return style().getFillBackgroundColorColor();
	}

	public short fillBackgroundColorIndex()
	{
		return style().getFillBackgroundColor();
	}

	public Color fillForegroundColor()
	{
		return style().getFillForegroundColorColor();
	}

	public short fillForegroundColorIndex()
	{
		return style().getFillForegroundColor();
	}

	public short fillPattern()
	{
		return style().getFillPattern();
	}

	public short fontIndex()
	{
		return style().getFontIndex();
	}

	public boolean hasWrappedText()
	{
		return style().getWrapText();
	}

	public short indention()
	{
		return style().getIndention();
	}

	public short index()
	{
		return style().getIndex();
	}

	public boolean isHidden()
	{
		return style().getHidden();
	}

	public short leftBorder()
	{
		return style().getBorderLeft();
	}

	public short leftBorderColor()
	{
		return style().getLeftBorderColor();
	}

	public boolean locked()
	{
		return style().getLocked();
	}

	public short rightBorder()
	{
		return style().getBorderRight();
	}

	public short rightBorderColor()
	{
		return style().getRightBorderColor();
	}

	public short rotation()
	{
		return style().getRotation();
	}

	public void set(MyCellStyle style)
	{
		style().cloneStyleFrom(style.style());
	}

	public void setAlignment(short alignment)
	{
		style().setAlignment(alignment);
	}

	public void setBottomBorder(short border)
	{
		style().setBorderBottom(border);
	}

	public void setBottomBorderColor(MyColor color)
	{
		setBottomBorderColor(color.getIndex());
	}

	public void setBottomBorderColor(short color)
	{
		style().setBottomBorderColor(color);
	}

	public void setDataFormat(short dataFormat)
	{
		style().setDataFormat(dataFormat);
	}

	public void setFillBackgroundColor(MyColor color)
	{
		setFillBackgroundColor(color.getIndex());
	}

	public void setFillBackgroundColor(short color)
	{
		style().setFillBackgroundColor(color);
	}

	public void setFillForegroundColor(MyColor color)
	{
		setFillForegroundColor(color.getIndex());
	}

	public void setFillForegroundColor(short color)
	{
		style().setFillForegroundColor(color);
	}

	public void setFillPattern(short fillPattern)
	{
		style().setFillPattern(fillPattern);
	}

	public void setFont(Font font)
	{
		style().setFont(font);
	}

	public void setHidden(boolean hidden)
	{
		style().setHidden(hidden);
	}

	public void setIndention(short indent)
	{
		style().setIndention(indent);
	}

	public void setLeftBorder(short border)
	{
		style().setBorderLeft(border);
	}

	public void setLeftBorderColor(MyColor color)
	{
		setLeftBorderColor(color.getIndex());
	}

	public void setLeftBorderColor(short color)
	{
		style().setLeftBorderColor(color);
	}

	public void setLocked(boolean locked)
	{
		style().setLocked(locked);
	}

	public void setRightBorder(short border)
	{
		style().setBorderRight(border);
	}

	public void setRightBorderColor(MyColor color)
	{
		setRightBorderColor(color.getIndex());
	}

	public void setRightBorderColor(short color)
	{
		style().setRightBorderColor(color);
	}

	public void setRotation(short rotation)
	{
		style().setRotation(rotation);
	}

	public void setShrinkToFit(boolean shrinkToFit)
	{
		style().setShrinkToFit(shrinkToFit);
	}

	public void setTopBorder(short border)
	{
		style().setBorderTop(border);
	}

	public void setTopBorderColor(MyColor color)
	{
		setTopBorderColor(color.getIndex());
	}

	public void setTopBorderColor(short color)
	{
		style().setTopBorderColor(color);
	}

	public void setVerticalAlignment(short alignment)
	{
		style().setVerticalAlignment(alignment);
	}

	public void setWrapText(boolean wrapped)
	{
		style().setWrapText(wrapped);
	}

	public boolean shrinksToFit()
	{
		return style().getShrinkToFit();
	}

	protected CellStyle style()
	{
		return style;
	}

	public short topBorder()
	{
		return style().getBorderTop();
	}

	public short topBorderColor()
	{
		return style().getTopBorderColor();
	}

	public short verticalAlignment()
	{
		return style().getVerticalAlignment();
	}
}
