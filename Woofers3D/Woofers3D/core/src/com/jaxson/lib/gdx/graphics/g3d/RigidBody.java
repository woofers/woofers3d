package com.jaxson.lib.gdx.graphics.g3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCapsuleShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.jaxson.lib.gdx.graphics.GameObject;
import com.jaxson.lib.gdx.math.collision.MyMotionState;
import java.lang.Math;

public abstract class RigidBody extends Entity
{
	private static final float MASS = 1f;

	private MyMotionState motionState;
	private float mass;
	private btRigidBody body;
	private btCollisionShape shape;

	public RigidBody(String modelPath, btCollisionShape shape)
	{
		this(modelPath, shape, MASS);
	}

	public RigidBody(String modelPath, btCollisionShape shape, float mass)
	{
		this(new ObjLoader().loadModel(Gdx.files.internal(modelPath)), shape, mass);
	}

	public RigidBody(Model model, btCollisionShape shape)
	{
		this(model, shape, MASS);
	}

	public RigidBody(Model model, btCollisionShape shape, float mass)
	{
		super(model);
		this.mass = mass;
		this.shape = shape;
		this.motionState = new MyMotionState(getTransform());
		this.body = new btRigidBody(mass, motionState, shape, getInertia());
		setCollisionShape(shape);
	}

	public void addCollisionFlag(int flag)
	{
		setCollisionFlags(getCollisionFlags() | flag);
	}

	@Override
	public void dispose()
	{
		super.dispose();
		body.dispose();
		shape.dispose();
	}

	public int getActivationState()
	{
		return body.getActivationState();
	}

	public btRigidBody getBody()
	{
		return body;
	}

	public int getContactCallbackFlag()
	{
		return body.getContactCallbackFlag();
	}

    public int getContactCallbackFilter()
    {
		return body.getContactCallbackFilter();
    }

	public int getCollisionFlags()
	{
		return body.getCollisionFlags();
	}

	public btCollisionShape getCollisionShape()
	{
		return shape;
	}

	public Vector3 getInertia()
	{
		Vector3 inertia = new Vector3();
		if (mass <= 0) return inertia;
		shape.calculateLocalInertia(mass, inertia);
		return inertia;
	}

	public float getMass()
	{
		return mass;
	}

	public void setActivationState(int state)
	{
		body.setActivationState(state);
	}

	public void setContactCallbackFlag(int flag)
	{
		body.setContactCallbackFlag(flag);
	}

    public void setContactCallbackFilter(int flag)
    {
		body.setContactCallbackFilter(flag);
    }

	public void setCollisionFlags(int flags)
	{
		body.setContactCallbackFilter(flags);
	}

	public void setCollisionShape(btCollisionShape shape)
	{
		this.shape = shape;
		body.setCollisionShape(shape);
	}

	public void setMass(float mass)
	{
		this.mass = mass;
		body.setMassProps(mass, getInertia());
	}

	public void setMotionState(MyMotionState motionState)
	{
		this.motionState = motionState;
	}

	private void updateBody()
	{
		body.proceedToTransform(getTransform());
	}
}
