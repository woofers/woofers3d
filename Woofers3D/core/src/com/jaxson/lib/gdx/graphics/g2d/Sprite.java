package com.jaxson.lib.gdx.graphics.g2d;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.jaxson.lib.gdx.graphics.GameObject;

public abstract class Sprite extends GameObject
{
	private Texture texture;

	public Sprite(FileHandle file)
	{
		this.texture = new Texture(file);
	}

	@Override
	public abstract void dispose();

	public abstract Vector2 getLocation();

	public Texture getTexture()
	{
		return texture;
	}

	@Override
	public abstract void update(float dt);
}
