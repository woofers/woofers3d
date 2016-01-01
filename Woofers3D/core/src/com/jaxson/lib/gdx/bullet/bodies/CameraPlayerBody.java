package com.jaxson.lib.gdx.bullet.bodies;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btConvexShape;
import com.jaxson.lib.gdx.bullet.bodies.PlayerBody;
import com.jaxson.lib.gdx.bullet.collision.BoxShape;
import com.jaxson.lib.gdx.graphics.cameras.TargetCamera;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.utils.UBJsonReader;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.Gdx;

public class CameraPlayerBody extends PlayerBody
{
	private static final float MASS = -1f;

	private TargetCamera camera;

	public CameraPlayerBody(String modelPath, btConvexShape shape, TargetCamera camera)
	{
		this(modelPath, shape, MASS, camera);
	}

	public CameraPlayerBody(String modelPath, btConvexShape shape, float mass, TargetCamera camera)
	{
		this(new G3dModelLoader(new UBJsonReader()).loadModel(Gdx.files.internal(modelPath)), shape, mass, camera);
	}

	public CameraPlayerBody(Model model, btConvexShape shape, TargetCamera camera)
	{
		this(model, shape, MASS, camera);
	}

	public CameraPlayerBody(Model model, btConvexShape shape, float mass, TargetCamera camera)
	{
		super(model, shape, mass);
		setCamera(camera);
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

	public boolean hasCamera()
	{
		return getCamera() != null;
	}

	public TargetCamera getCamera()
	{
		return camera;
	}

	public void lockCamera()
	{
		if (hasCamera()) getCamera().setTarget(null);
	}

	@Override
	protected void input()
	{
		super.input();
	}

	public void setCamera(TargetCamera camera)
	{
		if (camera == getCamera()) return;
		if (camera == null) lockCamera();
		this.camera = camera;
		unlockCamera();
	}

	public void unlockCamera()
	{
		if (hasCamera()) getCamera().setTarget(this);
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

	@Override
	public void update(float dt)
	{
		super.update(dt);
	}
}
