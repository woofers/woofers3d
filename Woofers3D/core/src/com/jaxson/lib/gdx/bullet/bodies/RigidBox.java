package com.jaxson.lib.gdx.bullet.bodies;

import com.badlogic.gdx.graphics.Color;
import com.jaxson.lib.gdx.graphics.MyColor;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btConvexShape;
import com.jaxson.lib.gdx.graphics.g3d.util.MyModelBuilder;

public class RigidBox extends RigidBody
{
	protected static final Color COLOR = new MyColor(81, 101, 107);
	private static final btConvexShape SHAPE = new btBoxShape(new Vector3(1f, 1f, 1f));
	private static final float MASS = 0f;

	public RigidBox()
	{
		this(COLOR);
	}

	public RigidBox(Color color)
	{
		super(new MyModelBuilder().createBox(color), SHAPE, MASS);
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
