package com.jaxson.lib.gdx.bullet.bodies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.jaxson.lib.gdx.bullet.PhysicsWorld;
import com.jaxson.lib.gdx.graphics.g3d.util.MyModelBuilder;

public class SoftBox extends SoftBody
{
	protected static final Color COLOR = Color.ORANGE;

	public SoftBox(Color color, PhysicsWorld world)
	{
		super(getModel(color), DEFAULT_MASS, world);
	}

	public SoftBox(PhysicsWorld world)
	{
		this(COLOR, world);
	}

	protected static Model getModel(Color color)
	{
		return new MyModelBuilder().createBox(color);
	}
}
