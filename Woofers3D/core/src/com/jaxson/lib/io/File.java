package com.jaxson.lib.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import com.jaxson.lib.util.Unwrapable;

/**
 * A File that handles writing and reading.
 * @param <F> the implementing {@link Object} for chaining
 * @param <R> the {@link Object} to be read
 * @param <W> the {@link Object} to write
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public interface File<F extends File, R, W> extends Unwrapable<R>
{
	public static final String FOWARD_SLASH = "/";
	public static final String BACK_SLASH = "\\";
	public static final String NO_EXTENSION = "";
	public static final String NEXT_LINE = System.lineSeparator();

	/**
	 * Appends string to the {@link File}.
	 * @param contents The contents to append
	 * @return {@link F} The file
	 */
	public F append(String contents);

	/**
	 * Gets whether the {@link File} can be read.
	 * @return {@link boolean} - Whether the {@link File} can be read
	 */
	public boolean canRead();

	/**
	 * Gets whether the {@link File} can be written to.
	 * @return {@link boolean} - Whether the {@link File} can be written to
	 */
	public boolean canWrite();

	/**
	 * Copies the file to another {@link File}.
	 * @param file The new location
	 * @return {@link F} - The {@link File} that was created.
	 * If the copy failed the original location is returned.
	 */
	public F copy(F file);

	/**
	 * Creates a directory in the {@link File} location.
	 * @return {@link F} - The {@link File} where the directory was created.
	 * If no directory was created an empty {@link File} is returned.
	 */
	public F createDirectory();

	/**
	 * Creates a file in the {@link File} location.
	 * @return {@link F} - The {@link File} where the file was created.
	 * If no file was created an empty {@link File} is returned
	 */
	public F createFile();

	/**
	 * Deletes the {@link File} from the disk.
	 * @return {@link F} - On success an empty {@link File} is returned.
	 * Otherwise the original {@link File} is returned
	 */
	public F delete();

	public boolean equals(F file);

	/**
	 * Gets whether the {@link File} exists.
	 * @return {@link boolean} - Whether the {@link File} exists
	 */
	public boolean exists();

	/**
	 * Return a {@link BufferedReader} from the {@link File}
	 * @return {@link BufferedReader} - The buffered reader
	 * @throws FileNotFoundException If the file is not found
	 */
	public BufferedReader bufferedReader()
			throws FileNotFoundException;

	/**
	 * Returns a child of the {@link File} if it exists.
	 * @param child The Child
	 * @return {@link F} - The child
	 */
	public F child(String child);

	/**
	 * Gets the file extension of the {@link File}. Returns an empty
	 * string if the {@link File} has no extension.
	 * @return {@link String} - The file extension
	 */
	public String extension();

	/**
	 * Gets the file extension of the {@link File} as a {@link FileExtension}.
	 * @return {@link FileExtension} - The file extension
	 */
	public FileExtension fileExtension();

	/**
	 * Returns a {@link FileInputStream} from the {@link File}
	 * @return {@link FileInputStream} - The file input stream
	 * @throws FileNotFoundException If the file is not found
	 */
	public FileInputStream fileInputStream()
			throws FileNotFoundException;

	/**
	 * Returns a {@link FileOutputStream} from the {@link File}
	 * @return {@link FileOutputStream} - The file output stream
	 * @throws FileNotFoundException If the file is not found
	 * @throws SecurityException If the access to the file denied
	 */
	public FileOutputStream fileOutputStream()
			throws FileNotFoundException,
				   SecurityException;

	/**
	 * Returns a {@link FileInputStream} from the {@link File}
	 * @return {@link FileInputStream} - The file input stream
	 * @throws FileNotFoundException If the file is not found
	 */
	public FileReader fileReader()
			throws FileNotFoundException;

	/**
	 * Gets the {@link java.io.File} of the {@link File}.
	 * @return {@link java.io.File} - The file
	 */
	public java.io.File javaFile();

	/**
	 * Gets the file name of the {@link File}.
	 * @return {@link String} - The file name
	 */
	public String name();

	/**
	 * Gets the file name of the {@link File} without the type.
	 * @return {@link String} - The file name
	 */
	public String nameWithoutExtension();

	/**
	 * Gets the parent directory of the {@link File}.
	 * @return {@link F} - The parent directory
	 */
	public F parent();

	/**
	 * Gets the parent directory's path of the {@link File}.
	 * @return {@link String} - The parent directory's path
	 */
	public String parentPath();

	/**
	 * Gets the file path of the {@link File}.
	 * @return {@link String} - The file path
	 */
	public String path();

	/**
	 * Returns a {@link PrintWriter} from the {@link File}
	 * @return {@link PrintWriter} - The print writer
	 * @throws FileNotFoundException If the file is not found
	 * @throws UnsupportedEncodingException If the charset is not supported
	 */
	public PrintWriter printWriter()
			throws FileNotFoundException,
				   UnsupportedEncodingException;

	/**
	 * Gets when the {@link File} was last changed.
	 * @return {@link Date} - When the {@link File} was last changed
	 */
	public Date lastModified();

	/**
	 * Gets whether the {@link File} is a directory.
	 * @return {@link boolean} - Whether the {@link File} is a directory
	 */
	public boolean isDirectory();

	/**
	 * Gets whether the {@link File} is a file.
	 * @return {@link boolean} - Whether the {@link File} is a file
	 */
	public boolean isFile();

	/**
	 * Gets the size of {@link File}'s abstract path in bytes.
	 * @return {@link long} - The size of {@link File}'s abstract path in
	 * bytes
	 */
	public long size();

	/**
	 * Moves the {@link File} to another location.
	 * @param file The new location
	 * @return {@link F} - The {@link File} in its new location.
	 * If the move failed the original location is returned.
	 */
	public F move(F file);

	/**
	 * Parses a the {@link File} as a {@link byte} array.
	 * @return {@link byte[]} - The contents of the file as a {@link byte} array
	 */
	public byte[] readBytes();

	/**
	 * Reads the {@link File} as a {@link R}.
	 * @return {@link R} - The {@link Object} read.
	 */
	public R readObject();

	/**
	 * Unwraps the content of the {@link File}.
	 * @return {@link R} - The content to unwrap.
	 */
	public R unwrap();

	/**
	 * Parses a the {@link File} as a {@link String}.
	 * @return {@link String} - The contents of the file
	 */
	public String readString();

	/**
	 * Renames the {@link File}.
	 * @param name The new name
	 * @return {@link F} - The {@link File} with its new name.
	 * If the rename failed the original is returned
	 */
	public F rename(String name);

	/**
	 * Changes the {@link FileExtension} extension of the {@link File}.
	 * @param extension The new fileType
	 * @return {@link F} - The {@link File} with its changed extension.
	 */
	public F setExtension(FileExtension extension);

	/**
	 * Changes the extension of the {@link File}.
	 * @param extension The new extension
	 * @return {@link F} - The {@link File} with its changed extension.
	 */
	public F setExtension(String extension);

	/**
	 * Changes the path of the {@link File} representation.
	 * @param path The new path
	 * @return {@link F} - The {@link File} with its new path.
	 */
	public F setPath(String path);

	/**
	 * Writes a blank document to the {@link File}.
	 * @return {@link F} The file
	 */
	public F write();

	/**
	 * Writes to the {@link File}.
	 * @param contents The contents to write as {@link byte}s
	 * @return {@link F} The file
	 */
	public F write(byte[] contents);

	/**
	 * Writes to the {@link File}.
	 * @param contents The contents to write as a {@link String}
	 * @return {@link F} The file
	 */
	public F write(String contents);

	/**
	 * Writes a {@link Object} to the {@link File}.
	 * @param object The object
	 * @return {@link F} The file
	 */
	public F write(W object);
}
