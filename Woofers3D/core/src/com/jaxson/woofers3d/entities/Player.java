package com.jaxson.woofers3d.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.entities.Entity;
import com.jaxson.lib.gdx.entities.Box;
import com.jaxson.lib.gdx.graphics.MyPerspectiveCamera;
import com.jaxson.lib.gdx.util.MyInputProcessor;
import java.lang.Math;

public class Player extends Entity
{
	private static final float SPEED = 0.03f;
	private static final String PATH = "entities/dog/dog.obj";

	private MyPerspectiveCamera camera;

	public Player(MyPerspectiveCamera camera)
	{
		this(camera, LOCATION);
	}

	public Player(MyPerspectiveCamera camera, Vector3 location)
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
		if (MyInputProcessor.isDown(MyInputProcessor.LEFT))
		{
			translate(new Vector3(SPEED, 0f, 0f));
		}
		if (MyInputProcessor.isDown(MyInputProcessor.RIGHT))
		{
			translate(new Vector3(-SPEED, 0f, 0f));
		}
		if (MyInputProcessor.isDown(MyInputProcessor.FORWARD))
		{
			translate(new Vector3(0f, 0f, SPEED));
		}
		if (MyInputProcessor.isDown(MyInputProcessor.BACK))
		{
			translate(new Vector3(0f, 0f, -SPEED));
		}
		if (MyInputProcessor.isPressed(MyInputProcessor.SPACE))
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
