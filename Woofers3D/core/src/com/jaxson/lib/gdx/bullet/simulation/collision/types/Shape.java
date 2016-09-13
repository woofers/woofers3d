package com.jaxson.lib.gdx.bullet.simulation.collision.types;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.utils.Disposable;

public class Shape<S extends btCollisionShape> implements Disposable
{
	private S shape;

	public Shape(S shape)
	{
		this.shape = shape;
	}

	@Override
	public void dispose()
	{
		getCollisionShape().dispose();
	}

	@Override
	public boolean equals(Object other)
	{
		if (!(other instanceof Shape<?>)) return false;
		Shape<?> otherShape = (Shape<?>) other;
		return getScale().equals(otherShape.getScale())
				&& getType() == otherShape.getType();
	}

	public S getCollisionShape()
	{
		return shape;
	}

	public Vector3 getInertia(float mass)
	{
		Vector3 inertia = new Vector3();
		if (mass <= 0) return inertia;
		getCollisionShape().calculateLocalInertia(mass, inertia);
		return inertia;
	}

	public Vector3 getScale()
	{
		return getCollisionShape().getLocalScaling();
	}

	public int getType()
	{
		return getCollisionShape().getShapeType();
	}

	public void setScale(Vector3 scale)
	{
		getCollisionShape().setLocalScaling(scale);
	}
}
