package com.jaxson.lib.gdx.graphics.g3d.environment.lighting;

import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;

public class ShadowBatch extends ModelBatch
{
	public ShadowBatch()
	{
		super(new DepthShaderProvider());
	}

	public void begin(MyDirectionalShadowLight light)
	{
		begin(light.getCamera());
	}
}
