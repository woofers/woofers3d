package com.jaxson.lib.gdx.math.collision;

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
	public void getWorldTransform(Matrix4 worldTrans)
	{
		worldTrans.set(transform);
	}

	@Override
	public void setWorldTransform(Matrix4 worldTrans)
	{
		transform.set(worldTrans);
	}
}
