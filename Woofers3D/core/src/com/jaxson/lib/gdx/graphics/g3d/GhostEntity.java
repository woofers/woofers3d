package com.jaxson.lib.gdx.graphics.g3d;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public abstract class GhostEntity extends Entity
{
	public GhostEntity(String modelPath)
	{
		super(modelPath);
	}

	public GhostEntity(String modelPath, Vector3 location)
	{
		super(modelPath, location);
	}

	public GhostEntity(Model model)
	{
		super(model);
	}

	public GhostEntity(Model model, Vector3 location)
	{
		super(model, location);
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
		getTransform().setToTranslation(location);
	}

	public void setScale(Vector3 scale)
	{
		getTransform().scale(scale.x, scale.y, scale.z);
	}

	public void setSize(Vector3 size)
	{
		Vector3 oldSize = getOriginalSize();
		System.out.println(oldSize.toString());
		oldSize = size.scl(1f / oldSize.x, 1f / oldSize.y, 1f / oldSize.z);
		System.out.println(oldSize.toString());
		setScale(oldSize);
	}

	public void setRotation(Vector3 angles)
	{
		setRotation(angles.x, angles.y, angles.z);
	}

	public void setRotation(float yaw, float pitch, float roll)
	{
		getTransform().setFromEulerAngles(yaw, pitch, roll);
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
