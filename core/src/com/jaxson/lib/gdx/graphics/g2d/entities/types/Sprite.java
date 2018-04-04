package com.jaxson.lib.gdx.graphics.g2d.entities.types;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.util.GameObject;

public abstract class Sprite extends GameObject
{
    private float alpha = 1f;
    private float depth = 1f;

    public float alpha()
    {
        return alpha;
    }

    public float depth()
    {
        return depth;
    }

    public Vector2 direction()
    {
        Vector2 scale = scale().cpy();
        return scale.set(Math.signum(scale.x), Math.signum(scale.y));
    }

    public abstract float height();

    public Vector2 location()
    {
        return new Vector2(x(), y());
    }

    public Vector2 locationFromCenter()
    {
        return location().add(location().scl(0.5f));
    }

    public abstract void moveCenterTo(Vector2 center);

    public abstract void moveTo(Vector2 location);

    public void moveTo(Vector3 location)
    {
        moveTo(new Vector2(location.x, location.y));
        depth = location.z;
    }

    public abstract Vector2 origin();

    public void rotate(float degrees)
    {
        setRotation(rotation() + degrees);
    }

    public abstract float rotation();

    public abstract Vector2 scale();

    public void scale(float scale)
    {
        scale(new Vector2(scale, scale));
    }

    public abstract void scale(Vector2 scale);

    public void setAlpha(float alpha)
    {
        this.alpha = alpha;
    }

    public void setDepth(float depth)
    {
        this.depth = depth;
    }

    public abstract void setFlip(boolean flipX, boolean flipY);

    public abstract void setOrigin();

    public abstract void setOrigin(Vector2 origin);

    public abstract void setRotation(float degrees);

    public abstract void setSize(float width, float height);

    public void setSize(Vector2 size)
    {
        setSize(size.x, size.y);
    }

    public Vector2 size()
    {
        return new Vector2(width(), height());
    }

    public void translate(float translation)
    {
        translate(new Vector2(translation, translation));
    }

    public abstract void translate(Vector2 translation);

    public abstract float width();

    public abstract float x();

    public abstract float y();
}
