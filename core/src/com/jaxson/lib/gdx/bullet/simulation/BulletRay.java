package com.jaxson.lib.gdx.bullet.simulation;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.utils.Disposable;
import com.jaxson.lib.util.Optional;

public class BulletRay implements Disposable
{
	private static final float CLOSEST_HIT_FRACTION = 1f;
	private static final float MAX_DISTANCE = 50f;
	private static final Vector3 RAY_START = Vector3.Zero;
	private static final Vector3 RAY_END = Vector3.Y;

	private ClosestRayResultCallback callback;
	private Vector3 rayStart, rayEnd;

	protected BulletRay()
	{
		this.rayStart = new Vector3(RAY_START);
		this.rayEnd = new Vector3(RAY_END);
		this.callback = new ClosestRayResultCallback(rayStart, rayEnd);
		reset();
	}

	public Optional<btCollisionObject> collisionObject(Ray ray,
			BulletWorld world)
	{
		return collisionObject(ray, MAX_DISTANCE, world);
	}

	public Optional<btCollisionObject> collisionObject(Ray ray,
			float distance, BulletWorld world)
	{
		set(ray, distance);
		rayTest(world);
		return hitObject();
	}

	@Override
	public void dispose()
	{
		callback.dispose();
	}

	private Optional<btCollisionObject> hitObject()
	{
		return new Optional<>(callback.getCollisionObject());
	}

	private void rayTest(BulletWorld world)
	{
		world.rayTest(rayStart, rayEnd, callback);
	}

	private void reset()
	{
		callback.setCollisionObject(null);
		callback.setClosestHitFraction(CLOSEST_HIT_FRACTION);
	}

	private void set(Ray ray)
	{
		set(ray, MAX_DISTANCE);
	}

	private void set(Ray ray, float distance)
	{
		rayStart.set(ray.origin);
		rayEnd.set(ray.direction);
		rayEnd.scl(distance);
		rayEnd.add(rayStart);
		set(rayStart, rayEnd);
	}

	private void set(Vector3 rayStart, Vector3 rayEnd)
	{
		reset();
		callback.setRayFromWorld(rayStart);
		callback.setRayToWorld(rayEnd);
	}
}
