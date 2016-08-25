package com.jaxson.lib.gdx.graphics.g3d.entities.types;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import com.jaxson.lib.gdx.io.GdxFile;
import com.jaxson.lib.gdx.math.GdxMath;
import com.jaxson.lib.gdx.util.GameObject;
import com.jaxson.lib.math.MyMath;

public abstract class Entity extends GameObject
{
	private static final int MATRIX_DIRECTION_X = 8;
	private static final int MATRIX_DIRECTION_Y = 9;
	private static final int MATRIX_DIRECTION_Z = 10;
	private static final int ROOT_NODE_LOCATION = 0;
	private static final float FORWARD_DIRECTION = 0f;
	private static final float BACKWARD_DIRECTION = 180f;

	protected static Model readModel(String modelPath)
	{
		return new GdxFile(modelPath).readModel();
	}

	private ModelInstance modelInstance;

	public Entity(Model model)
	{
		this(new ModelInstance(model));
	}

	public Entity(ModelInstance modelInstance)
	{
		this.modelInstance = modelInstance;
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

	public Ray getBackwardRay()
	{
		return getRay(BACKWARD_DIRECTION);
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
		return getLocation().sub(location);
	}

	public float getDiameter()
	{
		return getSize().len();
	}

	public Vector3 getDirection()
	{
		float[] matrix = getTransformValues();
		return new Vector3(matrix[MATRIX_DIRECTION_X], matrix[MATRIX_DIRECTION_Y], matrix[MATRIX_DIRECTION_Z]);
	}

	public Ray getForwardRay()
	{
		return getRay(FORWARD_DIRECTION);
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

	public Ray getRay(float direction)
	{
		return new Ray(getLocation(), getDirection().rotate(Vector3.Y, direction));
	}

	public Ray getRay(Vector3 location)
	{
		return new Ray(getLocation(), getDeltaLocation(location));
	}

	public Quaternion getRoationQuaternion()
	{
		return getTransform().getRotation(new Quaternion());
	}

	public Node getRootNode()
	{
		return getModelInstance().nodes.get(ROOT_NODE_LOCATION);
	}

	public Vector3 getRotation()
	{
		Quaternion rotation = getRoationQuaternion();
		return new Vector3(rotation.getYaw(), rotation.getPitch(), rotation.getRoll());
	}

	public Vector3 getScale()
	{
		return getRootNode().scale;
	}

	public Vector3 getSize()
	{
		return getOriginalSize().scl(getScale());
	}

	public Matrix4 getTransform()
	{
		return getModelInstance().transform;
	}

	public float[] getTransformValues()
	{
		return getTransform().getValues();
	}

	public boolean isVisible(Camera camera)
	{
		return camera.frustum.sphereInFrustum(getCenterLocation(), getRadius());
	}

	public void rotate(float yaw, float pitch, float roll)
	{
		rotate(Vector3.X, pitch);
		rotate(Vector3.Y, yaw);
		rotate(Vector3.Z, roll);
	}

	public void rotate(Vector3 angles)
	{
		rotate(angles.x, angles.y, angles.z);
	}

	public void rotate(Vector3 axis, float amount)
	{
		getTransform().rotate(axis, amount);
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
}
