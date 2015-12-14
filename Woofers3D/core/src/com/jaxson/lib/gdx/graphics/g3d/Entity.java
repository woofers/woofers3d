package com.jaxson.lib.gdx.graphics.g3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.graphics.GameObject;

public abstract class Entity extends GameObject
{
	protected static final Vector3 LOCATION = Vector3.Zero;
	private static final float DIAMETER_TO_RADIUS = 1f / 2f;

	private ModelInstance modelInstance;

	public Entity(String modelPath)
	{
		this(modelPath, LOCATION);
	}

	public Entity(String modelPath, Vector3 location)
	{
		this(new ObjLoader().loadModel(Gdx.files.internal(modelPath)), location);
	}

	public Entity(Model model)
	{
		this(model, LOCATION);
	}

	public Entity(Model model, Vector3 location)
	{
		this.modelInstance = new ModelInstance(model, location);
	}

	@Override
	public void dispose()
	{
		getModel().dispose();
	}

	public BoundingBox getBoundingBox()
	{
		return modelInstance.calculateBoundingBox(new BoundingBox());
	}

	public Vector3 getCenter()
	{
		return getBoundingBox().getCenter(new Vector3());
	}

	public Vector3 getCenterLocation()
	{
		return getCenter().add(getLocation());
	}

	public Vector3 getDeltaLocation(Entity entity)
	{
		return getDeltaLocation(entity.getLocation());
	}

	public Vector3 getDeltaLocation(Vector3 location)
	{
		return location.cpy().sub(getLocation());
	}

	public float getDiameter()
	{
		return getDimensions().len();
	}

	public Vector3 getDimensions()
	{
		return getBoundingBox().getDimensions(new Vector3());
	}

	public Vector3 getDirection()
	{
		float[] matrix = getTransform().getValues();
		return new Vector3(matrix[8], matrix[9], matrix[10]);
	}

	public ModelInstance getModelInstance()
	{
		return modelInstance;
	}

	public Model getModel()
	{
		return modelInstance.model;
	}

	public Vector3 getLocation()
	{
		return getTransform().getTranslation(new Vector3());
	}

	public Vector3 getScale()
	{
		return getTransform().getScale(new Vector3());
	}

	public float getRadius()
	{
		return getDiameter() * DIAMETER_TO_RADIUS;
	}

	public Ray getRay(Entity entity)
	{
		return getRay(entity.getLocation());
	}

	public Ray getRay(Vector3 location)
	{
		return new Ray(getCenterLocation(), getDeltaLocation(location));
	}

	public Vector3 getRotation()
	{
		Quaternion rotation = getRoationQuat();
		return new Vector3(rotation.getYaw(), rotation.getPitch(), rotation.getRoll());
	}

	public Quaternion getRoationQuat()
	{
		return getTransform().getRotation(new Quaternion());
	}

	public Matrix4 getTransform()
	{
		return modelInstance.transform;
	}

	public boolean isVisible(Camera camera)
	{
		return camera.frustum.sphereInFrustum(getCenterLocation(), getRadius());
	}

	public void setScale(Vector3 scale)
	{
		getTransform().scl(scale);
	}
}
