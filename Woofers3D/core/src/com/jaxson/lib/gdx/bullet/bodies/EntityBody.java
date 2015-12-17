package com.jaxson.lib.gdx.bullet.bodies;

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
import com.badlogic.gdx.physics.bullet.collision.btConvexShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.jaxson.lib.gdx.graphics.GameObject;
import com.jaxson.lib.gdx.graphics.g3d.Entity;
import com.jaxson.lib.gdx.graphics.g3d.GhostEntity;
import com.jaxson.lib.gdx.bullet.MyMotionState;
import java.lang.Math;

public abstract class EntityBody<T extends btCollisionObject> extends GhostEntity
{
	protected static final float MASS = 1f;

	private float mass;
	private T body;
	private btConvexShape shape;

	public EntityBody(String modelPath, btConvexShape shape)
	{
		this(modelPath, shape, MASS);
	}

	public EntityBody(String modelPath, btConvexShape shape, float mass)
	{
		this(new ObjLoader().loadModel(Gdx.files.internal(modelPath)), shape, mass);
	}

	public EntityBody(Model model, btConvexShape shape)
	{
		this(model, shape, MASS);
	}

	public EntityBody(Model model, btConvexShape shape, float mass)
	{
		super(model);
		this.mass = mass;
		this.shape = shape;
	}

	public void addCollisionFlag(int flag)
	{
		setCollisionFlags(getCollisionFlags() | flag);
	}

	protected void bodyToTransform()
	{
		getBody().getWorldTransform(getTransform());
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

	public T getBody()
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

	public btConvexShape getCollisionShape()
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

	@Override
	public void rotate(float yaw, float pitch, float roll)
	{
		super.rotate(yaw, pitch, roll);
		transformToBody();
	}

	public void setActivationState(int state)
	{
		body.setActivationState(state);
	}

	protected void setBody(T body)
	{
		this.body = body;
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
		body.setCollisionFlags(flags);
	}

	public void setCollisionShape(btConvexShape shape)
	{
		this.shape = shape;
		body.setCollisionShape(shape);
	}

	@Override
	public void setLocation(Vector3 location)
	{
		super.setLocation(location);
		transformToBody();
	}

	public void setMass(float mass)
	{
		this.mass = mass;
	}

	@Override
	public void setRotation(float yaw, float pitch, float roll)
	{
		super.setRotation(yaw, pitch, roll);
		transformToBody();
	}

	@Override
	public void setScale(Vector3 scale)
	{
		super.setScale(scale);
		shape.setLocalScaling(scale);
		transformToBody();
	}

	protected void transformToBody()
	{
		getBody().setWorldTransform(getTransform());
	}

	@Override
	public void translate(Vector3 translation)
	{
		super.translate(translation);
		transformToBody();
	}

	@Override
	public void translateABS(Vector3 translation)
	{
		super.translateABS(translation);
		transformToBody();
	}
}
