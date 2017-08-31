package com.jaxson.lib.gdx.box2d.simulation;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.DestructionListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.jaxson.lib.gdx.box2d.bodies.types.SpriteBody;
import com.jaxson.lib.gdx.graphics.views.View;
import com.jaxson.lib.gdx.util.GameObject;
import com.jaxson.lib.util.MyArrayList;

public class Box2DWorld extends GameObject
{
	private static final boolean SLEEP = true;
	public static float METERS_TO_PIXELS = 64f;
	public static float PIXELS_TO_METERS = 1f / METERS_TO_PIXELS;

	private World world;
	private MyArrayList<SpriteBody> sprites = new MyArrayList<>();
	private int velocityIterations = 6;
	private int positionIterations = 2;
	private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

	public Box2DWorld()
	{
		this(new Vector2(0f, -9.8f));
	}

	public Box2DWorld(Vector2 gravity)
	{
		this.world = new World(gravity, SLEEP);
	}

	public void add(SpriteBody sprite)
	{
		sprite.createBody(this);
		sprites.add(sprite);
	}

	public boolean autoClearsForces()
	{
		return world.getAutoClearForces();
	}

	public void clearForces()
	{
		world.clearForces();
	}

	public Array<Contact> contactList()
	{
		return world.getContactList();
	}

	public Body createBody(BodyDef def)
	{
		return world.createBody(def);
	}

	public Joint createJoint(JointDef def)
	{
		return world.createJoint(def);
	}

	public void destroyBody(Body body)
	{
		world.destroyBody(body);
	}

	public void destroyJoint(Joint joint)
	{
		world.destroyJoint(joint);
	}

	@Override
	public void dispose()
	{
		world.dispose();
	}

	@Override
	public boolean equals(Object arg0)
	{
		return world.equals(arg0);
	}

	public void getBodies(Array<Body> bodies)
	{
		world.getBodies(bodies);
	}

	public int getBodyCount()
	{
		return world.getBodyCount();
	}

	public int getContactCount()
	{
		return world.getContactCount();
	}

	public int getFixtureCount()
	{
		return world.getFixtureCount();
	}

	public void getFixtures(Array<Fixture> fixtures)
	{
		world.getFixtures(fixtures);
	}

	public Vector2 getGravity()
	{
		return world.getGravity();
	}

	public int getJointCount()
	{
		return world.getJointCount();
	}

	public void getJoints(Array<Joint> joints)
	{
		world.getJoints(joints);
	}

	public int getProxyCount()
	{
		return world.getProxyCount();
	}

	@Override
	public int hashCode()
	{
		return world.hashCode();
	}

	public boolean isLocked()
	{
		return world.isLocked();
	}

	public void queryAABB(QueryCallback callback, float lowerX, float lowerY,
			float upperX, float upperY)
	{
		world.QueryAABB(callback, lowerX, lowerY, upperX, upperY);
	}

	public void rayCast(RayCastCallback callback, float point1x, float point1y,
			float point2x, float point2y)
	{
		world.rayCast(callback, point1x, point1y, point2x, point2y);
	}

	public void rayCast(RayCastCallback callback, Vector2 point1,
			Vector2 point2)
	{
		world.rayCast(callback, point1, point2);
	}

	public void remove(SpriteBody sprite)
	{
		sprites.remove(sprite);
	}

	@Override
	public void render(View view)
	{
		debugRenderer.render(
				world, view.spriteBatch().getProjectionMatrix().cpy().scale(
						METERS_TO_PIXELS, METERS_TO_PIXELS, 0));
	}

	public void setAutoClearForces(boolean flag)
	{
		world.setAutoClearForces(flag);
	}

	public void setContactFilter(ContactFilter filter)
	{
		world.setContactFilter(filter);
	}

	public void setContactListener(ContactListener listener)
	{
		world.setContactListener(listener);
	}

	public void setContinuousPhysics(boolean flag)
	{
		world.setContinuousPhysics(flag);
	}

	public void setDestructionListener(DestructionListener listener)
	{
		world.setDestructionListener(listener);
	}

	public void setGravity(Vector2 gravity)
	{
		world.setGravity(gravity);
	}

	public void setWarmStarting(boolean flag)
	{
		world.setWarmStarting(flag);
	}

	@Override
	public String toString()
	{
		return world.toString();
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
		world.step(dt, velocityIterations, positionIterations);
	}
}
