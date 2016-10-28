package com.jaxson.lib.gdx.bullet.simulation;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;

public class MyMotionState extends btMotionState
{
	private Matrix4 transform;

	public MyMotionState(Matrix4 transform)
	{
		super();
		this.transform = transform;
	}

	@Override
	public void getWorldTransform(Matrix4 worldTransform)
	{
		worldTransform.set(transform);
	}

	@Override
	public void setWorldTransform(Matrix4 worldTransform)
	{
		transform.set(worldTransform);
	}

	protected Matrix4 transform()
	{
		return transform;
	}
}
