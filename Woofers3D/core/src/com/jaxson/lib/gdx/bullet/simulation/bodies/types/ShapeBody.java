package com.jaxson.lib.gdx.bullet.simulation.bodies.types;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.jaxson.lib.gdx.bullet.simulation.collision.BoxShape;
import com.jaxson.lib.gdx.bullet.simulation.collision.types.Shape;
import com.jaxson.lib.util.Optional;

public abstract class ShapeBody
		<B extends btCollisionObject, S extends Shape>
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

	public ShapeBody(String modelPath, B body, S shape)
	{
		this(modelPath, body, shape, MASS);
	}

	public ShapeBody(String modelPath, B body, S shape, float mass)
	{
		this(readModel(modelPath), body, shape, mass);
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
		if (new Optional<S>(shape).equals(getCollisionShape())) return;
		Shape oldShape = getCollisionShape();
		this.shape = shape;
		getBody().setCollisionShape(shape.getCollisionShape());
		if (oldShape != null) oldShape.dispose();
	}

	public void setCollisionShapeScale(float scale)
	{
		setCollisionShapeScale(new Vector3(scale, scale, scale));
	}

	public void setCollisionShapeScale(Vector3 scale)
	{
		setLocalScaling(scale.scl(getScale()));
	}

	@Override
	public void setScale(Vector3 scale)
	{
		super.setScale(scale);
		setLocalScaling(scale.scl(getCollisionShapeScale()));
	}

	private void setLocalScaling(Vector3 scale)
	{
		getCollisionShape().setScale(scale);
	}

	protected static BoxShape getFittedHitbox(Model model)
	{
		return new BoxShape(model.calculateBoundingBox(
				new BoundingBox()).getDimensions(new Vector3()));
	}
}
