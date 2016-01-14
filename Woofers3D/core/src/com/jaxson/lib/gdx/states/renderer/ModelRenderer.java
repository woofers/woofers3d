package com.jaxson.lib.gdx.states.renderer;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.jaxson.lib.gdx.graphics.g3d.Entity;
import com.jaxson.lib.gdx.graphics.g3d.MyEnvironment;

public class ModelRenderer extends Renderer<Entity>
{
	private MyEnvironment environment;

	public ModelRenderer()
	{
		super();
		this.environment = new MyEnvironment();
	}

	public MyEnvironment getEnvironment()
	{
		return environment;
	}

	public void render(ModelBatch modelBatch, Camera camera)
	{
		if (modelBatch == null) return;
		if (camera == null) return;
		if (isEmpty()) return;
		environment.render(objects, camera);
		modelBatch.begin(camera);
		for (Entity entity: objects)
		{
			if (entity.isVisible(camera)) modelBatch.render(entity.getModelInstance(), environment);
		}
		modelBatch.end();
	}
}
