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
		shape().dispose();
	}

	@Override
	public boolean equals(Object other)
	{
		if (!(other instanceof Shape<?>)) return false;
		Shape<?> otherShape = (Shape<?>) other;
		return scale().equals(otherShape.scale())
				&& type() == otherShape.type();
	}

	public Vector3 inertia(float mass)
	{
		Vector3 inertia = new Vector3();
		if (mass <= 0) return inertia;
		shape().calculateLocalInertia(mass, inertia);
		return inertia;
	}

	public Vector3 scale()
	{
		return shape().getLocalScaling();
	}

	public void setScale(Vector3 scale)
	{
		shape().setLocalScaling(scale);
	}

	public S shape()
	{
		return shape;
	}

	public int type()
	{
		return shape().getShapeType();
	}
}
