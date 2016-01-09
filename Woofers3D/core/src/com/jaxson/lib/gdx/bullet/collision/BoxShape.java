package com.jaxson.lib.gdx.bullet.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.jaxson.lib.util.MyMath;

public class BoxShape extends btBoxShape
{
	public BoxShape(Vector3 size)
	{
		super(size.scl(MyMath.HALF));
	}
}
