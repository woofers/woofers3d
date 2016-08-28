package com.jaxson.lib.gdx.backend.renderer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.jaxson.lib.gdx.graphics.g3d.entities.types.Entity;
import com.jaxson.lib.gdx.graphics.g3d.environment.MyEnvironment;
import com.jaxson.lib.gdx.graphics.views.View;

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
	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch, View view)
	{
		if (isEmpty()) return;
		checkAgruments(spriteBatch, modelBatch, view.getModelView().getCamera());
		view.getModelView().apply();
		environment.render(getObjects(), view.getModelView().getCamera());
		modelBatch.begin(view.getModelView().getCamera());
		for (Entity entity: getObjects())
		{
			if (entity.isVisible(view.getModelView().getCamera())) modelBatch.render(entity.getModelInstance(), environment);
		}
		modelBatch.end();
	}

	public void setEnvironment(MyEnvironment environment)
	{
		this.environment = environment;
	}
}
