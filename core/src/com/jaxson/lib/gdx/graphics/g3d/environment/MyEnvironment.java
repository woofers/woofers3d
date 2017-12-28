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
        add(light.light());
    }

    public void begin(Camera camera)
    {
        if (!hasShadows()) return;
        shadowLight().begin(Vector3.Zero, camera.direction);
    }

    private void clearShadowMap()
    {
        setShadowMap((ShadowMap) null);
    }

    public ColorAttribute color()
    {
        return color;
    }

    public void end()
    {
        if (!hasShadows()) return;
        shadowLight().end();
    }

    public boolean hasLight()
    {
        return light() != null;
    }

    public boolean hasShadows()
    {
        return light().hasShadows();
    }

    public Light light()
    {
        return light;
    }

    public void remove(Light light)
    {
        remove(light.light());
    }

    public void render(MyArrayList<Entity> entities, Camera camera)
    {
        if (!hasShadows()) return;
        begin(camera);
        for (Entity entity: entities)
        {
            shadowLight().render(entity.modelInstance());
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
        setColor(light.color());
    }

    public void setLight(Light light)
    {
        if (light == light()) return;
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
            setLight(light().toShadow());
        }
        else
        {
            setLight(light().toLight());
        }
    }

    public void setWorldSize(Vector3 worldSize)
    {
        this.worldSize = worldSize;
    }

    public MyDirectionalShadowLight shadowLight()
    {
        return light.toShadow();
    }

    public Vector3 worldSize()
    {
        return worldSize;
    }
}
