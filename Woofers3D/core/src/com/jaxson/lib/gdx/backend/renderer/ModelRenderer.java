package com.jaxson.lib.gdx.backend.renderer;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.jaxson.lib.gdx.graphics.g3d.entities.types.Entity;
import com.jaxson.lib.gdx.graphics.g3d.environment.MyEnvironment;

public class ModelRenderer extends Renderer<Entity>
{
	private MyEnvironment environment;

	public ModelRenderer()
	{
		this(new MyEnvironment());
	}

	public ModelRenderer(MyEnvironment environment)
	{
		super();
		this.environment = environment;
	}

	public MyEnvironment getEnvironment()
	{
		return environment;
	}

	@Override
	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch, Viewport viewport)
	{
		if (isEmpty()) return;
		checkAgruments(spriteBatch, modelBatch, viewport.getCamera());
		viewport.apply();
		environment.render(getObjects(), viewport.getCamera());
		modelBatch.begin(viewport.getCamera());
		for (Entity entity: getObjects())
		{
			if (entity.isVisible(viewport.getCamera())) modelBatch.render(entity.getModelInstance(), environment);
		}
		modelBatch.end();
	}

	public void setEnvironment(MyEnvironment environment)
	{
		this.environment = environment;
	}
}
