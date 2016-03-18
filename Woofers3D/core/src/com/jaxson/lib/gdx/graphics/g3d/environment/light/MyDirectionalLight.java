package com.jaxson.lib.gdx.graphics.g3d.environment.light;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector3;

public class MyDirectionalLight extends BaseLight<DirectionalLight>
{
	public MyDirectionalLight()
	{
		this(Light.DIRECTION);
	}

	public MyDirectionalLight(BaseLight<?> light)
	{
		this(light.getColor(), light.getDirection());
	}

	public MyDirectionalLight(Color color, Vector3 direction)
	{
		this(new DirectionalLight().set(color, direction));
	}

	public MyDirectionalLight(DirectionalLight light)
	{
		super(light);
	}

	public MyDirectionalLight(Vector3 direction)
	{
		this(Light.COLOR, direction);
	}

	@Override
	public MyDirectionalLight copy()
	{
		return new MyDirectionalLight(this);
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
		return new MyDirectionalShadowLight(this);
	}
}
