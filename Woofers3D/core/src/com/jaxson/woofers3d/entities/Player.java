package com.jaxson.woofers3d.entities;

import com.jaxson.lib.gdx.bullet.simulation.bodies.types.CameraPlayerBody;
import com.jaxson.lib.gdx.graphics.views.TargetCamera;
import com.jaxson.lib.gdx.input.KeyboardKey;
import com.jaxson.lib.gdx.io.GdxFile;
import com.jaxson.lib.gdx.bullet.simulation.collision.BoxShape;
import com.badlogic.gdx.math.Vector3;

public class Player extends CameraPlayerBody
{
	private static final String PATH = "entities/dog/fbx/dog.g3db";
	private static final float SCALE = 4f;
	private static final float HITBOX_SCALE = 50f / 100f;

	private KeyboardKey cameraKey;

	public Player(TargetCamera camera)
	{
		super(new GdxFile(PATH), new BoxShape(
				new Vector3(0.49f, 0.66f * 0.75f, 0.90f)), camera);
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
	protected void input(float dt)
	{
		super.input(dt);
		if (cameraKey.isPressed()) toggleCamera();
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
	}
}
