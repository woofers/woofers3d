package com.jaxson.lib.gdx.backend.renderer;

import com.jaxson.lib.gdx.graphics.g3d.entities.types.Entity;
import com.jaxson.lib.gdx.graphics.g3d.environment.MyEnvironment;
import com.jaxson.lib.gdx.graphics.views.View;

public class ModelRenderer extends BaseRenderer<Entity>
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
	public void render(View view)
	{
		if (isEmpty()) return;
		view.getModelView().apply();
		environment.render(getObjects(), view.getModelView().getCamera());
		view.getModelBatch().begin(view.getModelView().getCamera());
		for (Entity entity: getObjects())
		{
			if (entity.isVisible(view.getModelView().getCamera())) view.getModelBatch().render(entity.getModelInstance(), environment);
		}
		view.getModelBatch().end();
	}

	public void setEnvironment(MyEnvironment environment)
	{
		this.environment = environment;
	}
}
