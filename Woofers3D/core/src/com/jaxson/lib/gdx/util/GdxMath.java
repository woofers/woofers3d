package com.jaxson.lib.gdx.util;

import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.util.MyMath;

public class GdxMath extends MyMath
{
	private static final int RGB_MAX = 255;
	private static final float RGB_TO_FLOAT = (float) (1f) / (float) RGB_MAX;
	public static final float HALF = 1f / 2f;

	public static Vector3 divideVector(Vector3 vector, Vector3 vector2)
	{
		return new Vector3(vector.x / vector2.x, vector.y / vector2.y, vector.z / vector2.z);
	}

	public static Vector3 divideVector(Vector3 vector, float scalar)
	{
		return vector.scl(1f / scalar);
	}

	public static Vector3 reciprocalVector(Vector3 vector)
	{
		return vector.set(1f / vector.x, 1f / vector.y, 1f / vector.z);
	}

	public static float toRGB(int color)
	{
		color = Math.abs(color);
		if (color > RGB_MAX)
			return RGB_TO_FLOAT;
		return color * RGB_TO_FLOAT;
	}
}
