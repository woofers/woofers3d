package com.jaxson.lib.gdx.backend.objects;

import com.jaxson.lib.gdx.graphics.g3d.entities.types.Entity;
import com.jaxson.lib.gdx.graphics.g3d.environment.MyEnvironment;
import com.jaxson.lib.gdx.graphics.views.View;

public class Models extends ObjectsBase<Entity>
{
	private MyEnvironment environment;

	public Models()
	{
		this(new MyEnvironment());
	}

	public Models(MyEnvironment environment)
	{
		super();
		this.environment = environment;
	}

	public MyEnvironment environment()
	{
		return environment;
	}

	@Override
	public void render(View view)
	{
		if (isEmpty()) return;
		view.modelView().apply();
		environment.render(getObjects(), view.modelView().getCamera());
		view.modelBatch().begin(view.modelView().getCamera());
		for (Entity entity: getObjects())
		{
			if (entity.isVisible(view.modelView().getCamera()))
			{
				view.modelBatch().render(
						entity.modelInstance(), environment);
			}
		}
		view.modelBatch().end();
	}

	public void setEnvironment(MyEnvironment environment)
	{
		this.environment = environment;
	}
}
