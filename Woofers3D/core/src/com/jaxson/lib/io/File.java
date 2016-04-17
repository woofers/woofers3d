package com.jaxson.lib.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * A File that handles writing and reading.
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public interface File<F extends File>
{
	public static final String FOWARD_SLASH = "/";
	public static final String BACK_SLASH = "\\";
	public static final String NO_EXTENSION = "";
	public static final String DOT = Pattern.quote(".");
	public static final String NEXT_LINE = System.lineSeparator();

	public F append(String contents);

	public boolean canRead();

	public boolean canWrite();

	public F copy(F file);

	public F createDirectory();

	public F createFile();

	public F delete();

	public boolean equals(F file);

	/**
	 * Gets whether the {@link DefaultFile} exists.
	 * @return {@link boolean} - Whether the {@link DefaultFile} exists
	 */
	public boolean exists();

	/**
	 * Return a {@link BufferedReader} from the {@link DefaultFile}
	 * @return {@link BufferedReader} - The buffered reader
	 * @throws FileNotFoundException If the file is not found
	 */
	public BufferedReader getBufferedReader() throws FileNotFoundException;

	public F getChild(String child);

	/**
	 * Gets the file extension of the {@link DefaultFile}. Returns an empty
	 * string if
	 * the {@link DefaultFile} has no extension.
	 * @return {@link String} - The file extension
	 */
	public String getExtension();

	public FileType getExtensionType();

	public FileInputStream getFileInputStream() throws FileNotFoundException;

	public FileOutputStream getFileOutputStream() throws FileNotFoundException, SecurityException;

	public FileReader getFileReader() throws FileNotFoundException;

	/**
	 * Gets the {@link java.io.File} of the {@link DefaultFile}.
	 * @return {@link java.io.File} - The file
	 */
	public java.io.File getJavaFile();

	/**
	 * Gets the file name of the {@link DefaultFile}.
	 * @return {@link String} - The file name
	 */
	public String getName();

	public F getParent();

	public String getParentPath();

	/**
	 * Gets the file path of the {@link DefaultFile}.
	 * @return {@link String} - The file path
	 */
	public String getPath();

	public PrintWriter getPrintWriter() throws FileNotFoundException, UnsupportedEncodingException;

	public Date getWhenLastModified();

	/**
	 * Gets whether the {@link DefaultFile} is a directory.
	 * @return {@link boolean} - Whether the {@link DefaultFile} is a directory
	 */
	public boolean isDirectory();

	/**
	 * Gets whether the {@link DefaultFile} is a file.
	 * @return {@link boolean} - Whether the {@link DefaultFile} is a file
	 */
	public boolean isFile();

	public long length();

	public F move(F file);

	/**
	 * Parses a the {@link DefaultFile} as a {@link byte} array.
	 * @return {@link byte[]} - The contents of the file as a {@link byte} array
	 */
	public byte[] readBytes();

	/**
	 * Parses a the {@link DefaultFile} as a {@link String}.
	 * @return {@link String} - The contents of the file
	 */
	public String readString();

	public F rename(String path);

	public F setPath(String path);

	/**
	 * Writes to the {@link DefaultFile}.
	 * @param contents The contents to write as {@link byte}s
	 */
	public F write(byte[] contents);

	/**
	 * Writes to the {@link DefaultFile}.
	 * @param contents The contents to write as a {@link String}
	 */
	public F write(String contents);
}
