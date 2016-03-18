package com.jaxson.lib.gdx.graphics.g3d.environment.light;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector3;

public abstract class BaseLight<L extends DirectionalLight> implements Light
{
	private L light;

	public BaseLight(L light)
	{
		this.light = light;
	}

	public abstract BaseLight<L> copy();

	@Override
	public Color getColor()
	{
		return getLight().color;
	}

	@Override
	public Vector3 getDirection()
	{
		return getLight().direction;
	}

	@Override
	public L getLight()
	{
		return light;
	}

	@Override
	public abstract boolean hasShadows();

	public void set(Color color, Vector3 direction)
	{
		getLight().set(color, direction);
	}

	public void setColor(Color color)
	{
		set(color, getDirection());
	}

	public void setDirection(Vector3 direction)
	{
		getLight().setDirection(direction);
	}

	@Override
	public abstract MyDirectionalLight toLight();

	@Override
	public abstract MyDirectionalShadowLight toShadow();
}
