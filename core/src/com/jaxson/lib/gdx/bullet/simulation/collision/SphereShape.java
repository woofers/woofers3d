package com.jaxson.lib.gdx.bullet.simulation.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btSphereShape;
import com.jaxson.lib.gdx.bullet.simulation.collision.types.ConvexShape;

public class SphereShape extends ConvexShape
{
    private static final Vector3 DEFAULT_SIZE = new Vector3(1f, 1f, 1f);
    private static final float RADIUS = 0.5f;

    public SphereShape()
    {
        this(RADIUS);
    }

    public SphereShape(float radius)
    {
        super(new btSphereShape(radius));
    }

    public SphereShape(Vector3 size)
    {
        super(new btSphereShape(RADIUS));
        setScale(size);
    }
}
