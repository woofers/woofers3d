package com.jaxson.lib.io.excel.workbook;

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
import com.jaxson.lib.io.DataFile;
import com.jaxson.lib.util.MyArrayList;

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
    public static final int SHEET_VERY_HIDDEN
            = Workbook.SHEET_STATE_VERY_HIDDEN;

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

    public MySheet activeSheet()
    {
        return sheet(activeSheetIndex());
    }

    public int activeSheetIndex()
    {
        return workbook().getActiveSheetIndex();
    }

    public int addPicture(byte[] pictureData, int format)
    {
        return workbook().addPicture(pictureData, format);
    }

    public int addPicture(DataFile picture, int format)
    {
        return addPicture(picture.readBytes(), format);
    }

    public void addToolPack(UDFFinder toolpack)
    {
        workbook().addToolPack(toolpack);
    }

    public CellStyle cellStyle(short style)
    {
        return workbook().getCellStyleAt(style);
    }

    public Sheet cloneSheet(int sheet)
    {
        return workbook().cloneSheet(sheet);
    }

    @Override
    public void close() throws IOException
    {
        workbook().close();
    }

    public MyCellStyle createCellStyle()
    {
        return new MyCellStyle(workbook().createCellStyle());
    }

    public DataFormat createDataFormat()
    {
        return workbook().createDataFormat();
    }

    public Font createFont()
    {
        return workbook().createFont();
    }

    public Name createName()
    {
        return workbook().createName();
    }

    public MySheet createSheet()
    {
        return new MySheet(workbook().createSheet());
    }

    public MySheet createSheet(String name)
    {
        return new MySheet(workbook().createSheet(name));
    }

    public CreationHelper creationHelper()
    {
        return workbook().getCreationHelper();
    }

    public Font findFont(short boldWeight,
            short color,
            short fontHeight,
            String name,
            boolean italic,
            boolean strikeout,
            short typeOffset,
            byte underline)
    {
        return workbook().findFont(
                boldWeight, color, fontHeight, name,
                italic, strikeout, typeOffset, underline);
    }

    public int firstVisibleTab()
    {
        return workbook().getFirstVisibleTab();
    }

    public Font font(short font)
    {
        return workbook().getFontAt(font);
    }

    public boolean hasForcedFormulaRecalculation()
    {
        return workbook().getForceFormulaRecalculation();
    }

    public boolean isHidden()
    {
        return workbook().isHidden();
    }

    public boolean isSheetHidden(int sheet)
    {
        return workbook().isSheetHidden(sheet);
    }

    public boolean isSheetVeryHidden(int sheet)
    {
        return workbook().isSheetVeryHidden(sheet);
    }

    @Override
    public Iterator<MySheet> iterator()
    {
        MyArrayList<MySheet> sheets = new MyArrayList<>();
        int i = 0;
        Sheet sheet = null;
        do
        {
            sheets.add(sheet(i));
            i ++;
        }
        while (sheet != null);
        return sheets.iterator();
    }

    public int lastCellStyleIndex()
    {
        return workbook().getNumCellStyles() - 1;
    }

    public int lastFontIndex()
    {
        return workbook().getNumberOfFonts() - 1;
    }

    public int lastNameIndex()
    {
        return workbook().getNumberOfNames() - 1;
    }

    public int lastSheetIndex()
    {
        return workbook().getNumberOfSheets() - 1;
    }

    public int linkExternalWorkbook(String name, MyWorkbook workbook)
    {
        return workbook().linkExternalWorkbook(name, workbook.workbook());
    }

    public MissingCellPolicy missingCellPolicy()
    {
        return workbook().getMissingCellPolicy();
    }

    public Name name(int name)
    {
        return workbook().getNameAt(name);
    }

    public Name name(String name)
    {
        return workbook().getName(name);
    }

    public int nameIndex(String name)
    {
        return workbook().getNameIndex(name);
    }

    public List<? extends PictureData> pictures()
    {
        return workbook().getAllPictures();
    }

    public String printArea(int sheet)
    {
        return workbook().getPrintArea(sheet);
    }

    public void removeName(int name)
    {
        workbook().removeName(name);
    }

    public void removeName(String name)
    {
        workbook().removeName(name);
    }

    public void removePrintArea(int sheet)
    {
        workbook().removePrintArea(sheet);
    }

    public void removePrintArea(MySheet sheet)
    {
        removePrintArea(sheet.index());
    }

    public void removeSheet(int sheet)
    {
        workbook().removeSheetAt(sheet);
    }

    public void removeSheet(MySheet sheet)
    {
        removeSheet(sheet.index());
    }

    public void setActiveSheet(int sheet)
    {
        workbook().setActiveSheet(sheet);
    }

    public void setActiveSheet(MySheet sheet)
    {
        setActiveSheet(sheet.index());
    }

    public void setFirstVisibleSheet(int sheet)
    {
        workbook().setFirstVisibleTab(sheet);
    }

    public void setFirstVisibleSheet(MySheet sheet)
    {
        setFirstVisibleSheet(sheet.index());
    }

    public void setForceFormulaRecalculation(boolean formulaRecalculation)
    {
        workbook().setForceFormulaRecalculation(formulaRecalculation);
    }

    public void setHidden(boolean hidden)
    {
        workbook().setHidden(hidden);
    }

    public void setMissingCellPolicy(MissingCellPolicy policy)
    {
        workbook().setMissingCellPolicy(policy);
    }

    public void setPrintArea(int sheet,
            int startColumn,
            int endColumn,
            int startRow,
            int endRow)
    {
        workbook().setPrintArea(
                sheet, startColumn, endColumn, startRow,
                endRow);
    }

    public void setPrintArea(int sheet, String reference)
    {
        workbook().setPrintArea(sheet, reference);
    }

    public void setSelectedSheet(int sheet)
    {
        workbook().setSelectedTab(sheet);
    }

    public void setSheetHidden(int sheet, boolean hidden)
    {
        workbook().setSheetHidden(sheet, hidden);
    }

    public void setSheetHidden(int sheet, int hidden)
    {
        workbook().setSheetHidden(sheet, hidden);
    }

    public void setSheetName(int sheet, String name)
    {
        workbook().setSheetName(sheet, name);
    }

    public void setSheetOrder(String sheetName, int index)
    {
        workbook().setSheetOrder(sheetName, index);
    }

    public MySheet sheet(int sheet)
    {
        return new MySheet(workbook().getSheetAt(sheet));
    }

    public MySheet sheet(String sheetName)
    {
        return new MySheet(workbook().getSheet(sheetName));
    }

    public int sheetIndex(MySheet sheet)
    {
        return workbook().getSheetIndex(sheet.sheet());
    }

    public int sheetIndex(String sheetName)
    {
        return workbook().getSheetIndex(sheetName);
    }

    public String sheetName(int sheet)
    {
        return workbook().getSheetName(sheet);
    }

    protected Workbook workbook()
    {
        return workbook;
    }

    public void write(OutputStream stream) throws IOException
    {
        workbook().write(stream);
    }
}
