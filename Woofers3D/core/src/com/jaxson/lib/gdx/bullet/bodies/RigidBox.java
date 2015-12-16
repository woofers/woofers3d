package com.jaxson.lib.gdx.bullet.bodies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btConvexShape;
import com.jaxson.lib.gdx.bullet.collision.BoxShape;
import com.jaxson.lib.gdx.graphics.g3d.util.MyModelBuilder;
import com.jaxson.lib.gdx.graphics.MyColor;

public class RigidBox extends RigidBody
{
	protected static final Color COLOR = new MyColor(81, 101, 107);
	private static final float MASS = 1f;
	private final btConvexShape SHAPE = new BoxShape(new Vector3(1f, 1f, 1f));

	public RigidBox()
	{
		this(COLOR);
	}

	public RigidBox(Color color)
	{
		super(new MyModelBuilder().createBox(color), new BoxShape(new Vector3(1f, 1f, 1f)), MASS);
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
