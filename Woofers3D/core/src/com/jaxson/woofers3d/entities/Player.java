package com.jaxson.woofers3d.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.entities.Entity;
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
		super(PATH);
		this.camera = camera;
		camera.setTarget(this);
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	@Override
	protected void input()
	{
		if (MyInputProcessor.isDown(MyInputProcessor.LEFT))
		{
			transform.trn(new Vector3(SPEED, 0f, 0f));
		}
		if (MyInputProcessor.isDown(MyInputProcessor.RIGHT))
		{
			transform.trn(new Vector3(-SPEED, 0f, 0f));
		}
		if (MyInputProcessor.isDown(MyInputProcessor.FORWARD))
		{
			transform.trn(new Vector3(0f, 0f, SPEED));
		}
		if (MyInputProcessor.isDown(MyInputProcessor.BACK))
		{
			transform.trn(new Vector3(0f, 0f, -SPEED));
		}
		if (MyInputProcessor.isPressed(MyInputProcessor.SPACE))
		{
			System.out.println("jump");
		}
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
	}
}
