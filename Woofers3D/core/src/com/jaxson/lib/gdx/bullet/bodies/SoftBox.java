package com.jaxson.lib.gdx.bullet.bodies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.jaxson.lib.gdx.bullet.PhysicsWorld;
import com.jaxson.lib.gdx.graphics.g3d.util.MyModelBuilder;

public class SoftBox extends SoftBody
{
	public SoftBox(Color color, PhysicsWorld world)
	{
		super(getModel(color), DEFAULT_MASS, world);
	}

	public SoftBox(PhysicsWorld world)
	{
		this(DEFAULT_COLOR, world);
	}

	protected static Model getModel(Color color)
	{
		return new MyModelBuilder().createBox(color);
	}
}
