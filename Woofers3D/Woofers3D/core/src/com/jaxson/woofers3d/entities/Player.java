package com.jaxson.woofers3d.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCapsuleShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.jaxson.lib.gdx.graphics.cameras.TargetCamera;
import com.jaxson.lib.gdx.graphics.g3d.RigidBody;
import com.jaxson.lib.gdx.input.KeyHandler;

import java.lang.Math;

public class Player extends RigidBody
{
	private static final float SPEED = 0.03f;
	private static final String PATH = "entities/dog/dog.obj";
	private static final btCollisionShape SHAPE = new btCapsuleShape(1f, 1f);
	private static final float MASS = 100f;

	private TargetCamera camera;

	public Player(TargetCamera camera)
	{
		super(PATH, SHAPE, MASS);
		this.camera = camera;
		camera.setTarget(this);
	}

	@Override
	public void dispose()
	{
		super.dispose();
		camera.setTarget(null);
	}

	@Override
	protected void input()
	{
		Vector3 rotation = getRotation();
		if (KeyHandler.isDown(KeyHandler.LEFT))
		{
			translate(new Vector3(SPEED, 0f, 0f));
		}
		if (KeyHandler.isDown(KeyHandler.RIGHT))
		{
			translate(new Vector3(-SPEED, 0f, 0f));
		}
		if (KeyHandler.isDown(KeyHandler.FORWARD))
		{
			translateABS(new Vector3(0f, 0f, SPEED));
		}
		if (KeyHandler.isDown(KeyHandler.BACK))
		{
			translate(new Vector3(0f, 0f, -SPEED));
		}
		if (KeyHandler.isPressed(KeyHandler.SPACE))
		{
			//camera.center();
		}
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
	}
}
