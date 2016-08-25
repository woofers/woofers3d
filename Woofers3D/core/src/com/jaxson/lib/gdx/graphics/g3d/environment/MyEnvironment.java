package com.jaxson.lib.gdx.graphics.g3d.environment;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.ShadowMap;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.graphics.g3d.entities.types.Entity;
import com.jaxson.lib.gdx.graphics.g3d.environment.lighting.Light;
import com.jaxson.lib.gdx.graphics.g3d.environment.lighting.MyDirectionalLight;
import com.jaxson.lib.gdx.graphics.g3d.environment.lighting.MyDirectionalShadowLight;
import com.jaxson.lib.util.MyArrayList;

public class MyEnvironment extends Environment
{
	private ColorAttribute color;
	private Light light;
	private Vector3 worldSize;

	public MyEnvironment()
	{
		this(Light.COLOR, Light.DIRECTION);
	}

	public MyEnvironment(Color lightColor, Vector3 lightDirection)
	{
		this(new MyDirectionalLight(lightColor, lightDirection));
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
		if (!hasShadows()) return;
		getShadowLight().begin(Vector3.Zero, camera.direction);
	}

	private void clearShadowMap()
	{
		setShadowMap((ShadowMap) null);
	}

	public void end()
	{
		if (!hasShadows()) return;
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

	public MyDirectionalShadowLight getShadowLight()
	{
		return light.toShadow();
	}

	public Vector3 getWorldSize()
	{
		return worldSize;
	}

	public boolean hasLight()
	{
		return getLight() != null;
	}

	public boolean hasShadows()
	{
		return getLight().hasShadows();
	}

	public void remove(Light light)
	{
		remove(light.getLight());
	}

	public void render(MyArrayList<Entity> entities, Camera camera)
	{
		if (!hasShadows()) return;
		begin(camera);
		for (Entity entity: entities)
		{
			getShadowLight().render(entity.getModelInstance());
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
		clearShadowMap();
		this.light = light;
		add(light);
		setColor(light);
		if (hasShadows()) setShadowMap(light.toShadow());
	}

	private void setShadowMap(MyDirectionalShadowLight light)
	{
		setShadowMap(light.getShadowMap());
	}

	@SuppressWarnings("deprecation")
	private void setShadowMap(ShadowMap shadowMap)
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
