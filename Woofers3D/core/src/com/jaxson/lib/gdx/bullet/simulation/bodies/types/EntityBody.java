package com.jaxson.lib.gdx.bullet.simulation.bodies.types;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.jaxson.lib.gdx.bullet.simulation.WorldImporter;
import com.jaxson.lib.gdx.graphics.g3d.entities.types.AnimatedEntity;
import com.jaxson.lib.util.Optional;

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

	public void activate()
	{
		body().activate();
		body().setActivationState(ACTIVE_TAG);
	}

	public void addCollisionFlag(int flag)
	{
		setCollisionFlags(collisionFlags() | flag);
	}

	public void deactivate()
	{
		body().setActivationState(DISABLE_SIMULATION);
	}

	@Override
	public void dispose()
	{
		if (!wasImpoted()) super.dispose();
		body.dispose();
	}

	public int activationState()
	{
		return body().getActivationState();
	}

	public B body()
	{
		return body;
	}

	public int collisionFlags()
	{
		return body().getCollisionFlags();
	}

	public int contactCallbackFilter()
	{
		return body().getContactCallbackFilter();
	}

	public int contactCallbackFlag()
	{
		return body().getContactCallbackFlag();
	}

	public float mass()
	{
		return mass;
	}

	public boolean isBody(btCollisionObject body)
	{
		return body().equals(body);
	}

	@Override
	public void rotate(float yaw, float pitch, float roll)
	{
		super.rotate(yaw, pitch, roll);
		transformToBody();
	}

	public void setActivationState(int state)
	{
		body().setActivationState(state);
	}

	public void setCollisionFlags(int flags)
	{
		body().setCollisionFlags(flags);
	}

	public void setContactCallbackFilter(int flag)
	{
		body().setContactCallbackFilter(flag);
	}

	public void setContactCallbackFlag(int flag)
	{
		body().setContactCallbackFlag(flag);
	}

	@Override
	public void moveTo(Vector3 location)
	{
		super.moveTo(location);
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
		Optional<Integer> i = new Optional<>(
				(Integer) modelInstance().userData);
		return i.exists() && i.unwrap() == WorldImporter.IMPORTED;
	}

	protected void bodyToTransform()
	{
		body().getWorldTransform(transform());
	}

	protected void setBody(B body)
	{
		this.body = body;
	}

	protected void transformToBody()
	{
		body().setWorldTransform(transform());
	}
}
