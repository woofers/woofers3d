package com.jaxson.lib.gdx.bullet.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btSphereShape;
import com.jaxson.lib.util.MyMath;

public class SphereShape extends btSphereShape
{
	private static final Vector3 DEFAULT_SIZE = new Vector3(1f, 1f, 1f);
	private static final float RADIUS = 0.5f;

	public SphereShape(Vector3 size)
	{
		super(RADIUS);
		setLocalScaling(size);
	}

	public SphereShape(float radius)
	{
		super(radius);
	}

	public SphereShape()
	{
		super(RADIUS);
	}
}
