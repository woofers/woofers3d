package com.jaxson.woofers3d.entities;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btConvexShape;
import com.jaxson.lib.gdx.bullet.bodies.CameraPlayerBody;
import com.jaxson.lib.gdx.bullet.collision.BoxShape;
import com.jaxson.lib.gdx.graphics.cameras.TargetCamera;
import com.jaxson.lib.gdx.input.KeyHandler;

public class Player extends CameraPlayerBody
{
	private static final String PATH = "entities/ship/ship.g3db";
	private static final float MASS = -1f;
	private static final float SCALE = 2.1f;
	private static final Vector3 SIZE = new Vector3(1.3f, 0.33f, 1.5f).scl(0.8f);
	private static final btConvexShape SHAPE = new BoxShape(SIZE);

	public Player(TargetCamera camera)
	{
		super(PATH, SHAPE, MASS, camera);
		setScale(SCALE);
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	@Override
	protected void input()
	{
		super.input();
		if (KeyHandler.isPressed(Keys.O)) lockCamera();
		if (KeyHandler.isPressed(Keys.P)) unlockCamera();
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
	}
}
