package com.jaxson.lib.gdx.graphics.g3d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.jaxson.lib.gdx.graphics.g3d.util.MyModelBuilder;

public class Box extends Entity
{
	private static final Color COLOR = Color.ORANGE;

	public Box()
	{
		this(COLOR);
	}

	public Box(Color color)
	{
		super(getModel(color));
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

	protected static Model getModel(Color color)
	{
		return new MyModelBuilder().createBox(color);
	}
}
