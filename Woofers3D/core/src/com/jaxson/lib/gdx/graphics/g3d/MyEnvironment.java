package com.jaxson.lib.gdx.graphics.g3d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.jaxson.lib.gdx.graphics.MyColor;
import com.badlogic.gdx.graphics.Camera;
import com.jaxson.lib.util.MyArrayList;

public class MyEnvironment extends Environment
{
	private static final long LIGHT_TYPE = ColorAttribute.AmbientLight;
	private static final Color LIGHT_COLOR = new MyColor(204, 204, 204);
	private static final Vector3 LIGHT_DIRECTION = new Vector3(-1f, -0.8f, -0.2f);
	private static final int SHADOW_RESOLUTION = 4096;
	private static final float SHADOW_NEAR = 1f;
	private static final float SHADOW_FAR = 300f;

	private ColorAttribute color;
	private DirectionalLight light;
	private ModelBatch shadowBatch;

	public MyEnvironment()
	{
		this(LIGHT_COLOR, LIGHT_DIRECTION);
	}

	public MyEnvironment(Color lightColor, Vector3 lightDirection)
	{
		super();
		setLight(createShadowLight(lightColor, lightDirection, 40f, 40f));
		//setLight(createLight(lightColor, lightDirection));
	}

	private void createShadowBatch()
	{
		if (hasShawdowBatch()) return;
		this.shadowBatch = new ModelBatch(new DepthShaderProvider());
	}

	public static DirectionalLight createLight(Color color, Vector3 direction)
	{
		DirectionalLight light = new DirectionalLight();
		light.set(color, direction);
		return light;
	}

	public static DirectionalShadowLight createShadowLight(Color color, Vector3 direction, Vector3 worldSize, Camera camera)
	{
		return createShadowLight(color, direction, SHADOW_RESOLUTION, SHADOW_RESOLUTION, worldSize, camera);
	}

	public static DirectionalShadowLight createShadowLight(Color color, Vector3 direction, float worldWidth, float worldDepth)
	{
		return createShadowLight(color, direction, SHADOW_RESOLUTION, SHADOW_RESOLUTION, worldWidth, worldDepth);
	}

	public static DirectionalShadowLight createShadowLight(Color color, Vector3 direction, int shadowResolutionWidth, int shadowResolutionHeight, Vector3 worldSize, Camera camera)
	{
		return createShadowLight(color, direction, shadowResolutionHeight, shadowResolutionHeight, worldSize.x, worldSize.z, camera.near, camera.far);
	}

	public static DirectionalShadowLight createShadowLight(Color color, Vector3 direction, int shadowResolutionWidth, int shadowResolutionHeight, float worldWidth, float worldDepth)
	{
		return createShadowLight(color, direction, shadowResolutionHeight, shadowResolutionHeight, worldWidth, worldDepth, SHADOW_NEAR, SHADOW_FAR);
	}

	public static DirectionalShadowLight createShadowLight(Color color, Vector3 direction, int shadowResolutionWidth, int shadowResolutionHeight, float worldWidth, float worldDepth, float near, float far)
	{
		DirectionalShadowLight light = new DirectionalShadowLight(shadowResolutionWidth, shadowResolutionHeight, worldWidth, worldDepth, near, far);
		light.set(color, direction);
		return light;
	}

	public void begin(Camera camera)
	{
		getShadowLight().begin(Vector3.Zero, camera.direction);
		getShadowBatch().begin(getShadowLight().getCamera());
	}

	public void end()
	{
		getShadowBatch().end();
		getShadowLight().end();
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

	public boolean hasLight()
	{
		return getLight() != null;
	}

	public boolean hasColor()
	{
		return getColor() != null;
	}

	public DirectionalShadowLight getShadowLight()
	{
		if (hasShawdowLight()) return (DirectionalShadowLight)(getLight());
		return null;
	}

	public DirectionalLight getLight()
	{
		return light;
	}

	public ColorAttribute getColor()
	{
		return color;
	}

	public ModelBatch getShadowBatch()
	{
		return shadowBatch;
	}

	public void setLight(DirectionalLight light)
	{
		if (light == getLight()) return;
		if (hasLight()) remove(light);
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
}
