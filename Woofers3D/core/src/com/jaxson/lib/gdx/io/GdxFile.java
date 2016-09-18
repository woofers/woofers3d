package com.jaxson.lib.gdx.io;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.UBJsonReader;
import com.jaxson.lib.gdx.graphics.g2d.Screenshot;
import com.jaxson.lib.io.DefaultFile;
import com.jaxson.lib.io.File;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;

public class GdxFile implements File<GdxFile, Model, Pixmap>
{
	private static final String G3DJ_EXTENSION = "g3dj";
	private static final String G3DB_EXTENSION = "g3db";
	private static final String OBJ_EXTENSION = "obj";
	private static final String LOADER_NOT_FOUND
			= "Loader could not be found for given filetype.";

	public static final GdxFile NOTHING = new GdxFile(DefaultFile.NOTHING);

	private final File file;
	private final FileHandle fileHandle;

	public GdxFile(File file)
	{
		this(file, FileType.Absolute);
	}

	public GdxFile(File file, FileType fileType)
	{
		this.file = file;
		this.fileHandle = getFileHandle(fileType);
	}

	public GdxFile(String path)
	{
		this(new DefaultFile(path));
	}

	public GdxFile(String path, FileType fileType)
	{
		this(new DefaultFile(path), fileType);
	}

	@Override
	public GdxFile append(String contents)
	{
		return write(contents, false);
	}

	@Override
	public boolean canRead()
	{
		return getJavaFile().canRead();
	}

	@Override
	public boolean canWrite()
	{
		return getJavaFile().canWrite();
	}

	@Override
	public GdxFile copy(GdxFile file)
	{
		if (equals(file)) return this;
		try
		{
			getFileHandle().copyTo(file.getFileHandle());
		}
		catch (Exception ex)
		{
			return GdxFile.NOTHING;
		}
		return file;
	}

	@Override
	public GdxFile createDirectory()
	{
		if (exists() && isDirectory()) return this;
		if (getType() == FileType.Classpath || getType() == FileType.Internal)
			return GdxFile.NOTHING;
		return new GdxFile(getFile().createDirectory(), getType());
	}

	@Override
	public GdxFile createFile()
	{
		if (exists())
		{
			if (isFile()) return this;
			delete();
		}
		return write("");
	}

	@Override
	public GdxFile delete()
	{
		if (!exists()) return GdxFile.NOTHING;
		try
		{
			if (!getFileHandle().deleteDirectory()) return this;
		}
		catch (Exception ex)
		{
			return this;
		}
		return GdxFile.NOTHING;
	}

	@Override
	public boolean equals(File file)
	{
		return equals(file.getPath());
	}

	public boolean equals(GdxFile file)
	{
		return equals(file.getPath()) && equals(file.getType());
	}

	@Override
	public boolean equals(Object file)
	{
		if (file instanceof GdxFile) return equals((GdxFile) file);
		if (file instanceof File) return equals((File) file);
		return false;
	}

	@Override
	public boolean exists()
	{
		return getFileHandle().exists();
	}

	@Override
	public BufferedReader getBufferedReader() throws FileNotFoundException
	{
		return getFile().getBufferedReader();
	}

	@Override
	public GdxFile getChild(String child)
	{
		return new GdxFile(getFile().getChild(child), getType());
	}

	@Override
	public String getExtension()
	{
		return getFile().getExtension();
	}

	@Override
	public com.jaxson.lib.io.FileType getExtensionType()
	{
		return getFile().getExtensionType();
	}

	public FileHandle getFileHandle()
	{
		return fileHandle;
	}

	@Override
	public FileInputStream getFileInputStream()
			throws FileNotFoundException
	{
		return getFile().getFileInputStream();
	}

	@Override
	public FileOutputStream getFileOutputStream()
			throws FileNotFoundException,
				   SecurityException
	{
		return getFile().getFileOutputStream();
	}

	@Override
	public FileReader getFileReader()
			throws FileNotFoundException
	{
		return getFile().getFileReader();
	}

	@Override
	public java.io.File getJavaFile()
	{
		return getFileHandle().file();
	}

	@Override
	public String getName()
	{
		return getFile().getName();
	}

	@Override
	public String getNameWithoutExtension()
	{
		return getFile().getNameWithoutExtension();
	}

	@Override
	public GdxFile getParent()
	{
		return new GdxFile(getFile().getParent(), getType());
	}

