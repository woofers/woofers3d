package com.jaxson.lib.util;

import com.jaxson.lib.io.DataFile;
import com.jaxson.lib.util.MyArrayList;

public class MusicCopy
{
	public MusicCopy()
	{
		DataFile listFile = new DataFile("P:/Users/Jaxson/Reinstall Stuff/music2.txt");
		MyArrayList<String> list = new MyArrayList<>(listFile.readString().split("\n"));
		list.remove(0);
		for (String song: list)
		{
			DataFile file = new DataFile("P:/Music/Music Library/" + song);
			DataFile dest = new DataFile("D:/Music/" + song);
			dest.createDirectory();
			file.copy(dest);
			System.out.println(file);
		}
		System.out.println("Done");
	}
}