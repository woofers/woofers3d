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
	private static final long ATTRIBUTES = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal;
	private static final Vector3 SIZE = new Vector3(1f, 1f, 1f);

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
		return createBox(size.x, size.y, size.z, new Material(ColorAttribute.createDiffuse(color)), ATTRIBUTES);
	}
}
