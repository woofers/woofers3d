package com.jaxson.lib.gdx.bullet.bodies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.physics.bullet.collision.btConvexShape;
import com.badlogic.gdx.utils.UBJsonReader;
import com.jaxson.lib.gdx.graphics.cameras.TargetCamera;

public class CameraPlayerBody extends PlayerBody
{
	private static final float MASS = -1f;
	private TargetCamera camera;

	public CameraPlayerBody(Model model, btConvexShape shape, float mass, TargetCamera camera)
	{
		super(model, shape, mass);
		setCamera(camera);
	}

	public CameraPlayerBody(Model model, btConvexShape shape, TargetCamera camera)
	{
		this(model, shape, MASS, camera);
	}

	public CameraPlayerBody(String modelPath, btConvexShape shape, float mass, TargetCamera camera)
	{
		this(new G3dModelLoader(new UBJsonReader()).loadModel(Gdx.files.internal(modelPath)), shape, mass, camera);
	}

	public CameraPlayerBody(String modelPath, btConvexShape shape, TargetCamera camera)
	{
		this(modelPath, shape, MASS, camera);
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
