package com.jaxson.lib.gdx.math;

import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.math.random.RandomVector3;
import com.jaxson.lib.math.MyMath;
import com.jaxson.lib.math.Reciprocal;
import com.badlogic.gdx.math.MathUtils;

public class GdxMath extends MyMath
{
    public static float FLOAT_ROUNDING = MathUtils.FLOAT_ROUNDING_ERROR;

    public static Vector3 absVector(Vector3 vector)
    {
        return vector.set(abs(vector.x), abs(vector.y), abs(vector.z));
    }

    public static Vector3 divideVector(Vector3 vector, float scalar)
    {
        return vector.scl(new Reciprocal(scalar).floatValue());
    }

    public static Vector3 divideVector(Vector3 vector, Vector3 vector2)
    {
        return vector.set(
                vector.x / vector2.x,
                vector.y / vector2.y,
                vector.z / vector2.z);
    }

    public static Vector3 randVector3(float min, float max)
    {
        return new RandomVector3(min, max);
    }

    public static Vector3 reciprocalVector(Vector3 vector)
    {
        return vector.set(
                new Reciprocal(vector.x).floatValue(),
                new Reciprocal(vector.y).floatValue(),
                new Reciprocal(vector.z).floatValue());
    }
}
