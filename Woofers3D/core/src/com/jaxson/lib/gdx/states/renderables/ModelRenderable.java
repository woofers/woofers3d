package com.jaxson.lib.gdx.states.renderables;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.jaxson.lib.gdx.graphics.g3d.Entity;
import com.jaxson.lib.gdx.graphics.g3d.MyEnvironment;

public class ModelRenderable extends Renderable<Entity>
{
	private Environment environment;

	public ModelRenderable()
	{
		super();
		this.environment = new MyEnvironment();
	}

	public void render(ModelBatch modelBatch, Camera camera)
	{
		if (modelBatch == null)
			return;
		if (camera == null)
			return;
		if (isEmpty())
			return;
		modelBatch.begin(camera);
		for (Entity entity : objects)
		{
			if (entity.isVisible(camera))
				modelBatch.render(entity.getModelInstance(), environment);
		}
		modelBatch.end();
	}
}
