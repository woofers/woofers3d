package com.jaxson.woofers3d.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.entities.Entity;
import com.jaxson.lib.gdx.util.MyInputProcessor;
import java.lang.Math;

public class Player extends Entity
{
	private static final String PATH = "entities/dog/dog.obj";
	private Camera camera;

	public Player(Camera camera)
	{
		super(PATH);
		this.camera = camera;
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	@Override
	protected void input()
	{
		final float speed = 0.03f;
		if (MyInputProcessor.isDown(MyInputProcessor.LEFT))
		{
			transform.trn(new Vector3(speed, 0f, 0f));
		}
		if (MyInputProcessor.isDown(MyInputProcessor.RIGHT))
		{
			transform.trn(new Vector3(-speed, 0f, 0f));
		}
		if (MyInputProcessor.isDown(MyInputProcessor.FORWARD))
		{
			transform.trn(new Vector3(0f, 0f, speed));
		}
		if (MyInputProcessor.isDown(MyInputProcessor.BACK))
		{
			transform.trn(new Vector3(0f, 0f, -speed));
		}
		if (MyInputProcessor.isReleased(MyInputProcessor.PAUSE))
		{
			System.out.println("pause");
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
