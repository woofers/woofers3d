package com.jaxson.lib.gdx.bullet.simulation.bodies;

import com.badlogic.gdx.graphics.Color;
import com.jaxson.lib.gdx.bullet.simulation.BulletWorld;
import com.jaxson.lib.gdx.bullet.simulation.bodies.types.SoftBody;
import com.jaxson.lib.gdx.graphics.g3d.entities.Box;

public class SoftBox extends SoftBody
{
	public SoftBox(Color color, BulletWorld world)
	{
		super(new Box(color).modelInstance(), MASS, world);
	}

	public SoftBox(BulletWorld world)
	{
		this(COLOR, world);
	}
}
