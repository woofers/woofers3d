package com.jaxson.lib.gdx.graphics.g3d.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.jaxson.lib.gdx.graphics.g3d.entities.types.Entity;
import com.jaxson.lib.gdx.graphics.g3d.util.MyModelBuilder;

public class Box extends Entity
{
    private static final Color COLOR = Color.ORANGE;

    protected static Model getModel(Color color)
    {
        return new MyModelBuilder().createBox(color);
    }

    public Box()
    {
        this(COLOR);
    }

    public Box(Color color)
    {
        super(getModel(color));
    }
}
