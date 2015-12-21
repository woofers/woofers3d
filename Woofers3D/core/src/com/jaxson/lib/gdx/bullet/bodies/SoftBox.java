package com.jaxson.lib.gdx.bullet.bodies;

import com.badlogic.gdx.graphics.Color;
import com.jaxson.lib.gdx.bullet.PhysicsWorld;
import com.jaxson.lib.gdx.graphics.MyColor;
import com.jaxson.lib.gdx.graphics.g3d.util.MyModelBuilder;

public class SoftBox extends SoftBody
{
	protected static final Color COLOR = MyColor.ORANGE;

	public SoftBox(Color color, PhysicsWorld world)
	{
		super(new MyModelBuilder().createBox(color), DEFAULT_MASS, world);
	}

	public SoftBox(PhysicsWorld world)
	{
		this(COLOR, world);
	}
}
