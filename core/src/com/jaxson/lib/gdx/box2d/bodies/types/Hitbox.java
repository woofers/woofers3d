package com.jaxson.lib.gdx.box2d.bodies.types;

import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.math.Vector2;
import com.jaxson.lib.math.MyMath;
import com.badlogic.gdx.utils.Disposable;

public class Hitbox implements Disposable
{
    private float width;
    private float height;
    private float offsetX;
    private float offsetY;
    private PolygonShape shape;

    public Hitbox(float width, float height)
    {
        this(width, height, 0f, 0f);
    }

    public Hitbox(float width, float height, float offsetX, float offsetY)
    {
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;

        this.shape = new PolygonShape();
    }

    public float width()
    {
        return width;
    }

    public float height()
    {
        return height;
    }

    public float offsetX()
    {
        return offsetX;
    }

    public float offsetY()
    {
        return offsetY;
    }

    public BodyDef apply(BodyDef bodyDef, float x, float y)
    {
        bodyDef.position.set(x + width() / 2 + offsetY(), y + height() / 2 + offsetY());
        return bodyDef;
    }

    public FixtureDef apply(FixtureDef fixtureDef, float angle)
    {
        shape.setAsBox(
                width() / 2, height() / 2, Vector2.Zero,
                angle * MyMath.DEGREES_TO_RADIANS);

        fixtureDef.shape = shape;

        return fixtureDef;
    }

    public void dispose()
    {
        shape.dispose();
        shape = null;
    }
}
