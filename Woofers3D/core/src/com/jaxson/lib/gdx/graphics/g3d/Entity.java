package com.jaxson.lib.gdx.graphics.g3d;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import com.jaxson.lib.gdx.util.GameObject;
import com.jaxson.lib.gdx.util.GdxFileReader;
import com.jaxson.lib.gdx.util.GdxMath;
import com.jaxson.lib.util.MyMath;

public abstract class Entity extends GameObject
{
	private static final int MATRIX_DIRECTION_X = 8;
	private static final int MATRIX_DIRECTION_Y = 9;
	private static final int MATRIX_DIRECTION_Z = 10;
	private static final int ROOT_NODE_LOCATION = 0;

	private ModelInstance modelInstance;

	public Entity(Model model)
	{
		this.modelInstance = new ModelInstance(model);
	}

	public Entity(String modelPath)
	{
		this(readModel(modelPath));
	}

	protected void calculateTransforms()
	{
		getModelInstance().calculateTransforms();
	}

	@Override
	public void dispose()
	{
		getModel().dispose();
	}

	public BoundingBox getBoundingBox()
	{
		return getModelInstance().calculateBoundingBox(new BoundingBox());
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

	public Vector3 getLocation()
	{
		return getTransform().getTranslation(new Vector3());
	}

	public Model getModel()
	{
		return getModelInstance().model;
	}

	public ModelInstance getModelInstance()
	{
		return modelInstance;
	}

	public Vector3 getOriginalSize()
	{
		return getBoundingBox().getDimensions(new Vector3());
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

	public Quaternion getRoationQuat()
	{
		return getTransform().getRotation(new Quaternion());
	}

	public Node getRootNode()
	{
		return getModelInstance().nodes.get(ROOT_NODE_LOCATION);
	}

	public Vector3 getRotation()
	{
		Quaternion rotation = getRoationQuat();
		return new Vector3(rotation.getYaw(), rotation.getPitch(), rotation.getRoll());
	}

	public Vector3 getScale()
	{
		return getTransform().getScale(new Vector3());
	}

	public Vector3 getSize()
	{
		return getOriginalSize().scl(getScale());
	}

	public Matrix4 getTransform()
	{
		return getModelInstance().transform;
	}

	public boolean isVisible(Camera camera)
	{
		return camera.frustum.sphereInFrustum(getCenterLocation(), getRadius());
	}

	public void rotate(float yaw, float pitch, float roll)
	{
		getTransform().rotate(Vector3.Y, yaw);
		getTransform().rotate(Vector3.X, pitch);
		getTransform().rotate(Vector3.Z, roll);
	}

	public void rotate(Vector3 angles)
	{
		rotate(angles.x, angles.y, angles.z);
	}

	public void setLocation(Vector3 location)
	{
		getTransform().setToTranslation(location);
	}

	public void setRotation(float yaw, float pitch, float roll)
	{
		getTransform().setFromEulerAngles(yaw, pitch, roll);
	}

	public void setRotation(Vector3 angles)
	{
		setRotation(angles.x, angles.y, angles.z);
	}

	public void setScale(float scale)
	{
		setScale(new Vector3(scale, scale, scale));
	}

	public void setScale(Vector3 scale)
	{
		getRootNode().scale.set(scale);
		calculateTransforms();
	}

	public void setSize(Vector3 size)
	{
		setScale(GdxMath.divideVector(size, getOriginalSize()));
	}

	public void translate(Vector3 translation)
	{
		getTransform().translate(translation);
	}

	public void translateABS(Vector3 translation)
	{
		getTransform().trn(translation);
	}

	protected static Model readModel(String modelPath)
	{
		return GdxFileReader.loadModel(modelPath);
	}
}
