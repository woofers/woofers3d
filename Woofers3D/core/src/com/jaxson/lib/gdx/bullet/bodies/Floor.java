package com.jaxson.lib.gdx.bullet.bodies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.MathUtils;
import com.jaxson.lib.gdx.util.GdxMath;

public class Floor extends RigidBox
{
	private static final float THICKNESS = 1f / 4f;
	private static final float WIDTH = 100f;
	private static final float HEIGHT = 100f;
	private static final float MASS = 0f;

	private float angle;

	public Floor()
	{
		this(WIDTH, HEIGHT, COLOR);
	}

	public Floor(float width, float height)
	{
		this(width, height, COLOR);
	}

	public Floor(float width, float height, Color color)
	{
		super(color);
		this.angle = 90f;
		setMass(MASS);
		setScale(new Vector3(width, THICKNESS, height));
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	@Override
	public void update(float dt)
	{
		//angle = (angle + dt * 90f) % 360f;
		//translate(new Vector3(0, MathUtils.sinDeg(angle) * 2.5f, 0f));
	}
}
