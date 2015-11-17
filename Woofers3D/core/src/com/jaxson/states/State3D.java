package com.jaxson.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

public abstract class State3D extends State
{
	private static final int FOV                 = 75;
	private static final float CAMERA_NEAR       = 0.1f;
	private static final float CAMERA_FAR        = 300f;
	private static final Vector3 CAMERA_LOCATION = new Vector3(1f, 2f, 3f);
	private static final Vector3 STAGE_LOCATION  = new Vector3();

	protected PerspectiveCamera camera;
	protected Environment environment;

	public State3D(GameStateManager gameStateManager)
	{
		super(gameStateManager);

		camera = new PerspectiveCamera(FOV, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(CAMERA_LOCATION.x, CAMERA_LOCATION.y, CAMERA_LOCATION.z);
		camera.lookAt(STAGE_LOCATION.x, STAGE_LOCATION.y, STAGE_LOCATION.z);
		camera.near = CAMERA_NEAR;
		camera.far = CAMERA_FAR;

		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
	}

	public abstract void render(ModelBatch modelBatch);
}
