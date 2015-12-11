package com.jaxson.lib.gdx.graphics.g3d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.math.Vector3;

public class MyEnvironment extends Environment
{
	private static final long LIGHT_TYPE         = ColorAttribute.AmbientLight;
	private static final Color LIGHT_COLOR       = new Color(0.8f, 0.8f, 0.8f, 1f);
	private static final Vector3 LIGHT_DIRECTION = new Vector3(-1f, -0.8f, -0.2f);

	public MyEnvironment()
	{
		super();
		set(new ColorAttribute(LIGHT_TYPE, LIGHT_COLOR));
		add(new DirectionalLight().set(LIGHT_COLOR, LIGHT_DIRECTION));
	}
}
