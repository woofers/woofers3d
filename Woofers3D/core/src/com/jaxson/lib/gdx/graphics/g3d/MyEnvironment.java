package com.jaxson.lib.gdx.graphics.g3d;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.ShadowMap;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.graphics.g3d.environment.Light;
import com.jaxson.lib.gdx.graphics.g3d.environment.MyDirectionalLight;
import com.jaxson.lib.gdx.graphics.g3d.environment.MyDirectionalShadowLight;
import com.jaxson.lib.util.MyArrayList;

public class MyEnvironment extends Environment
{
	private ColorAttribute color;
	private Light light;
	private ModelBatch shadowBatch;
	private Vector3 worldSize;

	public MyEnvironment()
	{
		this(Light.COLOR, Light.DIRECTION);
	}

	public MyEnvironment(Color lightColor, Vector3 lightDirection)
	{
		super();
		setLight(new MyDirectionalLight(lightColor, lightDirection));
	}

	public MyEnvironment(MyDirectionalLight light)
	{
		super();
		setLight(light);
	}

	public void add(Light light)
	{
		add(light.getLight());
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

	public Light getLight()
	{
		return light;
	}

	public ModelBatch getShadowBatch()
	{
		return shadowBatch;
	}

	public MyDirectionalShadowLight getShadowLight()
	{
		if (hasShawdowLight()) return (MyDirectionalShadowLight) getLight();
		return null;
	}

	public Vector3 getWorldSize()
	{
		return worldSize;
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
		return getLight() instanceof MyDirectionalShadowLight;
	}

	public boolean hasShawdows()
	{
		return hasShawdowLight();
	}

	public void remove(Light light)
	{
		remove(light.getLight());
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
		setColor(color, Light.TYPE);
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

	public void setColor(Light light)
	{
		setColor(light.getColor());
	}

	public void setLight(Light light)
	{
		if (light == getLight()) return;
		if (hasLight()) remove(light);
		setShawdowMap(null);
		this.light = light;
		add(light);
		setColor(light);
		if (hasShawdowLight()) setShadowLight(getShadowLight());
	}

	private void setShadowLight(MyDirectionalShadowLight light)
	{
		setShawdowMap(light);
		createShadowBatch();
	}

	public void setShawdowMap(ShadowMap shadowMap)
	{
		this.shadowMap = shadowMap;
	}

	public void setShawdows(boolean shawdows)
	{
		if (shawdows)
		{
			setLight(getLight().toShadow());
		}
		else
		{
			setLight(getLight().toLight());
		}
	}

	public void setWorldSize(Vector3 worldSize)
	{
		this.worldSize = worldSize;
	}
}
