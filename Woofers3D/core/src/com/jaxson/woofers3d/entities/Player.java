package com.jaxson.woofers3d.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.graphics.cameras.TargetCamera;
import com.jaxson.lib.gdx.graphics.g3d.Box;
import com.jaxson.lib.gdx.graphics.g3d.Entity;
import com.jaxson.lib.gdx.input.KeyHandler;

import java.lang.Math;

public class Player extends Entity
{
	private static final float SPEED = 0.03f;
	private static final String PATH = "entities/dog/dog.obj";

	private TargetCamera camera;

	public Player(TargetCamera camera)
	{
		this(camera, LOCATION);
	}

	public Player(TargetCamera camera, Vector3 location)
	{
		super(PATH, location);
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
			translate(new Vector3(0f, 0f, SPEED));
		}
		if (KeyHandler.isDown(KeyHandler.BACK))
		{
			translate(new Vector3(0f, 0f, -SPEED));
		}
		if (KeyHandler.isPressed(KeyHandler.SPACE))
		{
			//camera.center();
			System.out.println(camera.getRotation());
		}
		System.out.println(getLocation());
		translate(new Vector3(0, -1f / 120f, 0));
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
	}
}
