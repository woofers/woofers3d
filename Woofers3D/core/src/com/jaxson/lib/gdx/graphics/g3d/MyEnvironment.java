package com.jaxson.lib.gdx.graphics.g3d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;

public class MyEnvironment extends Environment
{
	private static final long LIGHT_TYPE = ColorAttribute.AmbientLight;
	private static final Color LIGHT_COLOR = new Color(0.8f, 0.8f, 0.8f, 1f);
	private static final Vector3 LIGHT_DIRECTION = new Vector3(-1f, -0.8f, -0.2f);

	private DirectionalShadowLight shadowLight;
	private ModelBatch shadowBatch;

	public MyEnvironment()
	{
		super();
		set(new ColorAttribute(LIGHT_TYPE, LIGHT_COLOR));
		shadowLight = new DirectionalShadowLight(1024, 1024, 40f, 40f, 1f, 300f);
		shadowLight.set(LIGHT_COLOR, LIGHT_DIRECTION);
		add(shadowLight);
		this.shadowMap = shadowLight;
		this.shadowBatch = new ModelBatch(new DepthShaderProvider());
	}

	public DirectionalShadowLight getShadowLight()
	{
		return shadowLight;
	}

	public ModelBatch getShadowBatch()
	{
		return shadowBatch;
	}
}
