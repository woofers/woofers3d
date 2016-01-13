package com.jaxson.lib.gdx.states.renderer;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.jaxson.lib.gdx.graphics.g3d.Entity;
import com.jaxson.lib.gdx.graphics.g3d.MyEnvironment;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;

public class ModelRenderer extends Renderer<Entity>
{
	private MyEnvironment environment;

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

		environment.getShadowLight().begin(Vector3.Zero, camera.direction);
		environment.getShadowBatch().begin(environment.getShadowLight().getCamera());
		for (Entity entity: objects)
		{
			environment.getShadowBatch().render(entity.getModelInstance());
		}
		environment.getShadowBatch().end();
		environment.getShadowLight().end();

		modelBatch.begin(camera);
		for (Entity entity: objects)
		{
			if (entity.isVisible(camera)) modelBatch.render(entity.getModelInstance(), environment);
		}
		modelBatch.end();
	}
}
