package com.jaxson.lib.gdx.bullet.simulation.collision.types;

import com.badlogic.gdx.physics.bullet.collision.btConvexShape;

public class ConvexShape extends Shape<btConvexShape>
{
	public ConvexShape(btConvexShape shape)
	{
		super(shape);
	}
}
