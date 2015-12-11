package com.jaxson.lib.gdx.graphics.g2d;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public abstract class Sprite extends Texture
{
	public Sprite(FileHandle file)
	{
		super(file);
	}

	public abstract void dispose();

	public abstract Vector2 getLocation();

	public abstract void update(float dt);
}
