package com.jaxson.lib.gdx.bullet.bodies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.jaxson.lib.gdx.bullet.collision.BoxShape;
import com.jaxson.lib.gdx.graphics.MyColor;
import com.jaxson.lib.gdx.graphics.g3d.util.MyModelBuilder;

public class RigidBox extends RigidBody
{
	protected static final Color COLOR = new MyColor(81, 101, 107);
	private static final float MASS = 1f;

	protected static Model getModel(Color color)
	{
		return new MyModelBuilder().createBox(color);
	}

	protected static BoxShape getShape()
	{
		return new BoxShape();
	}

	public RigidBox()
	{
		this(COLOR);
	}

	public RigidBox(Color color)
	{
		super(getModel(color), getShape());
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
