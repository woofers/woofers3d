package com.jaxson.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.PerspectiveCamera;

public abstract class State3D extends State
{
	protected PerspectiveCamera camera;
	protected Environment environment;

	public State3D(GameStateManager gameStateManager)
	{
		super(gameStateManager);

		camera = new PerspectiveCamera(75, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(1f, 2f, 3f);
		camera.lookAt(0f,0f,0f);
		camera.near = 0.1f;
		camera.far = 300f;

		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
	}

	public abstract void render(ModelBatch modelBatch);
}
