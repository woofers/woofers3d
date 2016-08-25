package com.jaxson.lib.gdx.bullet.simulation;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;

public class RayCallback extends ClosestRayResultCallback
{
	private static final float CLOSEST_HIT_FRACTION = 1f;
	private static final float MAX_DISTANCE = 50f;
	private static final Vector3 RAY_START = Vector3.Zero;
	private static final Vector3 RAY_END = Vector3.Y;

	private Vector3 rayStart, rayEnd;

	public RayCallback()
	{
		super(RAY_START, RAY_END);
		this.rayStart = new Vector3();
		this.rayEnd = new Vector3();
		set();
	}

	public btCollisionObject getCollisionObject(Ray ray, PhysicsWorld world)
	{
		set(ray);
		world.rayTest(rayStart, rayEnd, this);
		if (hasHit()) return getCollisionObject();
		return null;
	}

	public void releaseCollisionObject()
	{
		setCollisionObject(null);
	}

	public void reset()
	{
		releaseCollisionObject();
		setClosestHitFraction(CLOSEST_HIT_FRACTION);
	}

	public void set()
	{
		set(RAY_START, RAY_END);
	}

	public void set(Ray ray)
	{
		set(ray, MAX_DISTANCE);
	}

	public void set(Ray ray, float distance)
	{
		rayStart.set(ray.origin);
		rayEnd.set(ray.direction);
		rayEnd.scl(distance);
		rayEnd.add(rayStart);
		set(rayStart, rayEnd);
	}

	public void set(Vector3 rayStart, Vector3 rayEnd)
	{
		reset();
		setRayFromWorld(rayStart);
		setRayToWorld(rayEnd);
	}
}
