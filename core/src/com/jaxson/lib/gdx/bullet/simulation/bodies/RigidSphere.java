package com.jaxson.lib.gdx.bullet.simulation.bodies;

import com.badlogic.gdx.graphics.Color;
import com.jaxson.lib.gdx.bullet.simulation.bodies.types.RigidBody;
import com.jaxson.lib.gdx.bullet.simulation.collision.SphereShape;
import com.jaxson.lib.gdx.graphics.color.MyColor;
import com.jaxson.lib.gdx.graphics.g3d.entities.Sphere;

public class RigidSphere extends RigidBody
{
    protected static final Color COLOR = new MyColor(81, 101, 107);

    protected static SphereShape getShape()
    {
        return new SphereShape();
    }

    public RigidSphere()
    {
        this(COLOR);
    }

    public RigidSphere(Color color)
    {
        super(new Sphere(color).modelInstance(), getShape());
    }
}
