package com.jaxson.woofers3d;

import com.jaxson.lib.io.DataFile;
import com.jaxson.lib.util.MyArrayList;
import com.jaxson.woofers3d.states.PlayState;
import com.jaxson.woofers3d.states.FlatState;
import com.jaxson.lib.gdx.GameInstance;

public class Woofers3D extends GameInstance
{
	private static final String TITLE = "Woofers 3D";

	public Woofers3D()
	{
		super();
		config().setTitle(TITLE);
		saveableConfig().save();

		//DataFile listFile = new DataFile("P:/Users/Jaxson/Reinstall Stuff/music2.txt");
		//MyArrayList<String> list = new MyArrayList<>(listFile.readString().split("\n"));
		//list.remove(0);
		//for (String song: list)
		//{
		//	DataFile file = new DataFile("P:/Music/Music Library/" + song);
		//	DataFile dest = new DataFile("D:/Music/" + song);
		//	dest.createDirectory();
		//	file.copy(dest);
		//	System.out.println(file);
		//}
		//System.out.println("Done");
	}

	@Override
	public void create()
	{
		super.create();
		pushState(new PlayState(game()));
	}
}
