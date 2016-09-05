package com.jaxson.lib.gdx.graphics.g3d.environment.lighting;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.environment.ShadowMap;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.graphics.g3d.entities.types.Entity;

@SuppressWarnings("deprecation")
public class MyDirectionalShadowLight extends BaseLight<DirectionalShadowLight>
{
	private static final int SHADOW_RESOLUTION = 4096;
	private static final float SHADOW_NEAR = 1f;
	private static final float SHADOW_FAR = 100f;
	private static final Vector3 WORLD_SIZE = new Vector3(100f, 100f, 100f);

	private ShadowBatch shadowBatch;

	public MyDirectionalShadowLight()
	{
		this(DIRECTION);
	}

	public MyDirectionalShadowLight(Color color, Vector3 direction)
	{
		this(color, direction, WORLD_SIZE);
	}

	public MyDirectionalShadowLight(Color color, Vector3 direction, float worldWidth, float worldDepth)
	{
		this(color, direction, SHADOW_RESOLUTION, SHADOW_RESOLUTION, worldWidth, worldDepth);
	}

	public MyDirectionalShadowLight(Color color, Vector3 direction, int shadowResolutionWidth, int shadowResolutionHeight,
			float worldWidth, float worldDepth)
	{
		this(color, direction, shadowResolutionHeight, shadowResolutionHeight, worldWidth, worldDepth, SHADOW_NEAR, SHADOW_FAR);
	}

	public MyDirectionalShadowLight(Color color, Vector3 direction, int shadowResolutionWidth, int shadowResolutionHeight,
			float worldWidth, float worldDepth, float near, float far)
	{
		this((DirectionalShadowLight) new DirectionalShadowLight(shadowResolutionWidth, shadowResolutionHeight, worldWidth, worldDepth, near, far).set(color, direction));
	}

	public MyDirectionalShadowLight(Color color, Vector3 direction, int shadowResolutionWidth, int shadowResolutionHeight,
			Vector3 worldSize, Camera camera)
	{
		this(color, direction, shadowResolutionHeight, shadowResolutionHeight, worldSize.x, worldSize.z, camera.near, camera.far);
	}

	public MyDirectionalShadowLight(Color color, Vector3 direction, Vector3 worldSize)
	{
		this(color, direction, worldSize.x, worldSize.z);
	}

	public MyDirectionalShadowLight(Color color, Vector3 direction, Vector3 worldSize, Camera camera)
	{
		this(color, direction, SHADOW_RESOLUTION, SHADOW_RESOLUTION, worldSize, camera);
	}

	public MyDirectionalShadowLight(DirectionalShadowLight light)
	{
		super(light);
		this.shadowBatch = new ShadowBatch();
	}

	public MyDirectionalShadowLight(Light light)
	{
		this(light.getColor(), light.getDirection());
	}

	public MyDirectionalShadowLight(Vector3 direction)
	{
		this(COLOR, direction);
	}

	public void begin(Camera camera)
	{
		getLight().begin(camera);
		being();
	}

	public void begin(Vector3 center, Vector3 forward)
	{
		getLight().begin(center, forward);
		being();
	}

	@Override
	public MyDirectionalShadowLight copy()
	{
		return new MyDirectionalShadowLight(this);
	}

	public void end()
	{
		getShadowBatch().end();
		getLight().end();
	}

	public Camera getCamera()
	{
		return getLight().getCamera();
	}

	public ShadowBatch getShadowBatch()
	{
		return shadowBatch;
	}

	public ShadowMap getShadowMap()
	{
		return getLight();
	}

	@Override
	public boolean hasShadows()
	{
		return true;
	}

	public void render(Entity entity)
	{
		render(entity.getModelInstance());
	}

	public void render(ModelInstance modelInstance)
	{
		getShadowBatch().render(modelInstance);
	}

	@Override
	public MyDirectionalLight toLight()
	{
		return new MyDirectionalLight(this);
	}

	@Override
	public MyDirectionalShadowLight toShadow()
	{
		return this;
	}

	private void being()
	{
		getShadowBatch().begin(this);
	}
}
