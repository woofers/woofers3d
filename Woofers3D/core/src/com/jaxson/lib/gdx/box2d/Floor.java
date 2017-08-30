package com.jaxson.lib.gdx.box2d;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.jaxson.lib.gdx.box2d.bodies.types.SpriteBody;

public class Floor extends SpriteBody
{
	public Floor(float x, float y)
	{
		super(getTex(), BodyDef.BodyType.StaticBody, 1f);
		setLocation(x, y);
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	public static Texture getTex()
	{
		Pixmap pixmap = new Pixmap(256, 32, Pixmap.Format.RGBA8888);
		pixmap.setColor(1f, 1f, 1f, 1f);
		pixmap.fillRectangle(0, 0, 512, 64);
		return new Texture(pixmap);
	}
}
