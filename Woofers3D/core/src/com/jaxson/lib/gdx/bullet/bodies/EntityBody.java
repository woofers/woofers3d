package com.jaxson.lib.gdx.bullet.bodies;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.jaxson.lib.gdx.graphics.g3d.AnimatedEntity;

public abstract class EntityBody<T extends btCollisionObject> extends AnimatedEntity
{
	protected static final float DEFAULT_MASS = 1f;

	private float mass;
	private T body;

	public EntityBody(Model model, float mass)
	{
		super(model);
		this.mass = mass;
	}

	public EntityBody(String modelPath, float mass)
	{
		this(readModel(modelPath), mass);
	}

	public void activate()
	{
		getBody().activate();
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
	}

	public int getActivationState()
	{
		return getBody().getActivationState();
	}

	public T getBody()
	{
		return body;
	}

	public int getCollisionFlags()
	{
		return getBody().getCollisionFlags();
	}

	public int getContactCallbackFilter()
	{
		return getBody().getContactCallbackFilter();
	}

	public int getContactCallbackFlag()
	{
		return getBody().getContactCallbackFlag();
	}

	public float getMass()
	{
		return mass;
	}

	public boolean isBody(btCollisionObject body)
	{
		return getBody() == body;
	}

	@Override
	public void rotate(float yaw, float pitch, float roll)
	{
		super.rotate(yaw, pitch, roll);
		transformToBody();
	}

	public void setActivationState(int state)
	{
		getBody().setActivationState(state);
	}

	protected void setBody(T body)
	{
		this.body = body;
	}

	public void setCollisionFlags(int flags)
	{
		getBody().setCollisionFlags(flags);
	}

	public void setContactCallbackFilter(int flag)
	{
		getBody().setContactCallbackFilter(flag);
	}

	public void setContactCallbackFlag(int flag)
	{
		getBody().setContactCallbackFlag(flag);
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
