package com.jaxson.lib.gdx.bullet.bodies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btConvexShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.jaxson.lib.gdx.bullet.MyMotionState;

public abstract class RigidBody extends EntityBody<btRigidBody>
{
	private MyMotionState motionState;

	public RigidBody(String modelPath, btConvexShape shape)
	{
		this(modelPath, shape, MASS);
	}

	public RigidBody(String modelPath, btConvexShape shape, float mass)
	{
		this(new ObjLoader().loadModel(Gdx.files.internal(modelPath)), shape, mass);
	}

	public RigidBody(Model model, btConvexShape shape)
	{
		this(model, shape, MASS);
	}

	public RigidBody(Model model, btConvexShape shape, float mass)
	{
		super(model, shape, mass);
		this.motionState = new MyMotionState(getTransform());
		setBody(new btRigidBody(mass, motionState, shape, getInertia()));
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
