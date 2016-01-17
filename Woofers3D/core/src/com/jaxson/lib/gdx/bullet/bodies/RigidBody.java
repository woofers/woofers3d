package com.jaxson.lib.gdx.bullet.bodies;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btConvexShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.jaxson.lib.gdx.bullet.MyMotionState;

public abstract class RigidBody extends ShapeBody<btRigidBody>
{
	private MyMotionState motionState;

	public RigidBody(Model model, btConvexShape shape)
	{
		this(model, shape, DEFAULT_MASS);
	}

	public RigidBody(Model model, btConvexShape shape, float mass)
	{
		super(model, shape, mass);
		setBody(new btRigidBody(mass, getMotionState(), shape, getInertia()));
		setMotionState(new MyMotionState(getTransform()));
	}

	public RigidBody(String modelPath, btConvexShape shape)
	{
		this(modelPath, shape, DEFAULT_MASS);
	}

	public RigidBody(String modelPath, btConvexShape shape, float mass)
	{
		this(readModel(modelPath), shape, mass);
	}

	public void applyCentralImpulse(Vector3 impulse)
	{
		activate();
		getBody().applyCentralImpulse(impulse);
	}

	@Override
	public void dispose()
	{
		super.dispose();
		motionState.dispose();
	}

	public MyMotionState getMotionState()
	{
		return motionState;
	}

	@Override
	public void setMass(float mass)
	{
		super.setMass(mass);
		getBody().setMassProps(mass, getInertia());
	}

	public void setMotionState(MyMotionState motionState)
	{
		this.motionState = motionState;
		getBody().setMotionState(motionState);
	}
}
