package com.jaxson.lib.gdx.bullet.simulation.bodies;

import com.badlogic.gdx.graphics.Color;
import com.jaxson.lib.gdx.bullet.simulation.PhysicsWorld;
import com.jaxson.lib.gdx.bullet.simulation.bodies.types.SoftBody;
import com.jaxson.lib.gdx.graphics.g3d.entities.Box;

public class SoftBox extends SoftBody
{
	public SoftBox(Color color, PhysicsWorld world)
	{
		super(new Box(color).modelInstance(), MASS, world);
	}

	public SoftBox(PhysicsWorld world)
	{
		this(COLOR, world);
	}
}
