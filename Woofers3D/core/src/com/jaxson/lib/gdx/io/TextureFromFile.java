package com.jaxson.lib.gdx.io;

import com.jaxson.lib.util.Unwrapable;
import com.badlogic.gdx.graphics.Texture;

public class TextureFromFile implements Unwrapable<Texture>
{
	private GdxFile file;

	public TextureFromFile(GdxFile file)
	{
		this.file = file;
	}

	public Texture unwrap()
	{
		return new Texture(file.getFileHandle());
	}
}
