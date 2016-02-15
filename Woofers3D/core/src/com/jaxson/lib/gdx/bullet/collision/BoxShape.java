package com.jaxson.lib.gdx.bullet.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.jaxson.lib.math.MyMath;

public class BoxShape extends btBoxShape
{
	private static Vector3 DEFAULT_SIZE = new Vector3(1f, 1f, 1f);

	public BoxShape()
	{
		this(DEFAULT_SIZE.cpy());
	}

	public BoxShape(Vector3 size)
	{
		super(size.scl(MyMath.HALF));
	}
}
