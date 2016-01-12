package com.jaxson.lib.gdx.states.renderer;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.jaxson.lib.gdx.graphics.g3d.Entity;
import com.jaxson.lib.gdx.graphics.g3d.MyEnvironment;

public class ModelRenderer extends Renderer<Entity>
{
	private Environment environment;

	public ModelRenderer()
	{
		super();
		this.environment = new MyEnvironment();
	}

	public void render(ModelBatch modelBatch, Camera camera)
	{
		if (modelBatch == null) return;
		if (camera == null) return;
		if (isEmpty()) return;

		enviroment.getShadowLight().begin(Vector3.Zero, camera.direction);
		enviroment.getShadowBatch().begin(shadowLight.getCamera());
		enviroment.getShadowBatch().render(objects);
		enviroment.getShadowBatch().end();
		enviroment.getShadowLight().end();

		modelBatch.begin(camera);
		for (Entity entity: objects)
		{
			if (entity.isVisible(camera)) modelBatch.render(entity.getModelInstance(), environment);
		}
		modelBatch.end();
	}
}
