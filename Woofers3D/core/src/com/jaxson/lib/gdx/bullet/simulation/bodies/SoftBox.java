package com.jaxson.lib.gdx.bullet.simulation.bodies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.jaxson.lib.gdx.bullet.simulation.PhysicsWorld;
import com.jaxson.lib.gdx.bullet.simulation.bodies.types.SoftBody;
import com.jaxson.lib.gdx.graphics.g3d.util.MyModelBuilder;
import com.jaxson.lib.gdx.graphics.g3d.entities.Box;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

public class SoftBox extends SoftBody
{
	public SoftBox(Color color, PhysicsWorld world)
	{
		super(new Box(color).getModelInstance(), MASS, world);
	}

	public SoftBox(PhysicsWorld world)
	{
		this(COLOR, world);
	}
}
