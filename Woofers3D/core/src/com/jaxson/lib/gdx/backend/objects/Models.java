package com.jaxson.lib.gdx.backend.objects;

import com.jaxson.lib.gdx.graphics.g3d.entities.types.Entity;
import com.jaxson.lib.gdx.graphics.g3d.environment.MyEnvironment;
import com.jaxson.lib.gdx.graphics.views.View;
import com.jaxson.lib.util.MyArrayList;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.jaxson.lib.gdx.graphics.g3d.environment.shadows.system.ShadowSystem;
import com.jaxson.lib.gdx.graphics.g3d.environment.shadows.system.classical.ClassicalShadowSystem;
import com.jaxson.lib.gdx.graphics.g3d.environment.shadows.system.realistic.RealisticShadowSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.glutils.HdpiUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Models extends ObjectsBase<Entity>
{
	private MyEnvironment environment;
	private MyArrayList<ModelBatch> shadowsPasses;
	private ShadowSystem shadows;

	public Models()
	{
		this(new MyEnvironment());
	}

	public Models(MyEnvironment environment)
	{
		super();
		this.environment = environment;

		this.shadowsPasses = new MyArrayList<ModelBatch>();
		this.shadows = new ClassicalShadowSystem();
		this.shadows.init();
		for (int i = 0; i < shadows.getPassQuantity(); i ++)
		{
			shadowsPasses.add(new ModelBatch(shadows.getPassShaderProvider(i)));
		}
	}

	public MyEnvironment environment()
	{
		return environment;
	}

	@Override
	public void render(View view)
	{
		if (isEmpty()) return;

		if (view.modelBatch().getShaderProvider() != shadows.getShaderProvider())
		{
			view.setShaderProvider(shadows.getShaderProvider());
		}
		shadows.begin(view.modelView().getCamera(), instances());
		shadows.update();
		for (int i = 0; i < shadows.getPassQuantity(); i ++)
		{
			shadows.begin(i);
			Camera camera;
			while ((camera = shadows.next()) != null)
			{
				shadowsPasses.get(i).begin(camera);
				shadowsPasses.get(i).render(instances(), environment);
				shadowsPasses.get(i).end();
			}
			camera = null;
			shadows.end(i);
		}
		shadows.end();


HdpiUtils.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//Gdx.gl.glClearColor(0, 0, 0, 1);
//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

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

	private MyArrayList<ModelInstance> instances()
	{
		MyArrayList<ModelInstance> instances = new MyArrayList<ModelInstance>();
		for (Entity entity : getObjects())
		{
			instances.add(entity.modelInstance());
		}
		return instances;
	}
}
