package com.jaxson.lib.gdx.graphics.g3d.environment;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.math.Vector3;

public class MyDirectionalShadowLight extends DirectionalShadowLight implements Light
{
	private static final int SHADOW_RESOLUTION = 4096;
	private static final float SHADOW_NEAR = 1f;
	private static final float SHADOW_FAR = 100f;
	private static final Vector3 WORLD_SIZE = new Vector3(100f, 100f, 100f);

	public MyDirectionalShadowLight()
	{
		this(Light.DIRECTION);
	}

	public MyDirectionalShadowLight(Color color, Vector3 direction)
	{
		this(color, direction, WORLD_SIZE);
	}

	public MyDirectionalShadowLight(Color color, Vector3 direction, float worldWidth, float worldDepth)
	{
		this(color, direction, SHADOW_RESOLUTION, SHADOW_RESOLUTION, worldWidth, worldDepth);
	}

	public MyDirectionalShadowLight(Color color, Vector3 direction, int shadowResolutionWidth, int shadowResolutionHeight, float worldWidth, float worldDepth)
	{
		this(color, direction, shadowResolutionHeight, shadowResolutionHeight, worldWidth, worldDepth, SHADOW_NEAR, SHADOW_FAR);
	}

	public MyDirectionalShadowLight(Color color, Vector3 direction, int shadowResolutionWidth, int shadowResolutionHeight, float worldWidth, float worldDepth, float near, float far)
	{
		super(shadowResolutionWidth, shadowResolutionHeight, worldWidth, worldDepth, near, far);
		set(color, direction);
	}

	public MyDirectionalShadowLight(Color color, Vector3 direction, int shadowResolutionWidth, int shadowResolutionHeight, Vector3 worldSize, Camera camera)
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

	public MyDirectionalShadowLight(Vector3 direction)
	{
		this(Light.COLOR, direction);
	}

	@Override
	public Color getColor()
	{
		return color;
	}

	@Override
	public Vector3 getDirection()
	{
		return direction;
	}

	@Override
	public DirectionalLight getLight()
	{
		return this;
	}

	@Override
	public boolean hasShadows()
	{
		return true;
	}

	@Override
	public MyDirectionalLight toLight()
	{
		return new MyDirectionalLight(getColor(), getDirection());
	}

	@Override
	public MyDirectionalShadowLight toShadow()
	{
		return this;
	}
}
