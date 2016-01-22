package com.jaxson.lib.gdx.bullet.bodies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.jaxson.lib.gdx.bullet.collision.BoxShape;
import com.jaxson.lib.gdx.graphics.MyColor;
import com.jaxson.lib.gdx.graphics.g3d.util.MyModelBuilder;
import com.badlogic.gdx.physics.bullet.collision.btSphereShape;
import com.jaxson.lib.gdx.bullet.collision.SphereShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;

public class RigidSphere extends RigidBody
{
	protected static final Color COLOR = new MyColor(81, 101, 107);
	private static final float MASS = 1f;

	public RigidSphere()
	{
		this(COLOR);
	}

	public RigidSphere(Color color)
	{
		super(getModel(color), getShape());
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	protected static Model getModel(Color color)
	{
		return new MyModelBuilder().createSphere(color);
	}

	protected static SphereShape getShape()
	{
		return new SphereShape();
	}
}
