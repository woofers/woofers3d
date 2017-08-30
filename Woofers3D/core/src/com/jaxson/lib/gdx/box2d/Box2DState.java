package com.jaxson.lib.gdx.box2d;

import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.backend.Game;
import com.jaxson.lib.gdx.bullet.simulation.BulletWorld;
import com.jaxson.lib.gdx.bullet.simulation.bodies.Floor;
import com.jaxson.lib.gdx.bullet.simulation.bodies.types.PlayerBody;
import com.jaxson.lib.gdx.bullet.simulation.bodies.types.RigidBody;
import com.jaxson.lib.gdx.bullet.simulation.bodies.types.SoftBody;
import com.jaxson.lib.gdx.graphics.views.TargetCamera;
import com.jaxson.lib.gdx.graphics.views.View;
import com.jaxson.lib.gdx.io.GdxFile;
import com.jaxson.lib.gdx.states.State;
import com.jaxson.lib.util.MyArrayList;
import com.badlogic.gdx.physics.box2d.World;
import com.jaxson.lib.gdx.graphics.g2d.SpriteActor;
import com.jaxson.lib.gdx.box2d.bodies.types.SpriteBody;
import com.jaxson.lib.gdx.box2d.Box2DWorld;

public abstract class Box2DState extends State
{
	private Box2DWorld world;

	public Box2DState(Game game)
	{
		super(game);
		this.world = new Box2DWorld();
	}

	public void applyPhysics(SpriteBody sprite)
	{
		world.add(sprite);
	}

	@Override
	public void dispose()
	{
		world.dispose();
		super.dispose();
	}

	public Box2DWorld physicsWorld()
	{
		return world;
	}

	public void removePhysics(SpriteBody sprite)
	{
		world.remove(sprite);
	}

	@Override
	public void render(View view)
	{
		super.render(view);
		world.render(view);
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
		world.update(dt);
	}
}
