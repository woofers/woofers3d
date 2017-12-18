package com.jaxson.lib.gdx.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
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
import com.jaxson.lib.io.DataFile;
import com.jaxson.lib.io.File;
import com.jaxson.lib.io.FileExtension;

public class GdxFile implements File<GdxFile, Model, Pixmap>
{
	public static final GdxFile NOTHING = new GdxFile(DataFile.NOTHING);

	private static final String LOADER_NOT_FOUND
			= "Loader could not be found for given filetype.";

	private final File file;
	private final FileHandle fileHandle;

	public GdxFile(File file)
	{
		this(file, FileType.Internal);
	}

	public GdxFile(File file, FileType fileType)
	{
		this.file = file;
		this.fileHandle = getFileHandle(fileType);
	}

	public GdxFile(String path)
	{
		this(new DataFile(path));
	}

	public GdxFile(String path, FileType fileType)
	{
		this(new DataFile(path), fileType);
	}

	@Override
	public GdxFile append(String contents)
	{
		return write(contents, false);
	}

	@Override
	public BufferedReader bufferedReader() throws FileNotFoundException
	{
		return getFile().bufferedReader();
	}

	@Override
	public boolean canRead()
	{
		return javaFile().canRead();
	}

	@Override
	public boolean canWrite()
	{
		return javaFile().canWrite();
	}

	@Override
	public GdxFile child(String child)
	{
		return new GdxFile(getFile().child(child), getType());
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

	private boolean equals(FileType fileType)
	{
		return fileType == getType();
	}

	@Override
	public boolean equals(GdxFile file)
	{
		return equals(file.path()) && equals(file.getType());
	}

	@Override
	public boolean equals(Object file)
	{
		if (file instanceof GdxFile) return equals((GdxFile) file);
		if (file instanceof File) return equals(file);
		return false;
	}

	@Override
	public boolean exists()
	{
		return getFileHandle().exists();
	}

	@Override
	public String extension()
	{
		return getFile().extension();
	}

	@Override
	public com.jaxson.lib.io.FileExtension fileExtension()
	{
		return getFile().fileExtension();
	}

	@Override
	public FileInputStream fileInputStream() throws FileNotFoundException
	{
		return getFile().fileInputStream();
	}

	@Override
	public
		FileOutputStream
			fileOutputStream() throws FileNotFoundException, SecurityException
	{
		return getFile().fileOutputStream();
	}

	@Override
	public FileReader fileReader() throws FileNotFoundException
	{
		return getFile().fileReader();
	}

	private FileHandle getAbsoluteFile()
	{
		return getFiles().absolute(path());
	}

	private FileHandle getClasspathFile()
	{
		return getFiles().classpath(path());
	}

	private FileHandle getExternalFile()
	{
		return getFiles().external(path());
	}

	private File getFile()
	{
		return file;
	}

	public FileHandle getFileHandle()
	{
		return fileHandle;
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
		return getFiles().internal(path());
	}

	private FileHandle getLocalFile()
	{
		return getFiles().local(path());
	}

	public FileType getType()
	{
		return getFileHandle().type();
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
	public java.io.File javaFile()
	{
		return getFileHandle().file();
	}

	@Override
	public Date lastModified()
	{
		return getFile().lastModified();
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
	public String name()
	{
		return getFile().name();
	}

	@Override
	public String nameWithoutExtension()
	{
		return getFile().nameWithoutExtension();
	}

	@Override
	public GdxFile parent()
	{
		return new GdxFile(getFile().parent(), getType());
	}

	@Override
	public String parentPath()
	{
		return getFile().parentPath();
	}

	@Override
	public String path()
	{
		return getFile().path();
	}

	@Override
	public
		PrintWriter
			printWriter()
					throws FileNotFoundException, UnsupportedEncodingException
	{
		return getFile().printWriter();
	}

	@Override
	public byte[] readBytes()
	{
		return getFileHandle().readBytes();
	}

	private Model readG3db()
	{
		return new G3dModelLoader(
				new UBJsonReader()).loadModel(getFileHandle());
	}

	private Model readG3dj()
	{
		return new G3dModelLoader(new JsonReader()).loadModel(getFileHandle());
	}

	private Model readObj()
	{
		return new ObjLoader().loadModel(getFileHandle());
	}

	@Override
	public Model readObject()
	{
		FileExtension extension = fileExtension();
		if (extension.equals(FileExtension.G3DB)) return readG3db();
		if (extension.equals(FileExtension.G3DJ)) return readG3dj();
		if (extension.equals(FileExtension.OBJ)) return readObj();
		throw new IllegalArgumentException(LOADER_NOT_FOUND);
	}

	@Override
	public String readString()
	{
		return getFileHandle().readString();
	}

	private GdxFile rename(GdxFile file)
	{
		if (equals(file)) return this;
		GdxFile copy = copy(file);
		if (copy.equals(GdxFile.NOTHING)) return this;
		return copy;
	}

	@Override
	public GdxFile rename(String name)
	{
		return rename(new GdxFile(name, getType()));
	}

	@Override
	public GdxFile setExtension(com.jaxson.lib.io.FileExtension extension)
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
		return new GdxFile(path(), fileType);
	}

	@Override
	public GdxFile setPath(String path)
	{
		return new GdxFile(path, getType());
	}

	@Override
	public long size()
	{
		return getFileHandle().length();
	}

	@Override
	public String toString()
	{
		return getFile().toString();
	}

	@Override
	public Model unwrap()
	{
		return readObject();
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
		return write(screenshot.pixmap());
	}

	@Override
	public GdxFile write(String contents)
	{
		return write(contents, true);
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
