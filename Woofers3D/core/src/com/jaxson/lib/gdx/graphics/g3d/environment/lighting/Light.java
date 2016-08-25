package com.jaxson.lib.gdx.graphics.g3d.environment.lighting;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.graphics.color.MyColor;

public interface Light
{
	public static final long TYPE = ColorAttribute.AmbientLight;
	public static final Color COLOR = new MyColor(200, 200, 200);
	public static final Vector3 DIRECTION = new Vector3(-1f, -0.8f, -0.2f);

	public Color getColor();

	public Vector3 getDirection();

	public DirectionalLight getLight();

	public boolean hasShadows();

	public MyDirectionalLight toLight();

	public MyDirectionalShadowLight toShadow();
}
