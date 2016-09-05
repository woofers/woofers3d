package com.jaxson.lib.gdx.bullet.simulation.bodies.types;

import com.badlogic.gdx.graphics.g3d.Model;
import com.jaxson.lib.gdx.bullet.simulation.collision.types.ConvexShape;
import com.jaxson.lib.gdx.graphics.views.TargetCamera;

public class CameraPlayerBody extends PlayerBody
{
	private TargetCamera camera;

	public CameraPlayerBody(Model model, ConvexShape shape, TargetCamera camera)
	{
		super(model, shape);
		setCamera(camera);
	}

	public CameraPlayerBody(String modelPath, ConvexShape shape, TargetCamera camera)
	{
		this(readModel(modelPath), shape, camera);
	}

	public boolean cameraIsLocked()
	{
		return !getCamera().hasTarget();
	}

	@Override
	public void dispose()
	{
		super.dispose();
		unlockCamera();
	}

	public TargetCamera getCamera()
	{
		return camera;
	}

	public boolean hasCamera()
	{
		return getCamera() != null;
	}

	public void lockCamera()
	{
		if (hasCamera()) getCamera().setTarget(null);
	}

	public void setCamera(TargetCamera camera)
	{
		if (camera == getCamera()) return;
		if (camera == null) lockCamera();
		this.camera = camera;
		unlockCamera();
	}

	public void toggleCamera()
	{
		if (cameraIsLocked())
		{
			unlockCamera();
		}
		else
		{
			lockCamera();
		}
	}

	public void unlockCamera()
	{
		if (hasCamera()) getCamera().setTarget(this);
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
	}

	@Override
	protected void input(float dt)
	{
		super.input(dt);
	}
}
