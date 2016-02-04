package com.jaxson.lib.gdx.bullet.bodies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.bullet.collision.SphereShape;
import com.jaxson.lib.gdx.graphics.MyColor;
import com.jaxson.lib.gdx.graphics.g3d.util.MyModelBuilder;

public class RigidSphere extends RigidBody
{
	protected static final Color COLOR = new MyColor(81, 101, 107);

	public RigidSphere()
	{
		this(COLOR);
	}

	public RigidSphere(Color color)
	{
		super(getModel(color), getShape());
	}

	protected static Model getModel(Color color)
	{
		return new MyModelBuilder().createSphere(color);
	}

	protected static Model getModel(Vector3 size, Color color)
	{
		return new MyModelBuilder().createSphere(size, color);
	}

	protected static SphereShape getShape()
	{
		return new SphereShape();
	}
}
