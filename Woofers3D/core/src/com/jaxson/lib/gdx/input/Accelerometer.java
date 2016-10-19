package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.util.Updateable;
import com.badlogic.gdx.math.Matrix4;

public interface Accelerometer extends Updateable
{
	public static final float MAX = 1f;
	public static final float MIN = -MAX;
	public static final float RANGE = MAX - MIN;

	public Vector3 alpha();

	public Vector3 deadZone();

	public boolean exists();

	public boolean tiltsBackward();

	public boolean tiltsDown();

	public boolean tiltsForward();

	public boolean tiltsLeft();

	public boolean tiltsRight();

	public boolean tiltsUp();

	public Vector3 values();

	public Matrix4 rotationMatrix();

	public float x();

	public float y();

	public float z();
}
