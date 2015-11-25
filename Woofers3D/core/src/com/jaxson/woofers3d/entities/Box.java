package com.jaxson.woofers3d.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.math.Vector3;

public class Box extends Entity
{
	private static final Color COLOR = Color.ORANGE;
	private static final Vector3 SIZE = new Vector3(2f, 2f, 2f);
	private static final long ATTRIBUTES = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal;

	public Box()
	{
		this(COLOR);
	}

	public Box(Color color)
	{
		this(color, LOCATION);
	}

	public Box(Vector3 location)
	{
		this(COLOR, location);
	}

	public Box(Color color, Vector3 location)
	{
		super(new ModelBuilder().createBox(SIZE.x, SIZE.y, SIZE.x, new Material(ColorAttribute.createDiffuse(color)), ATTRIBUTES));
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	@Override
	protected void handleInput()
	{

	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
	}
}
