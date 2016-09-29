package com.jaxson.lib.gdx.bullet.simulation.bodies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.jaxson.lib.gdx.bullet.simulation.bodies.types.RigidBody;
import com.jaxson.lib.gdx.bullet.simulation.collision.BoxShape;
import com.jaxson.lib.gdx.bullet.simulation.collision.types.Shape;
import com.jaxson.lib.gdx.graphics.g3d.util.MyModelBuilder;
import com.jaxson.lib.gdx.graphics.g3d.entities.Box;

public class RigidBox extends RigidBody
{
	public RigidBox()
	{
		this(COLOR);
	}

	public RigidBox(Color color)
	{
		super(new Box(color).modelInstance(), getShape());
	}

	protected static Shape getShape()
	{
		return new BoxShape();
	}
}
