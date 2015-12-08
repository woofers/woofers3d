package com.jaxson.lib.gdx.states.stage;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.collision.Ray;
import com.jaxson.lib.gdx.entities.Entity;
import com.jaxson.lib.gdx.graphics.MyEnvironment;

public class Stage3D extends Stage<Entity>
{
	private Environment environment;

	public Stage3D()
	{
		super();
		this.environment = new MyEnvironment();
	}

	@Override
	public void dispose()
	{
		for (Entity entity: objects)
		{
			entity.dispose();
		}
	}

	public Entity getEntity(int x, int y, Camera camera)
	{
		Ray ray = camera.getPickRay(x, y);

		for (Entity entity: objects)
		{

		}

		return null;
	}

	/*
	private boolean isVisible(Entity entity)
	{
		entity.transform.getTranslation(position);
		position.add(entity.center);
		return cam.frustum.sphereInFrustum(position, entity.radius);
	}
	*/

	public void render(ModelBatch modelBatch, Camera camera)
	{
		if (modelBatch == null) return;
		if (camera == null) return;
		if (isEmpty()) return;
		modelBatch.begin(camera);
		for (Entity entity: objects)
		{
			modelBatch.render(entity, environment);
		}
		modelBatch.end();
	}

	@Override
	public void update(float dt)
	{
		for (Entity entity: objects)
		{
			entity.update(dt);
		}
	}
}
