package com.jaxson.lib.gdx.graphics.g3d.environment;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector3;

public class MyDirectionalLight extends DirectionalLight implements Light
{
	public MyDirectionalLight()
	{
		this(Light.DIRECTION);
	}

	public MyDirectionalLight(Color color, Vector3 direction)
	{
		super();
		set(color, direction);
	}

	public MyDirectionalLight(Vector3 direction)
	{
		this(Light.COLOR, direction);
	}

	@Override
	public Color getColor()
	{
		return color;
	}

	@Override
	public Vector3 getDirection()
	{
		return direction;
	}

	@Override
	public DirectionalLight getLight()
	{
		return this;
	}

	@Override
	public boolean hasShadows()
	{
		return false;
	}

	@Override
	public MyDirectionalLight toLight()
	{
		return this;
	}

	@Override
	public MyDirectionalShadowLight toShadow()
	{
		return new MyDirectionalShadowLight(getColor(), getDirection());
	}
}