	@Override
	public String getParentPath()
	{
		return getFile().getParentPath();
	}

	@Override
	public String getPath()
	{
		return getFile().getPath();
	}

	@Override
	public PrintWriter getPrintWriter() throws FileNotFoundException,
	UnsupportedEncodingException
	{
		return getFile().getPrintWriter();
	}

	public FileType getType()
	{
		return getFileHandle().type();
	}

	@Override
	public Date getWhenLastModified()
	{
		return getFile().getWhenLastModified();
	}

	@Override
	public boolean isDirectory()
	{
		return getFileHandle().isDirectory();
	}

	@Override
	public boolean isFile()
	{
		return getFile().isFile();
	}

	@Override
	public long length()
	{
		return getFileHandle().length();
	}

	@Override
	public GdxFile move(GdxFile file)
	{
		GdxFile copy = copy(file);
		if (copy.equals(GdxFile.NOTHING)) return this;
		delete();
		return copy;
	}

	@Override
	public byte[] readBytes()
	{
		return getFileHandle().readBytes();
	}

	@Override
	public Model readObject()
	{
		String extension = getExtension();
		if (extension.equals(G3DB_EXTENSION)) return readG3db();
		if (extension.equals(G3DJ_EXTENSION)) return readG3dj();
		if (extension.equals(OBJ_EXTENSION)) return readObj();
		throw new IllegalArgumentException(LOADER_NOT_FOUND);
	}

	@Override
	public String readString()
	{
		return getFileHandle().readString();
	}

	@Override
	public GdxFile rename(String name)
	{
		return rename(new GdxFile(name, getType()));
	}

	@Override
	public GdxFile setExtension(com.jaxson.lib.io.FileType extension)
	{
		return new GdxFile(getFile().setExtension(extension), getType());
	}

	@Override
	public GdxFile setExtension(String extension)
	{
		return new GdxFile(getFile().setExtension(extension), getType());
	}

	public GdxFile setFileType(FileType fileType)
	{
		return new GdxFile(getPath(), fileType);
	}

	@Override
	public GdxFile setPath(String path)
	{
		return new GdxFile(path, getType());
	}

	@Override
	public GdxFile write()
	{
		return write("");
	}

	@Override
	public GdxFile write(byte[] contents)
	{
		return new GdxFile(getFile().write(contents), getType());
	}

	@Override
	public GdxFile write(Pixmap pixmap)
	{
		PixmapIO.writePNG(getFileHandle(), pixmap);
		return this;
	}

	public GdxFile write(Screenshot screenshot)
	{
		return write(screenshot.getPixmap());
	}

	@Override
	public GdxFile write(String contents)
	{
		return write(contents, true);
	}

	private boolean equals(FileType fileType)
	{
		return fileType == getType();
	}

	private FileHandle getAbsoluteFile()
	{
		return getFiles().absolute(getPath());
	}

	private FileHandle getClasspathFile()
	{
		return getFiles().classpath(getPath());
	}

	private FileHandle getExternalFile()
	{
		return getFiles().external(getPath());
	}

	private File getFile()
	{
		return file;
	}

	private FileHandle getFileHandle(FileType fileType)
	{
		switch (fileType)
		{
			case Absolute:
				return getAbsoluteFile();
			case Classpath:
				return getClasspathFile();
			case External:
				return getExternalFile();
			case Internal:
				return getInternalFile();
		}
		return getLocalFile();
	}

	private FileHandle getInternalFile()
	{
		return getFiles().internal(getPath());
	}

	private FileHandle getLocalFile()
	{
		return getFiles().local(getPath());
	}

	private Model readG3db()
	{
		return new G3dModelLoader(new UBJsonReader()).loadModel(
		getFileHandle());
	}

	private Model readG3dj()
	{
		return new G3dModelLoader(new JsonReader()).loadModel(getFileHandle());
	}

	private Model readObj()
	{
		return new ObjLoader().loadModel(getFileHandle());
	}

	private GdxFile rename(GdxFile file)
	{
		if (equals(file)) return this;
		GdxFile copy = copy(file);
		if (copy.equals(GdxFile.NOTHING)) return this;
		return copy;
	}

	private GdxFile write(String contents, boolean overwrite)
	{
		try
		{
			getFileHandle().writeString(contents, !overwrite);
		}
		catch (Exception ex)
		{
			return GdxFile.NOTHING;
		}
		return this;
	}

	private static Files getFiles()
	{
		return Gdx.files;
	}
}
