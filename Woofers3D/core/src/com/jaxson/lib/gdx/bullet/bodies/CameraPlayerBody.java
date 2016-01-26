package com.jaxson.lib.gdx.bullet.bodies;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.physics.bullet.collision.btConvexShape;
import com.jaxson.lib.gdx.graphics.cameras.TargetCamera;
import com.jaxson.lib.gdx.input.InputHandler;
import com.badlogic.gdx.math.Vector3;

public class CameraPlayerBody extends PlayerBody
{
	private TargetCamera camera;

	public CameraPlayerBody(Model model, btConvexShape shape, TargetCamera camera)
	{
		super(model, shape);
		setCamera(camera);
	}

	public CameraPlayerBody(String modelPath, btConvexShape shape, TargetCamera camera)
	{
		this(readModel(modelPath), shape, camera);
	}

	public boolean cameraIsLocked()
	{
		return getCamera().hasTarget();
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

	@Override
	protected void input()
	{
		super.input();
		int scroll = 5;
		getCamera().setZoom(new Vector3(1f, scroll, scroll));
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

	public void toggleCameraLock()
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
}
