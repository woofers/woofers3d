package com.jaxson.lib.gdx.bullet;

import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.backend.Game;
import com.jaxson.lib.gdx.bullet.simulation.PhysicsWorld;
import com.jaxson.lib.gdx.bullet.simulation.bodies.Floor;
import com.jaxson.lib.gdx.bullet.simulation.bodies.types.PlayerBody;
import com.jaxson.lib.gdx.bullet.simulation.bodies.types.RigidBody;
import com.jaxson.lib.gdx.bullet.simulation.bodies.types.SoftBody;
import com.jaxson.lib.gdx.graphics.views.TargetCamera;
import com.jaxson.lib.gdx.graphics.views.View;
import com.jaxson.lib.gdx.io.GdxFile;
import com.jaxson.lib.gdx.states.State;
import com.jaxson.lib.util.MyArrayList;

public abstract class BulletState extends State
{
	private static final boolean SHADOWS = true;

	private PhysicsWorld world;

	public BulletState(Game game)
	{
		super(game);
		this.world = new PhysicsWorld(environment());
		setShadows(SHADOWS);
	}

	public void applyPhysics(Floor entity)
	{
		world.add(entity);
	}

	public void applyPhysics(Floor entity, boolean hasMovement)
	{
		world.add(entity, hasMovement);
	}

	public void applyPhysics(PlayerBody entity)
	{
		world.add(entity);
	}

	public void applyPhysics(RigidBody entity)
	{
		world.add(entity);
	}

	public void applyPhysics(SoftBody entity)
	{
		world.add(entity);
	}

	public void applyPhysics(TargetCamera camera)
	{
		world.add(camera);
	}

	@Override
	public void dispose()
	{
		world.dispose();
		super.dispose();
	}

	public MyArrayList<RigidBody> load(GdxFile file)
	{
		MyArrayList<RigidBody> imported = physicsWorld().load(file);
		for (RigidBody entity: imported)
			add(entity);
		return imported;
	}

	public PhysicsWorld physicsWorld()
	{
		return world;
	}

	public void removePhysics(PlayerBody entity)
	{
		world.remove(entity);
	}

	public void removePhysics(RigidBody entity)
	{
		world.remove(entity);
	}

	public void removePhysics(SoftBody entity)
	{
		world.remove(entity);
	}

	@Override
	public void render(View view)
	{
		super.render(view);
		world.render(view);
	}

	public void setShadows(boolean shadows)
	{
		environment().setShawdows(shadows);
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
		world.update(dt);
	}

	public Vector3 worldSize()
	{
		return physicsWorld().worldSize();
	}
}
