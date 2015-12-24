package com.jaxson.lib.gdx.bullet.bodies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btConvexShape;

public abstract class ShapeBody<T extends btCollisionObject> extends EntityBody<T>
{
	private btConvexShape shape;

	public ShapeBody(Model model, btConvexShape shape)
	{
		this(model, shape, DEFAULT_MASS);
	}

	public ShapeBody(Model model, btConvexShape shape, float mass)
	{
		super(model, mass);
		this.shape = shape;
	}

	public ShapeBody(String modelPath, btConvexShape shape)
	{
		this(modelPath, shape, DEFAULT_MASS);
	}

	public ShapeBody(String modelPath, btConvexShape shape, float mass)
	{
		this(new ObjLoader().loadModel(Gdx.files.internal(modelPath)), shape, mass);
	}

	@Override
	public void dispose()
	{
		super.dispose();
		shape.dispose();
	}

	public btConvexShape getCollisionShape()
	{
		return shape;
	}

	public btConvexShape getFittedHitbox()
	{
		return new btBoxShape(getSize());
	}

	public Vector3 getInertia()
	{
		Vector3 inertia = new Vector3();
		if (getMass() <= 0) return inertia;
		System.out.println(getCollisionShape());
		getCollisionShape().calculateLocalInertia(getMass(), inertia);
		return inertia;
	}

	public void setCollisionShape(btConvexShape shape)
	{
		this.shape = shape;
		getBody().setCollisionShape(shape);
	}

	@Override
	public void setScale(Vector3 scale)
	{
		super.setScale(scale);
		getCollisionShape().setLocalScaling(scale);
	}
}
