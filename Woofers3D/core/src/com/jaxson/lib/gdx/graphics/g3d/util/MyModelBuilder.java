package com.jaxson.lib.gdx.graphics.g3d.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

public class MyModelBuilder extends ModelBuilder
{
	private static final long ATTRIBUTES
	= VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal;

	private static final Vector3 SIZE = new Vector3(1f, 1f, 1f);
	private static final int DIVISIONS = 16;

	public MyModelBuilder()
	{
		super();
	}

	public Model createBox(Color color)
	{
		return createBox(SIZE, color);
	}

	public Model createBox(Vector3 size, Color color)
	{
		return createBox(size.x, size.y, size.z, new Material(ColorAttribute
		.createDiffuse(color)), ATTRIBUTES);
	}

	public Model createSphere(Color color)
	{
		return createSphere(SIZE, color);
	}

	public Model createSphere(Vector3 size, Color color)
	{
		return createSphere(size.x, size.y, size.z, DIVISIONS, DIVISIONS,
		new Material(ColorAttribute.createDiffuse(color)), ATTRIBUTES);
	}
}
