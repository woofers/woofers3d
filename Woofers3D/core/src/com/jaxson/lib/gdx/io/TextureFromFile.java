package com.jaxson.lib.gdx.io;

import com.badlogic.gdx.graphics.Texture;
import com.jaxson.lib.util.Unwrapable;

public class TextureFromFile implements Unwrapable<Texture>
{
	private GdxFile file;

	public TextureFromFile(GdxFile file)
	{
		this.file = file;
	}

	@Override
	public Texture unwrap()
	{
		return new Texture(file.getFileHandle());
	}
}
