package com.jaxson.lib.gdx.box2d;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
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
import com.jaxson.lib.gdx.util.GameObject;
import com.badlogic.gdx.math.Vector2;
import com.jaxson.lib.util.MyArrayList;
import com.jaxson.lib.gdx.box2d.bodies.types.SpriteBody;
import com.jaxson.lib.gdx.graphics.views.View;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

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
		this(new Vector2(0f, -98f));
	}

	public void add(SpriteBody sprite)
	{
		sprite.createBody(this);
		sprites.add(sprite);
	}

	public void remove(SpriteBody sprite)
	{
		sprites.remove(sprite);
	}

	public Box2DWorld(Vector2 gravity)
	{
		this.world = new World(gravity, SLEEP);
	}

	public void setDestructionListener(DestructionListener listener)
	{
		world.setDestructionListener(listener);
	}

	public void render(View view)
	{
		debugRenderer.render(world, view.spriteBatch().getProjectionMatrix());
		//.cpy().scale(PIXELS_TO_METERS, PIXELS_TO_METERS, 0));
	}

	public void setContactFilter(ContactFilter filter)
	{
		world.setContactFilter(filter);
	}

	public void setContactListener(ContactListener listener)
	{
		world.setContactListener(listener);
	}

	public Body createBody(BodyDef def)
	{
		return world.createBody(def);
	}

	public void destroyBody(Body body)
	{
		world.destroyBody(body);
	}

	public Joint createJoint(JointDef def)
	{
		return world.createJoint(def);
	}

	public void destroyJoint(Joint joint)
	{
		world.destroyJoint(joint);
	}

	public void update(float dt)
	{
		super.update(dt);
		world.step(dt, velocityIterations, positionIterations);
	}

	public void clearForces()
	{
		world.clearForces();
	}

	public void setWarmStarting(boolean flag)
	{
		world.setWarmStarting(flag);
	}

	public void setContinuousPhysics(boolean flag)
	{
		world.setContinuousPhysics(flag);
	}

	public int getProxyCount()
	{
		return world.getProxyCount();
	}

	public int getBodyCount()
	{
		return world.getBodyCount();
	}

	public int getFixtureCount()
	{
		return world.getFixtureCount();
	}

	public int getJointCount()
	{
		return world.getJointCount();
	}

	public int getContactCount()
	{
		return world.getContactCount();
	}

	public void setGravity(Vector2 gravity)
	{
		world.setGravity(gravity);
	}

	public Vector2 getGravity()
	{
		return world.getGravity();
	}

	public boolean isLocked()
	{
		return world.isLocked();
	}

	public void setAutoClearForces(boolean flag)
	{
		world.setAutoClearForces(flag);
	}

	public boolean autoClearsForces()
	{
		return world.getAutoClearForces();
	}

	public void queryAABB(QueryCallback callback, float lowerX, float lowerY,
			float upperX, float upperY)
	{
		world.QueryAABB(callback, lowerX, lowerY, upperX, upperY);
	}

	public Array<Contact> contactList()
	{
		return world.getContactList();
	}

	public void getBodies(Array<Body> bodies)
	{
		world.getBodies(bodies);
	}

	public void dispose()
	{
		world.dispose();
	}

	public boolean equals(Object arg0)
	{
		return world.equals(arg0);
	}

	public void getFixtures(Array<Fixture> fixtures)
	{
		world.getFixtures(fixtures);
	}

	public void getJoints(Array<Joint> joints)
	{
		world.getJoints(joints);
	}

	public int hashCode()
	{
		return world.hashCode();
	}

	public void rayCast(RayCastCallback callback, Vector2 point1,
			Vector2 point2)
	{
		world.rayCast(callback, point1, point2);
	}

	public void rayCast(RayCastCallback callback, float point1x, float point1y,
			float point2x, float point2y)
	{
		world.rayCast(callback, point1x, point1y, point2x, point2y);
	}

	public String toString()
	{
		return world.toString();
	}
}
