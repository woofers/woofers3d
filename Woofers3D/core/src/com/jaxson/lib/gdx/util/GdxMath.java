package com.jaxson.lib.gdx.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.graphics.MyColor;
import com.jaxson.lib.util.MyMath;

public class GdxMath extends MyMath
{
	public static final int RGB_MIN = 0;
	public static final int RGB_MAX = 255;
	private static final float RGB_TO_FLOAT = 1f / RGB_MAX;

	public static final float HALF = 1f / 2f;
	public static final float DEGREES_TO_RADIANS = MathUtils.degreesToRadians;
	public static final float RADIANS_TO_DEGREES = MathUtils.radiansToDegrees;

	public static Vector3 divideVector(Vector3 vector, float scalar)
	{
		return vector.scl(reciprocal(scalar));
	}

	public static Vector3 divideVector(Vector3 vector, Vector3 vector2)
	{
		return vector.set(vector.x / vector2.x, vector.y / vector2.y, vector.z / vector2.z);
	}

	public static Color randColor()
	{
		return randColor(RGB_MIN, RGB_MAX);
	}

	public static Color randColor(int minRGB, int maxRGB)
	{
		return new MyColor(randInt(minRGB, maxRGB), randInt(minRGB, maxRGB), randInt(minRGB, maxRGB));
	}

	public static Vector3 reciprocalVector(Vector3 vector)
	{
		return vector.set(reciprocal(vector.x), reciprocal(vector.y), reciprocal(vector.z));
	}

	public static float toRGB(int color)
	{
		color = Math.abs(color);
		if (color > RGB_MAX) return RGB_TO_FLOAT;
		return color * RGB_TO_FLOAT;
	}
}
