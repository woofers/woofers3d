package com.jaxson.lib.gdx.bullet.bodies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.jaxson.lib.gdx.bullet.collision.BoxShape;
import com.jaxson.lib.gdx.graphics.g3d.util.MyModelBuilder;

public class RigidBox extends RigidBody
{
	public RigidBox()
	{
		this(DEFAULT_COLOR);
	}

	public RigidBox(Color color)
	{
		super(getModel(color), getShape());
	}

	protected static Model getModel(Color color)
	{
		return new MyModelBuilder().createBox(color);
	}

	protected static BoxShape getShape()
	{
		return new BoxShape();
	}
}
