package com.jaxson.lib.gdx.io;

import com.badlogic.gdx.graphics.g3d.Model;

public class ModelFromFile extends FromFile<Model>
{
	public ModelFromFile(GdxFile file)
	{
		super(file);
	}

	@Override
	public Model unwrap()
	{
		return file().readObject();
	}
}
