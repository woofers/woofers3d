package com.jaxson.woofers3d.entities;

import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;

public class Player extends Entity
{
	private static final String PATH = "entities/dog/dog.obj";

	public Player()
	{
		super(PATH);
		// /transform.scale(0.1f, 0.1f, 0.1f);
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
