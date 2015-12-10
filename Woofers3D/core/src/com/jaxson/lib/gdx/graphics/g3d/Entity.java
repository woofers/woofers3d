package com.jaxson.lib.gdx.graphics.g3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCapsuleShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.jaxson.lib.gdx.graphics.GameObject;
import com.jaxson.lib.gdx.math.collision.MyMotionState;
import java.lang.Math;

public abstract class Entity extends GameObject
{
	protected static final Vector3 LOCATION = Vector3.Zero;

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
		modelInstance.model.dispose();
	}

	public BoundingBox getBoundingBox()
	{
		return modelInstance.calculateBoundingBox(new BoundingBox());
	}

	public Vector3 getCenterLocation()
	{
		return getRadius().add(getLocation());
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

	public Vector3 getRadius()
	{
		return getBoundingBox().getCenter(new Vector3());
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

	public void rotate(Vector3 angles)
	{
		rotate(angles.x, angles.y, angles.z);
	}

	public void rotate(float yaw, float pitch, float roll)
	{
		getTransform().rotate(Vector3.Y, yaw);
		getTransform().rotate(Vector3.X, pitch);
		getTransform().rotate(Vector3.Z, roll);
	}

	public void setLocation(Vector3 location)
	{
		getTransform().set(location, getRoationQuat());
	}

	public void setRotation(Vector3 angles)
	{
		setRotation(angles.x, angles.y, angles.z);
	}

	public void setRotation(float yaw, float pitch, float roll)
	{
		getTransform().setFromEulerAngles(yaw, pitch, roll);
	}

	public void setScale(Vector3 scale)
	{
		modelInstance.transform.scl(scale);
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
