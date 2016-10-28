package com.jaxson.lib.gdx.bullet.simulation.bodies.types;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.jaxson.lib.gdx.bullet.simulation.collision.BoxShape;
import com.jaxson.lib.gdx.bullet.simulation.collision.types.Shape;
import com.jaxson.lib.util.Optional;
import com.jaxson.lib.gdx.bullet.simulation.collision.types.ConvexHullShape;

public abstract class ShapeBody<B extends btCollisionObject, S extends Shape>
		extends EntityBody<B>
{
	private S shape;

	public ShapeBody(Model model, B body, S shape)
	{
		this(model, body, shape, MASS);
	}

	public ShapeBody(Model model, B body, S shape, float mass)
	{
		this(new ModelInstance(model), body, shape, mass);
	}

	public ShapeBody(ModelInstance modelInstance, B body, S shape)
	{
		this(modelInstance, body, shape, MASS);
	}

	public ShapeBody(ModelInstance modelInstance, B body, S shape, float mass)
	{
		super(modelInstance, body, mass);
		setCollisionShape(shape);
	}

	@Override
	public void dispose()
	{
		super.dispose();
		shape.dispose();
	}

	public BoxShape fittedHitbox()
	{
		return new BoxShape(size());
	}

	public ConvexHullShape fittedShape()
	{
		return new ConvexHullShape(model());
	}

	public Vector3 inertia()
	{
		return shape.inertia(mass());
	}

	@Override
	public void scale(Vector3 scale)
	{
		super.scale(scale);
		setLocalScaling(scale.scl(shapeScale()));
	}

	/*
	1) remove the object from the world
	2) assign the new shape using object->setCollisionShape(newShape)
	3) recompute the inertia tensor for dynamic objects (mass>0)
			using newShape->calcLocalInertia(...) and use body->setMassProps
	4) add the body to the world
	 */
	public void setCollisionShape(S shape)
	{
		if (new Optional<>(shape).equals(shape())) return;
		Shape oldShape = shape();
		this.shape = shape;
		body().setCollisionShape(shape.bulletShape());
		if (oldShape != null) oldShape.dispose();
	}

	public void setCollisionShapeScale(float scale)
	{
		setCollisionShapeScale(new Vector3(scale, scale, scale));
	}

	public void setCollisionShapeScale(Vector3 scale)
	{
		setLocalScaling(scale.scl(scale()));
	}

	private void setLocalScaling(Vector3 scale)
	{
		shape().setScale(scale);
	}

	public S shape()
	{
		return shape;
	}

	public Vector3 shapeScale()
	{
		return shape().scale();
	}

	protected static BoxShape fittedHitbox(Model model)
	{
		return new BoxShape(model.calculateBoundingBox(
				new BoundingBox()).getDimensions(new Vector3()));
	}

	protected static ConvexHullShape fittedShape(Model model)
	{
		return new ConvexHullShape(model);
	}
}
