package com.jaxson.lib.gdx.graphics.g3d.environment.lighting;

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

	@Override
	public Color color()
	{
		return light().color;
	}

	public abstract BaseLight<L> copy();

	@Override
	public Vector3 direction()
	{
		return light().direction;
	}

	@Override
	public abstract boolean hasShadows();

	@Override
	public L light()
	{
		return light;
	}

	public void set(Color color, Vector3 direction)
	{
		light().set(color, direction);
	}

	public void setColor(Color color)
	{
		set(color, direction());
	}

	public void setDirection(Vector3 direction)
	{
		light().setDirection(direction);
	}

	@Override
	public abstract MyDirectionalLight toLight();

	@Override
	public abstract MyDirectionalShadowLight toShadow();
}
