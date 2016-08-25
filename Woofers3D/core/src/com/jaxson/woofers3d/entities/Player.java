package com.jaxson.woofers3d.entities;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.bullet.simulation.bodies.types.CameraPlayerBody;
import com.jaxson.lib.gdx.bullet.simulation.collision.BoxShape;
import com.jaxson.lib.gdx.bullet.simulation.collision.types.ConvexShape;
import com.jaxson.lib.gdx.graphics.cameras.TargetCamera;
import com.jaxson.lib.gdx.input.InputHandler;

public class Player extends CameraPlayerBody
{
	private static final String PATH = "entities/ship/ship.g3db";
	private static final float SCALE = 2.1f;
	private static final float HITBOX_SCALE = 85f / 100f;
	private static final Vector3 SIZE = new Vector3(1.3f, 0.33f, 1.5f);
	private static final ConvexShape SHAPE = new BoxShape(SIZE);

	public Player(TargetCamera camera)
	{
		super(PATH, SHAPE, camera);
		setCollisionShapeScale(HITBOX_SCALE);
		setScale(SCALE);
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
		if (InputHandler.isPressed(Keys.T)) toggleCamera();
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
	}
}
