package com.jaxson.lib.gdx.graphics.g3d;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.graphics.MyColor;
import com.jaxson.lib.util.MyArrayList;

public class MyEnvironment extends Environment
{
	private static final long LIGHT_TYPE = ColorAttribute.AmbientLight;
	private static final Color LIGHT_COLOR = new MyColor(200, 200, 200);
	private static final Vector3 LIGHT_DIRECTION = new Vector3(-1f, -0.8f, -0.2f);
	private static final int SHADOW_RESOLUTION = 4096;
	private static final float SHADOW_NEAR = 1f;
	private static final float SHADOW_FAR = 300f;

	private ColorAttribute color;
	private DirectionalLight light;
	private ModelBatch shadowBatch;
	private Vector3 worldSize;

	public MyEnvironment()
	{
		this(LIGHT_COLOR, LIGHT_DIRECTION);
	}

	public MyEnvironment(Color lightColor, Vector3 lightDirection)
	{
		super();
		setLight(createLight(lightColor, lightDirection));
	}

	public void begin(Camera camera)
	{
		getShadowLight().begin(Vector3.Zero, camera.direction);
		getShadowBatch().begin(getShadowLight().getCamera());
	}

	private void createShadowBatch()
	{
		if (hasShawdowBatch()) return;
		this.shadowBatch = new ModelBatch(new DepthShaderProvider());
	}

	public void end()
	{
		getShadowBatch().end();
		getShadowLight().end();
	}

	public ColorAttribute getColor()
	{
		return color;
	}

	public DirectionalLight getLight()
	{
		return light;
	}

	public Color getLightColor()
	{
		return getLight().color;
	}

	public Vector3 getLightDirection()
	{
		return getLight().direction;
	}

	public ModelBatch getShadowBatch()
	{
		return shadowBatch;
	}

	public DirectionalShadowLight getShadowLight()
	{
		if (hasShawdowLight()) return (DirectionalShadowLight) getLight();
		return null;
	}

	public Vector3 getWorldSize()
	{
		return worldSize;
	}

	public boolean hasColor()
	{
		return getColor() != null;
	}

	public boolean hasLight()
	{
		return getLight() != null;
	}

	private boolean hasShawdowBatch()
	{
		return shadowBatch != null;
	}

	private boolean hasShawdowLight()
	{
		return getLight() instanceof DirectionalShadowLight;
	}

	public boolean hasShawdows()
	{
		return hasShawdowLight();
	}

	public void render(MyArrayList<Entity> entities, Camera camera)
	{
		if (!hasShawdows()) return;
		begin(camera);
		for (Entity entity: entities)
		{
			getShadowBatch().render(entity.getModelInstance());
		}
		end();
	}

	public void setColor(Color color)
	{
		setColor(color, LIGHT_TYPE);
	}

	public void setColor(Color color, long attribute)
	{
		setColor(new ColorAttribute(attribute, color));
	}

	public void setColor(ColorAttribute color)
	{
		this.color = color;
		set(color);
	}

	public void setLight(DirectionalLight light)
	{
		if (light == getLight()) return;
		if (hasLight()) remove(light);
		this.shadowMap = null;
		this.light = light;
		add(light);
		setColor(light.color);
		if (hasShawdowLight()) setShadowLight(getShadowLight());
	}

	private void setShadowLight(DirectionalShadowLight light)
	{
		this.shadowMap = light;
		createShadowBatch();
	}

	public void setShawdows(boolean shawdows)
	{
		if (shawdows == hasShawdows()) return;
		if (shawdows)
		{
			if (getWorldSize() == null)
			{
				setLight(createShadowLight(getLightColor(), getLightDirection()));
			}
			else
			{
				setLight(createShadowLight(getLightColor(), getLightDirection(), getWorldSize()));
			}
		}
		else
		{
			setLight(createLight(getLightColor(), getLightDirection()));
		}
	}

	public void setWorldSize(Vector3 worldSize)
	{
		this.worldSize = worldSize;
	}

	public static DirectionalLight createLight(Color color, Vector3 direction)
	{
		return new DirectionalLight().set(color, direction);
	}

	public static DirectionalShadowLight createShadowLight(Color color, Vector3 direction)
	{
		return createShadowLight(color, direction, SHADOW_RESOLUTION, SHADOW_RESOLUTION);
	}

	public static DirectionalShadowLight createShadowLight(Color color, Vector3 direction, float worldWidth,
			float worldDepth)
	{
		return createShadowLight(color, direction, SHADOW_RESOLUTION, SHADOW_RESOLUTION, worldWidth, worldDepth);
	}

	public static DirectionalShadowLight createShadowLight(Color color, Vector3 direction, int shadowResolutionWidth,
			int shadowResolutionHeight, float worldWidth, float worldDepth)
	{
		return createShadowLight(color, direction, shadowResolutionHeight, shadowResolutionHeight, worldWidth,
				worldDepth, SHADOW_NEAR, SHADOW_FAR);
	}

	public static DirectionalShadowLight createShadowLight(Color color, Vector3 direction, int shadowResolutionWidth,
			int shadowResolutionHeight, float worldWidth, float worldDepth, float near, float far)
	{
		DirectionalShadowLight light = new DirectionalShadowLight(shadowResolutionWidth, shadowResolutionHeight,
				worldWidth, worldDepth, near, far);
		light.set(color, direction);
		return light;
	}

	public static DirectionalShadowLight createShadowLight(Color color, Vector3 direction, int shadowResolutionWidth,
			int shadowResolutionHeight, Vector3 worldSize, Camera camera)
	{
		return createShadowLight(color, direction, shadowResolutionHeight, shadowResolutionHeight, worldSize.x,
				worldSize.z, camera.near, camera.far);
	}

	public static DirectionalShadowLight createShadowLight(Color color, Vector3 direction, Vector3 worldSize)
	{
		return createShadowLight(color, direction, worldSize.x, worldSize.z);
	}

	public static DirectionalShadowLight createShadowLight(Color color, Vector3 direction, Vector3 worldSize,
			Camera camera)
	{
		return createShadowLight(color, direction, SHADOW_RESOLUTION, SHADOW_RESOLUTION, worldSize, camera);
	}
}
