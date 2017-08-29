package com.jaxson.lib.gdx.box2d;

import com.jaxson.lib.gdx.bullet.simulation.bodies.types.CameraPlayerBody;
import com.jaxson.lib.gdx.graphics.views.TargetCamera;
import com.jaxson.lib.gdx.input.KeyboardKey;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.graphics.g2d.SpriteActor;
import com.jaxson.lib.gdx.input.Keyboard;
import com.jaxson.lib.gdx.input.Inputs;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;
import com.jaxson.lib.gdx.io.TextureFromFile;
import com.jaxson.lib.gdx.io.GdxFile;
import com.jaxson.lib.box2d.bodies.types.SpriteBody;
import com.badlogic.gdx.graphics.Pixmap;

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
		Pixmap pixmap = new Pixmap(512, 64, Pixmap.Format.RGBA8888);
		pixmap.setColor(1f, 1f, 1f, 1f);
		pixmap.fillRectangle(0, 0, 512, 64);
		return new Texture(pixmap);
	}
}
