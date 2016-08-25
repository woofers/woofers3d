package com.jaxson.lib.gdx.bullet.simulation.bodies.types;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.jaxson.lib.gdx.bullet.simulation.collision.BoxShape;
import com.jaxson.lib.gdx.bullet.simulation.collision.types.Shape;

public abstract class ShapeBody<B extends btCollisionObject, S extends Shape> extends EntityBody<B>
{
	private S shape;

	public ShapeBody(Model model, S shape)
	{
		this(model, shape, MASS);
	}

	public ShapeBody(Model model, S shape, float mass)
	{
		this(new ModelInstance(model), shape, mass);
	}

	public ShapeBody(ModelInstance modelInstance, S shape)
	{
		this(modelInstance, shape, MASS);
	}

	public ShapeBody(ModelInstance modelInstance, S shape, float mass)
	{
		super(modelInstance, mass);
		this.shape = shape;
	}

	public ShapeBody(String modelPath, S shape)
	{
		this(modelPath, shape, MASS);
	}

	public ShapeBody(String modelPath, S shape, float mass)
	{
		this(readModel(modelPath), shape, mass);
	}

	@Override
	public void dispose()
	{
		super.dispose();
		shape.dispose();
	}

	public S getCollisionShape()
	{
		return shape;
	}

	public Vector3 getCollisionShapeScale()
	{
		return getCollisionShape().getScale();
	}

	public Shape getFittedHitbox()
	{
		return new BoxShape(getSize());
	}

	public Vector3 getInertia()
	{
		return shape.getInertia(getMass());
	}

	public void setCollisionShape(S shape)
	{
		this.shape = shape;
		getBody().setCollisionShape(shape.getCollisionShape());
	}

	public void setCollisionShapeScale(float scale)
	{
		setCollisionShapeScale(new Vector3(scale, scale, scale));
	}

	public void setCollisionShapeScale(Vector3 scale)
	{
		setLocalScaling(scale.scl(getScale()));
	}

	private void setLocalScaling(Vector3 scale)
	{
		getCollisionShape().setScale(scale);
	}

	@Override
	public void setScale(Vector3 scale)
	{
		super.setScale(scale);
		setLocalScaling(scale.scl(getCollisionShapeScale()));
	}
}
