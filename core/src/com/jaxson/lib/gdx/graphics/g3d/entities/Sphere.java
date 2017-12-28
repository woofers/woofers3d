package com.jaxson.lib.gdx.graphics.g3d.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.jaxson.lib.gdx.graphics.g3d.entities.types.Entity;
import com.jaxson.lib.gdx.graphics.g3d.util.MyModelBuilder;

public class Sphere extends Entity
{
    private static final Color COLOR = Color.BLUE;

    protected static Model getModel(Color color)
    {
        return new MyModelBuilder().createSphere(color);
    }

    public Sphere()
    {
        this(COLOR);
    }

    public Sphere(Color color)
    {
        super(getModel(color));
    }
}
