package com.jaxson.woofers3d.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.util.MyArrayList;
import com.jaxson.woofers3d.entities.Entity;
import com.jaxson.woofers3d.states.GameStateManager;
import com.jaxson.woofers3d.states.State;

public abstract class State3D extends State
{
	private static final int FOV                 = 75;
	private static final float CAMERA_NEAR       = 0.1f;
	private static final float CAMERA_FAR        = 300f;
	private static final Vector3 CAMERA_LOCATION = new Vector3(1f, 2f, 3f);
	private static final Vector3 STAGE_LOCATION  = Vector3.Zero;

	protected PerspectiveCamera camera;
	protected Environment environment;
	private MyArrayList<Entity> entities;

	public State3D(GameStateManager gameStateManager)
	{
		super(gameStateManager);

		entities = new MyArrayList<Entity>();

		camera = new PerspectiveCamera(FOV, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(CAMERA_LOCATION.x, CAMERA_LOCATION.y, CAMERA_LOCATION.z);
		camera.lookAt(STAGE_LOCATION.x, STAGE_LOCATION.y, STAGE_LOCATION.z);
		camera.near = CAMERA_NEAR;
		camera.far = CAMERA_FAR;

		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
	}

	public void add(Entity model)
	{
		entities.add(model);
	}

	public void dispose()
	{
		for (Entity model: entities)
		{
			model.dispose();
		}
	}

	public void render(ModelBatch modelBatch)
	{
		modelBatch.begin(camera);
		for (Entity model: entities)
		{
			modelBatch.render(model, environment);
		}
		modelBatch.end();
	}

	public void update(float dt)
	{
		super.update(dt);
		camera.update();
		for (Entity model: entities)
		{
			model.update(dt);
		}
	}
}
