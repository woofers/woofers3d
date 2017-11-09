package com.jaxson.lib.gdx.io;

import com.badlogic.gdx.graphics.Texture;

public class TextureFromFile extends FromFile<Texture>
{
	public TextureFromFile(GdxFile file)
	{
		super(file);
	}

	@Override
	public Texture unwrap()
	{
		return new Texture(file().getFileHandle());
	}
}
