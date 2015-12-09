package com.jaxson.lib.gdx.graphics.g3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCapsuleShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.jaxson.lib.gdx.graphics.GameObject;
import java.lang.Math;

public abstract class Entity extends GameObject
{
	protected static final Vector3 LOCATION = Vector3.Zero;
	private static final btCollisionShape SHAPE = new btCapsuleShape(1f, 1f);
	private static final int COLLISON_FLAG = btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK;

	private ModelInstance modelInstance;
	private btCollisionObject body;

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
		this.body = new btCollisionObject();

		setCollisionShape(new btCapsuleShape(1f, 1f));
		setCollisionFlags(getCollisionFlags() | COLLISON_FLAG);
	}

	@Override
	public void dispose()
	{
		modelInstance.model.dispose();
	}

	public btCollisionObject getBody()
	{
		return body;
	}

	public BoundingBox getBoundingBox()
	{
		return modelInstance.calculateBoundingBox(new BoundingBox());
	}

	public int getCollisionFlags()
	{
		return body.getCollisionFlags();
	}

	public Vector3 getDirection()
	{
		float[] matrix = modelInstance.transform.getValues();
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
		return modelInstance.transform.getTranslation(new Vector3());
	}

	public Vector3 getCenterLocation()
	{
		return getRadius().add(getLocation());
	}

	public Vector3 getScale()
	{
		return modelInstance.transform.getScale(new Vector3());
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
		return modelInstance.transform.getRotation(new Quaternion());
	}

	public boolean hasBody()
	{
		return body != null;
	}

	public void rotate(Vector3 angles)
	{
		rotate(angles.x, angles.y, angles.z);
	}

	public void rotate(float yaw, float pitch, float roll)
	{
		modelInstance.transform.rotate(Vector3.Y, yaw);
		modelInstance.transform.rotate(Vector3.X, pitch);
		modelInstance.transform.rotate(Vector3.Z, roll);
		updateBody();
	}

	public void setCollisionFlags(int flags)
	{
		if (!hasBody()) return;
		body.setCollisionFlags(flags);
	}

	public void setCollisionShape(btCollisionShape shape)
	{
		if (!hasBody()) return;
		body.setCollisionShape(shape);
	}

	public void setLocation(Vector3 location)
	{
		modelInstance.transform.set(location, getRoationQuat());
		updateBody();
	}

	public void setRotation(Vector3 angles)
	{
		setRotation(angles.x, angles.y, angles.z);
	}

	public void setRotation(float yaw, float pitch, float roll)
	{
		modelInstance.transform.setFromEulerAngles(yaw, pitch, roll);
		updateBody();
	}

	public void setScale(Vector3 scale)
	{
		modelInstance.transform.scl(scale);
		updateBody();
	}

	public void translate(Vector3 translation)
	{
		modelInstance.transform.translate(translation);
		updateBody();
	}

	public void translateABS(Vector3 translation)
	{
		modelInstance.transform.trn(translation);
		updateBody();
	}

	private void updateBody()
	{
		if (!hasBody()) return;
		body.setWorldTransform(modelInstance.transform);
	}
}
