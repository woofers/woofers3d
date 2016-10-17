package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.util.Updateable;

public interface Accelerometer extends Updateable
{
	public static final float MAX = 1f;
	public static final float MIN = -MAX;
	public static final float RANGE = MAX - MIN;

	public Vector3 alpha();

	public Vector3 deadZone();

	public boolean exists();

	public Vector3 values();

	public float z();

	public float y();

	public float x();

	public boolean tiltsForward();

	public boolean tiltsBackward();

	public boolean tiltsLeft();

	public boolean tiltsRight();

	public boolean tiltsUp();

	public boolean tiltsDown();
}
