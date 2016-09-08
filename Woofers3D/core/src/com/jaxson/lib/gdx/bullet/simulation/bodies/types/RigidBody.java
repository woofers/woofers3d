package com.jaxson.lib.gdx.bullet.simulation.bodies.types;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.jaxson.lib.gdx.bullet.simulation.MyMotionState;
import com.jaxson.lib.gdx.bullet.simulation.collision.types.Shape;

public class RigidBody extends ShapeBody<btRigidBody, Shape>
{
	private MyMotionState motionState;

	public RigidBody(Model model, Shape shape)
	{
		this(model, shape, MASS);
	}

	public RigidBody(Model model, Shape shape, float mass)
	{
		this(new ModelInstance(model), shape, mass);
	}

	public RigidBody(ModelInstance modelInstance, Shape shape)
	{
		this(modelInstance, shape, MASS);
	}

	public RigidBody(ModelInstance modelInstance, Shape shape, float mass)
	{
		this(modelInstance, shape, mass, new btRigidBody(mass, null, null, shape.getInertia(mass)));
	}

	public RigidBody(String modelPath, Shape shape)
	{
		this(modelPath, shape, MASS);
	}

	public RigidBody(String modelPath, Shape shape, float mass)
	{
		this(readModel(modelPath), shape, mass);
	}

	private RigidBody(ModelInstance modelInstance, Shape shape, float mass, btRigidBody body)
	{
		super(modelInstance, body, shape, mass);
		setMotionState(new MyMotionState(getTransform()));
	}

	public void applyCentralImpulse(Ray ray)
	{
		applyCentralImpulse(ray, 1f);
	}

	public void applyCentralImpulse(Ray ray, float impulse)
	{
		applyCentralImpulse(ray.direction.scl(impulse));
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
