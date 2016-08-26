package com.jaxson.lib.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * A File that handles writing and reading.
 * @param <T> the implementing {@link Object} for chaining
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public interface File<F extends File>
{
	public FileType getType()

	public GdxFile setFileType(FileType fileType)
}
