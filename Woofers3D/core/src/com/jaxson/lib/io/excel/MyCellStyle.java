package com.jaxson.lib.io.excel;

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
	public final static short ALIGN_CENTER_SELECTION = CellStyle.ALIGN_CENTER_SELECTION;
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
	public final static short BORDER_MEDIUM_DASHED = CellStyle.BORDER_MEDIUM_DASHED;
	public final static short BORDER_DASH_DOT = CellStyle.BORDER_DASH_DOT;
	public final static short BORDER_MEDIUM_DASH_DOT = CellStyle.BORDER_MEDIUM_DASH_DOT;
	public final static short BORDER_DASH_DOT_DOT = CellStyle.BORDER_DASH_DOT_DOT;
	public final static short BORDER_MEDIUM_DASH_DOT_DOT = CellStyle.BORDER_MEDIUM_DASH_DOT_DOT;
	public final static short BORDER_SLANTED_DASH_DOT = CellStyle.BORDER_SLANTED_DASH_DOT;

	public final static short NO_FILL = CellStyle.NO_FILL;
	public final static short SOLID_FOREGROUND = CellStyle.SOLID_FOREGROUND;
	public final static short FINE_DOTS = CellStyle.FINE_DOTS;
	public final static short ALT_BARS = CellStyle.ALT_BARS;
	public final static short SPARSE_DOTS = CellStyle.SPARSE_DOTS;
	public final static short THICK_HORZ_BANDS = CellStyle.THICK_HORZ_BANDS;
	public final static short THICK_VERT_BANDS = CellStyle.THICK_VERT_BANDS;
	public final static short THICK_BACKWARD_DIAG = CellStyle.THICK_BACKWARD_DIAG;
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

	@Override
	public MyCellStyle clone() throws CloneNotSupportedException
	{
		XSSFCellStyle style = null;
		if (getStyle() instanceof XSSFCellStyle)
		{
			style = (XSSFCellStyle) getStyle();
			style = (XSSFCellStyle) style.clone();
			return new MyCellStyle(style);
		}
		throw new CloneNotSupportedException("HSSFWorkbook clones not supported");
	}

	public short getAlignment()
	{
		return getStyle().getAlignment();
	}

	public short getBorderBottom()
	{
		return getStyle().getBorderBottom();
	}

	public short getBorderLeft()
	{
		return getStyle().getBorderLeft();
	}

	public short getBorderRight()
	{
		return getStyle().getBorderRight();
	}

	public short getBorderTop()
	{
		return getStyle().getBorderTop();
	}

	public short getBottomBorderColor()
	{
		return getStyle().getBottomBorderColor();
	}

	public short getDataFormat()
	{
		return getStyle().getDataFormat();
	}

	public String getDataFormatString()
	{
		return getStyle().getDataFormatString();
	}

	public Color getFillBackgroundColor()
	{
		return getStyle().getFillBackgroundColorColor();
	}

	public short getFillBackgroundColorIndex()
	{
		return getStyle().getFillBackgroundColor();
	}

	public Color getFillForegroundColor()
	{
		return getStyle().getFillForegroundColorColor();
	}

	public short getFillForegroundColorIndex()
	{
		return getStyle().getFillForegroundColor();
	}

	public short getFillPattern()
	{
		return getStyle().getFillPattern();
	}

	public short getFontIndex()
	{
		return getStyle().getFontIndex();
	}

	public boolean getHidden()
	{
		return getStyle().getHidden();
	}

	public short getIndention()
	{
		return getStyle().getIndention();
	}

	public short getIndex()
	{
		return getStyle().getIndex();
	}

	public short getLeftBorderColor()
	{
		return getStyle().getLeftBorderColor();
	}

	public boolean getLocked()
	{
		return getStyle().getLocked();
	}

	public short getRightBorderColor()
	{
		return getStyle().getRightBorderColor();
	}

	public short getRotation()
	{
		return getStyle().getRotation();
	}

	public boolean getShrinkToFit()
	{
		return getStyle().getShrinkToFit();
	}

	protected CellStyle getStyle()
	{
		return style;
	}

	public short getTopBorderColor()
	{
		return getStyle().getTopBorderColor();
	}

	public short getVerticalAlignment()
	{
		return getStyle().getVerticalAlignment();
	}

	public boolean hasWrappedText()
	{
		return getStyle().getWrapText();
	}

	public void set(MyCellStyle style)
	{
		getStyle().cloneStyleFrom(style.getStyle());
	}

	public void setAlignment(short alignment)
	{
		getStyle().setAlignment(alignment);
	}

	public void setBorderBottom(short border)
	{
		getStyle().setBorderBottom(border);
	}

	public void setBorderLeft(short border)
	{
		getStyle().setBorderLeft(border);
	}

	public void setBorderRight(short border)
	{
		getStyle().setBorderRight(border);
	}

	public void setBorderTop(short border)
	{
		getStyle().setBorderTop(border);
	}

	public void setBottomBorderColor(MyColor color)
	{
		setBottomBorderColor(color.getIndex());
	}

	public void setBottomBorderColor(short color)
	{
		getStyle().setBottomBorderColor(color);
	}

	public void setDataFormat(short dataFormat)
	{
		getStyle().setDataFormat(dataFormat);
	}

	public void setFillBackgroundColor(MyColor color)
	{
		setFillBackgroundColor(color.getIndex());
	}

	public void setFillBackgroundColor(short color)
	{
		getStyle().setFillBackgroundColor(color);
	}

	public void setFillForegroundColor(MyColor color)
	{
		setFillForegroundColor(color.getIndex());
	}

	public void setFillForegroundColor(short color)
	{
		getStyle().setFillForegroundColor(color);
	}

	public void setFillPattern(short fillPattern)
	{
		getStyle().setFillPattern(fillPattern);
	}

	public void setFont(Font font)
	{
		getStyle().setFont(font);
	}

	public void setHidden(boolean hidden)
	{
		getStyle().setHidden(hidden);
	}

	public void setIndention(short indent)
	{
		getStyle().setIndention(indent);
	}

	public void setLeftBorderColor(MyColor color)
	{
		setLeftBorderColor(color.getIndex());
	}

	public void setLeftBorderColor(short color)
	{
		getStyle().setLeftBorderColor(color);
	}

	public void setLocked(boolean locked)
	{
		getStyle().setLocked(locked);
	}

	public void setRightBorderColor(MyColor color)
	{
		setRightBorderColor(color.getIndex());
	}

	public void setRightBorderColor(short color)
	{
		getStyle().setRightBorderColor(color);
	}

	public void setRotation(short rotation)
	{
		getStyle().setRotation(rotation);
	}

	public void setShrinkToFit(boolean shrinkToFit)
	{
		getStyle().setShrinkToFit(shrinkToFit);
	}

	public void setTopBorderColor(MyColor color)
	{
		setTopBorderColor(color.getIndex());
	}

	public void setTopBorderColor(short color)
	{
		getStyle().setTopBorderColor(color);
	}

	public void setVerticalAlignment(short alignment)
	{
		getStyle().setVerticalAlignment(alignment);
	}

	public void setWrapText(boolean wrapped)
	{
		getStyle().setWrapText(wrapped);
	}
}
