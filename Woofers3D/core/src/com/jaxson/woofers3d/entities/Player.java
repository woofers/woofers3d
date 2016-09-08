package com.jaxson.woofers3d.entities;

import com.jaxson.lib.gdx.bullet.simulation.bodies.types.CameraPlayerBody;
import com.jaxson.lib.gdx.graphics.views.TargetCamera;
import com.jaxson.lib.gdx.input.KeyboardKey;
import com.jaxson.lib.gdx.bullet.simulation.collision.BoxShape;
import com.badlogic.gdx.math.Vector3;

public class Player extends CameraPlayerBody
{
	private static final String PATH = "entities/ship/ship.g3db";
	private static final float SCALE = 2f;
	private static final float HITBOX_SCALE = 80f / 100f;

	private KeyboardKey cameraKey;

	public Player(TargetCamera camera)
	{
		super(PATH, camera);
		setCollisionShapeScale(HITBOX_SCALE);
		setScale(SCALE);
		//setCollisionShape(new BoxShape(new Vector3(1f, 2f, 3f)));

		this.cameraKey = getKeyboard().getKey("T");
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
