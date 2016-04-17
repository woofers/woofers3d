package com.jaxson.lib.gdx.math.random;

import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.math.random.RandomNumber;

public class RandomVector3 extends Vector3
{
	public RandomVector3(float min, float max)
	{
		this(min, max, min, max, min, max);
	}

	public RandomVector3(float minX, float maxX, float minY, float maxY, float minZ, float maxZ)
	{
		super(new RandomNumber(minX, maxX).floatValue(), new RandomNumber(minY, maxY).floatValue(), new RandomNumber(minZ, maxZ).floatValue());
	}

	public RandomVector3(Vector3 min, Vector3 max)
	{
		this(min.x, max.x, min.y, max.y, min.z, max.z);
	}
}
