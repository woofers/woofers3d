package com.jaxson.lib.gdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import java.lang.Math;

public abstract class Entity extends ModelInstance
{
	protected static final Vector3 LOCATION = Vector3.Zero;

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
		super(model, location);
	}

	public void dispose()
	{
		model.dispose();
	}

	public BoundingBox getBoundingBox()
	{
		return calculateBoundingBox(new BoundingBox());
	}

	public Vector3 getDirection()
	{
		float[] matrix = transform.getValues();
		return new Vector3(matrix[8], matrix[9], matrix[10]);
	}

	public Model getModel()
	{
		return model;
	}

	public Vector3 getLocation()
	{
		float[] matrix = transform.getValues();
		return new Vector3(matrix[12], matrix[13], matrix[14]);
	}

	public Vector3 getCenterLocation()
	{
		return getRadius().add(getLocation());
	}

	public Vector3 getScale()
	{
		return transform.getScale(new Vector3());
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
		return transform.getRotation(new Quaternion());
	}

	protected void input()
	{

	}

	public void rotate(Vector3 angles)
	{
		rotate(angles.x, angles.y, angles.z);
	}

	public void rotate(float yaw, float pitch, float roll)
	{
		transform.rotate(Vector3.Y, yaw);
		transform.rotate(Vector3.X, pitch);
		transform.rotate(Vector3.Z, roll);
	}

	public void setLocation(Vector3 location)
	{
		transform.set(location, getRoationQuat());
	}

	public void setRotation(Vector3 angles)
	{
		setRotation(angles.x, angles.y, angles.z);
	}

	public void setRotation(float yaw, float pitch, float roll)
	{
		transform.setFromEulerAngles(yaw, pitch, roll);
	}

	public void setScale(Vector3 scale)
	{
		transform.scl(scale);
	}

	public void translate(Vector3 translation)
	{
		transform.translate(translation);
	}

	public void translateABS(Vector3 translation)
	{
		transform.trn(translation);
	}

	public void update(float dt)
	{
		input();
	}
}
