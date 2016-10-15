package com.jaxson.woofers3d.entities;

import com.jaxson.lib.gdx.bullet.simulation.bodies.types.CameraPlayerBody;
import com.jaxson.lib.gdx.graphics.views.TargetCamera;
import com.jaxson.lib.gdx.input.KeyboardKey;
import com.jaxson.lib.gdx.io.GdxFile;

public class Player extends CameraPlayerBody
{
	private static final String PATH = "entities/ship/ship.obj";
	private static final float SCALE = 2f;
	private static final float HITBOX_SCALE = 80f / 100f;

	private KeyboardKey cameraKey;

	public Player(TargetCamera camera)
	{
		super(new GdxFile(PATH), camera);
		setCollisionShapeScale(HITBOX_SCALE);
		scale(SCALE);

		this.cameraKey = keyboard().key("T");
	}

	@Override
	public void dispose()
	{
		super.dispose();
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
		if (cameraKey.isPressed()) toggleCamera();
	}
}
