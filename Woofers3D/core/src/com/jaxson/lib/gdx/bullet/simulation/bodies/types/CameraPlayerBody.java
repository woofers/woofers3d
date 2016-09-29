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

	public CameraPlayerBody(Model model, TargetCamera camera)
	{
		this(model, fittedHitbox(model), camera);
	}

	public boolean cameraIsLocked()
	{
		return !camera().hasTarget();
	}

	@Override
	public void dispose()
	{
		super.dispose();
		unlockCamera();
	}

	public TargetCamera camera()
	{
		return camera;
	}

	public boolean hasCamera()
	{
		return camera() != null;
	}

	public void lockCamera()
	{
		if (hasCamera()) camera().setTarget(null);
	}

	public void setCamera(TargetCamera camera)
	{
		if (camera == camera()) return;
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
		if (hasCamera()) camera().setTarget(this);
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
