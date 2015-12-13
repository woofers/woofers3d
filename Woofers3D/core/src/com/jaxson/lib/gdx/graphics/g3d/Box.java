package com.jaxson.lib.gdx.graphics.g3d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btConvexShape;
import com.jaxson.lib.gdx.bullet.bodies.RigidBody;

public class Box extends RigidBody
{
	private static final Color COLOR = Color.ORANGE;
	private static final Vector3 SIZE = new Vector3(1f, 5f, 1f);
	private static final Vector3 SCALE = new Vector3(100f, 1f, 100f);
	private static final long ATTRIBUTES = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal;
	private static final btConvexShape SHAPE = new btBoxShape(new Vector3(100f, 1f, 100f));

	private static final float MASS = 0f;

	public Box()
	{
		this(COLOR);
	}

	public Box(Color color)
	{
		this(color, SCALE);
	}

	public Box(Color color, Vector3 scale)
	{
		this(color, scale, LOCATION);
	}

	public Box(Vector3 scale)
	{
		this(COLOR, scale);
	}

	public Box(Vector3 scale, Vector3 location)
	{
		this(COLOR, scale, location);
	}

	public Box(Color color, Vector3 scale, Vector3 location)
	{
		super(new ModelBuilder().createBox(SIZE.x, SIZE.y, SIZE.z, new Material(ColorAttribute.createDiffuse(color)), ATTRIBUTES), SHAPE, MASS);
		setScale(scale);
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
