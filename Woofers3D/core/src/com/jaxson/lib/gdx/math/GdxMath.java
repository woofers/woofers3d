package com.jaxson.lib.gdx.math;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.math.MyMath;
import com.jaxson.lib.math.random.RandomNumber;

public class GdxMath extends MyMath
{
	public static final float DEGREES_TO_RADIANS = MathUtils.degreesToRadians;
	public static final float RADIANS_TO_DEGREES = MathUtils.radiansToDegrees;

	private GdxMath()
	{

	}

	public static Vector3 absVector(Vector3 vector)
	{
		return vector.set(abs(vector.x), abs(vector.y), abs(vector.z));
	}

	public static Vector3 divideVector(Vector3 vector, float scalar)
	{
		return vector.scl(reciprocal(scalar));
	}

	public static Vector3 divideVector(Vector3 vector, Vector3 vector2)
	{
		return vector.set(vector.x / vector2.x, vector.y / vector2.y, vector.z / vector2.z);
	}

	public static Vector3 randVector3(float min, float max)
	{
		RandomNumber range = new RandomNumber(min, max);
		return new Vector3(range.floatValue(), range.floatValue(), range.floatValue());
	}

	public static Vector3 reciprocalVector(Vector3 vector)
	{
		return vector.set(reciprocal(vector.x), reciprocal(vector.y), reciprocal(vector.z));
	}
}
