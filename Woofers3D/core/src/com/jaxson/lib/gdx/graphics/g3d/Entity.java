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
import com.jaxson.lib.util.MyMath;

public abstract class Entity extends GameObject
{
	protected static final Vector3 LOCATION = Vector3.Zero;
	private static final int MATRIX_DIRECTION_X = 8;
	private static final int MATRIX_DIRECTION_Y = 9;
	private static final int MATRIX_DIRECTION_Z = 10;

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
		return getSize().len();
	}

	public Vector3 getDirection()
	{
		float[] matrix = getTransform().getValues();
		return new Vector3(matrix[MATRIX_DIRECTION_X], matrix[MATRIX_DIRECTION_Y], matrix[MATRIX_DIRECTION_Z]);
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

	public Vector3 getOriginalSize()
	{
		return getBoundingBox().getDimensions(new Vector3());
	}

	public Vector3 getScale()
	{
		return getTransform().getScale(new Vector3());
	}

	public Vector3 getSize()
	{
		return getOriginalSize().scl(getScale());
	}

	public float getRadius()
	{
		return getDiameter() * MyMath.DIAMETER_TO_RADIUS;
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
}
