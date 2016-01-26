package com.jaxson.lib.gdx.bullet.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btSphereShape;

public class SphereShape extends btSphereShape
{
	private static final Vector3 DEFAULT_SIZE = new Vector3(1f, 1f, 1f);
	private static final float RADIUS = 0.5f;

	public SphereShape()
	{
		super(RADIUS);
	}

	public SphereShape(float radius)
	{
		super(radius);
	}

	public SphereShape(Vector3 size)
	{
		super(RADIUS);
		setLocalScaling(size);
	}
}
