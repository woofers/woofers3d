package com.jaxson.lib.gdx.bullet.simulation.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.jaxson.lib.gdx.bullet.simulation.collision.types.ConvexShape;

public class BoxShape extends ConvexShape
{
	private static final Vector3 SIZE = new Vector3(1f, 1f, 1f);

	public BoxShape()
	{
		this(SIZE.cpy());
	}

	public BoxShape(Vector3 size)
	{
		super(new btBoxShape(size.scl(0.5f)));
	}
}
