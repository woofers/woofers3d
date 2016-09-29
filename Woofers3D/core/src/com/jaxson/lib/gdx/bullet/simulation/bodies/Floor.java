package com.jaxson.lib.gdx.bullet.simulation.bodies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.graphics.color.MyColor;

public class Floor extends RigidBox
{
	private static final float THICKNESS = 1f / 4f;
	private static final float WIDTH = 100f;
	private static final float HEIGHT = 100f;
	private static final float MASS = 0f;
	private static final Color COLOR = new MyColor(102, 107, 121);

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
		setMass(MASS);
		scale(new Vector3(width, THICKNESS, height));
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	@Override
	public void update(float dt)
	{

	}
}
