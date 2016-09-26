package com.jaxson.lib.gdx.bullet.simulation.bodies.types;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.jaxson.lib.gdx.bullet.simulation.WorldImporter;
import com.jaxson.lib.gdx.graphics.g3d.entities.types.AnimatedEntity;

public abstract class EntityBody<B extends btCollisionObject>
		extends AnimatedEntity
{
	public static final int ACTIVE_TAG = 1;
	public static final int ISLAND_SLEEPING = 2;
	public static final int WANTS_DEACTIVATION = 3;
	public static final int DISABLE_DEACTIVATION = 4;
	public static final int DISABLE_SIMULATION = 5;

	protected static final float MASS = 1f;
	protected static final Color COLOR = Color.ORANGE;

	private float mass;
	private B body;

	public EntityBody(Model model, B body, float mass)
	{
		this(new ModelInstance(model), body, mass);
	}

	public EntityBody(ModelInstance modelInstance, B body)
	{
		this(modelInstance, body, MASS);
	}

	public EntityBody(ModelInstance modelInstance, B body, float mass)
	{
		super(modelInstance);
		this.mass = mass;
		setBody(body);
	}

	public EntityBody(String modelPath, B body)
	{
		this(modelPath, body, MASS);
	}

	public EntityBody(String modelPath, B body, float mass)
	{
		this(readModel(modelPath), body, mass);
	}

	public void activate()
	{
		getBody().activate();
		getBody().setActivationState(ACTIVE_TAG);
	}

	public void addCollisionFlag(int flag)
	{
		setCollisionFlags(getCollisionFlags() | flag);
	}

	public void deactivate()
	{
		getBody().setActivationState(DISABLE_SIMULATION);
	}

	@Override
	public void dispose()
	{
		if (!wasImpoted()) super.dispose();
		body.dispose();
	}

	public int getActivationState()
	{
		return getBody().getActivationState();
	}

	public B getBody()
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
		return getBody().equals(body);
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

	public boolean wasImpoted()
	{
		Integer i = (Integer) getModelInstance().userData;
		return i != null && i == WorldImporter.IMPORTED;
	}

	protected void bodyToTransform()
	{
		getBody().getWorldTransform(getTransform());
	}

	protected void setBody(B body)
	{
		this.body = body;
	}

	protected void transformToBody()
	{
		getBody().setWorldTransform(getTransform());
	}
}
