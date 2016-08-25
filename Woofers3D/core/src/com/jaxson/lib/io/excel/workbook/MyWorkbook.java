package com.jaxson.lib.io.excel.workbook;

import com.jaxson.lib.io.DefaultFile;
import com.jaxson.lib.util.MyArrayList;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.formula.udf.UDFFinder;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MyWorkbook implements Iterable<MySheet>, AutoCloseable, Closeable
{
	public static final int PICTURE_EMF = Workbook.PICTURE_TYPE_EMF;
	public static final int PICTURE_WMF = Workbook.PICTURE_TYPE_WMF;
	public static final int PICTURE_PICT = Workbook.PICTURE_TYPE_PICT;
	public static final int PICTURE_JPEG = Workbook.PICTURE_TYPE_JPEG;
	public static final int PICTURE_PNG = Workbook.PICTURE_TYPE_PNG;
	public static final int PICTURE_DIB = Workbook.PICTURE_TYPE_DIB;

	public static final int SHEET_VISIBLE = Workbook.SHEET_STATE_VISIBLE;
	public static final int SHEET_HIDDEN = Workbook.SHEET_STATE_HIDDEN;
	public static final int SHEET_VERY_HIDDEN = Workbook.SHEET_STATE_VERY_HIDDEN;

	protected static final MissingCellPolicy POLICY = Row.CREATE_NULL_AS_BLANK;

	public static final MyWorkbook NOTHING = new MyWorkbook();

	private Workbook workbook;

	public MyWorkbook()
	{
		this(new XSSFWorkbook());
	}

	public MyWorkbook(Workbook workbook)
	{
		this.workbook = workbook;
		setMissingCellPolicy(POLICY);
	}

	public int addPicture(byte[] pictureData, int format)
	{
		return getWorkbook().addPicture(pictureData, format);
	}

	public int addPicture(DefaultFile picture)
	{
		return addPicture(picture, PICTURE_PNG);
	}

	public int addPicture(DefaultFile picture, int format)
	{
		return addPicture(picture.readBytes(), format);
	}

	public void addToolPack(UDFFinder toolpack)
	{
		getWorkbook().addToolPack(toolpack);
	}

	public Sheet cloneSheet(int sheet)
	{
		return getWorkbook().cloneSheet(sheet);
	}

	@Override
	public void close() throws IOException
	{
		getWorkbook().close();
	}

	public MyCellStyle createCellStyle()
	{
		return new MyCellStyle(getWorkbook().createCellStyle());
	}

	public DataFormat createDataFormat()
	{
		return getWorkbook().createDataFormat();
	}

	public Font createFont()
	{
		return getWorkbook().createFont();
	}

	public Name createName()
	{
		return getWorkbook().createName();
	}

	public MySheet createSheet()
	{
		return new MySheet(getWorkbook().createSheet());
	}

	public MySheet createSheet(String name)
	{
		return new MySheet(getWorkbook().createSheet(name));
	}

	public Font findFont(short boldWeight, short color, short fontHeight, String name, boolean italic, boolean strikeout, short typeOffset, byte underline)
	{
		return getWorkbook().findFont(boldWeight, color, fontHeight, name, italic, strikeout, typeOffset, underline);
	}

	public MySheet getActiveSheet()
	{
		return getSheet(getActiveSheetIndex());
	}

	public int getActiveSheetIndex()
	{
		return getWorkbook().getActiveSheetIndex();
	}

	public CellStyle getCellStyle(short style)
	{
		return getWorkbook().getCellStyleAt(style);
	}

	public CreationHelper getCreationHelper()
	{
		return getWorkbook().getCreationHelper();
	}

	public int getFirstVisibleTab()
	{
		return getWorkbook().getFirstVisibleTab();
	}

	public Font getFont(short font)
	{
		return getWorkbook().getFontAt(font);
	}

	public int getLastCellStyleIndex()
	{
		return getWorkbook().getNumCellStyles() - 1;
	}

	public int getLastFontIndex()
	{
		return getWorkbook().getNumberOfFonts() - 1;
	}

	public int getLastNameIndex()
	{
		return getWorkbook().getNumberOfNames() - 1;
	}

	public int getLastSheetIndex()
	{
		return getWorkbook().getNumberOfSheets() - 1;
	}

	public MissingCellPolicy getMissingCellPolicy()
	{
		return getWorkbook().getMissingCellPolicy();
	}

	public Name getName(int name)
	{
		return getWorkbook().getNameAt(name);
	}

	public Name getName(String name)
	{
		return getWorkbook().getName(name);
	}

	public int getNameIndex(String name)
	{
		return getWorkbook().getNameIndex(name);
	}

	public List<? extends PictureData> getPictures()
	{
		return getWorkbook().getAllPictures();
	}

	public String getPrintArea(int sheet)
	{
		return getWorkbook().getPrintArea(sheet);
	}

	public MySheet getSheet(int sheet)
	{
		return new MySheet(getWorkbook().getSheetAt(sheet));
	}

	public MySheet getSheet(String sheetName)
	{
		return new MySheet(getWorkbook().getSheet(sheetName));
	}

	public int getSheetIndex(MySheet sheet)
	{
		return getWorkbook().getSheetIndex(sheet.getSheet());
	}

	public int getSheetIndex(String sheetName)
	{
		return getWorkbook().getSheetIndex(sheetName);
	}

	public String getSheetName(int sheet)
	{
		return getWorkbook().getSheetName(sheet);
	}

	protected Workbook getWorkbook()
	{
		return workbook;
	}

	public boolean hasForcedFormulaRecalculation()
	{
		return getWorkbook().getForceFormulaRecalculation();
	}

	public boolean isHidden()
	{
		return getWorkbook().isHidden();
	}

	public boolean isSheetHidden(int sheet)
	{
		return getWorkbook().isSheetHidden(sheet);
	}

	public boolean isSheetVeryHidden(int sheet)
	{
		return getWorkbook().isSheetVeryHidden(sheet);
	}

	@Override
	public Iterator<MySheet> iterator()
	{
		MyArrayList<MySheet> sheets = new MyArrayList<>();
		for (Sheet sheet: getWorkbook())
		{
			sheets.add(new MySheet(sheet));
		}
		return sheets.iterator();
	}

	public int linkExternalWorkbook(String name, MyWorkbook workbook)
	{
		return getWorkbook().linkExternalWorkbook(name, workbook.getWorkbook());
	}

	public void removeName(int name)
	{
		getWorkbook().removeName(name);
	}

	public void removeName(String name)
	{
		getWorkbook().removeName(name);
	}

	public void removePrintArea(int sheet)
	{
		getWorkbook().removePrintArea(sheet);
	}

	public void removePrintArea(MySheet sheet)
	{
		removePrintArea(sheet.getIndex());
	}

	public void removeSheet(int sheet)
	{
		getWorkbook().removeSheetAt(sheet);
	}

	public void removeSheet(MySheet sheet)
	{
		removeSheet(sheet.getIndex());
	}

	public void setActiveSheet(int sheet)
	{
		getWorkbook().setActiveSheet(sheet);
	}

	public void setActiveSheet(MySheet sheet)
	{
		setActiveSheet(sheet.getIndex());
	}

	public void setFirstVisibleSheet(int sheet)
	{
		getWorkbook().setFirstVisibleTab(sheet);
	}

	public void setFirstVisibleSheet(MySheet sheet)
	{
		setFirstVisibleSheet(sheet.getIndex());
	}

	public void setForceFormulaRecalculation(boolean formulaRecalculation)
	{
		getWorkbook().setForceFormulaRecalculation(formulaRecalculation);
	}

	public void setHidden(boolean hidden)
	{
		getWorkbook().setHidden(hidden);
	}

	public void setMissingCellPolicy(MissingCellPolicy policy)
	{
		getWorkbook().setMissingCellPolicy(policy);
	}

	public void setPrintArea(int sheet, int startColumn, int endColumn, int startRow, int endRow)
	{
		getWorkbook().setPrintArea(sheet, startColumn, endColumn, startRow, endRow);
	}

	public void setPrintArea(int sheet, String reference)
	{
		getWorkbook().setPrintArea(sheet, reference);
	}

	public void setSelectedSheet(int sheet)
	{
		getWorkbook().setSelectedTab(sheet);
	}

	public void setSheetHidden(int sheet, boolean hidden)
	{
		getWorkbook().setSheetHidden(sheet, hidden);
	}

	public void setSheetHidden(int sheet, int hidden)
	{
		getWorkbook().setSheetHidden(sheet, hidden);
	}

	public void setSheetName(int sheet, String name)
	{
		getWorkbook().setSheetName(sheet, name);
	}

	public void setSheetOrder(String sheetName, int index)
	{
		getWorkbook().setSheetOrder(sheetName, index);
	}

	public void write(OutputStream stream) throws IOException
	{
		getWorkbook().write(stream);
	}
}
