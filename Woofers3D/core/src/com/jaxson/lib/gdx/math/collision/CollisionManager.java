package com.jaxson.lib.gdx.math.collision;

import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btCapsuleShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;
import com.badlogic.gdx.physics.bullet.collision.btConeShape;
import com.badlogic.gdx.physics.bullet.collision.btCylinderShape;
import com.badlogic.gdx.physics.bullet.collision.btDbvtBroadphase;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btSphereShape;
import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.jaxson.lib.gdx.graphics.g3d.Entity;
import com.jaxson.lib.util.MyArrayList;

public class CollisionManager
{
	private final static short GROUND_FLAG = 1 << 8;
	private final static short OBJECT_FLAG = 1 << 9;
	private final static short ALL_FLAG    = -1;

	private MyArrayList<Entity> objects;
	private btDefaultCollisionConfiguration collisionConfig;
	private btCollisionDispatcher dispatcher;
	private btDbvtBroadphase broadphase;
	private btCollisionWorld collisionWorld;

	public CollisionManager()
	{
		this.objects = new MyArrayList<Entity>();
		this.collisionConfig = new btDefaultCollisionConfiguration();
		this.dispatcher = new btCollisionDispatcher(collisionConfig);
		this.broadphase = new btDbvtBroadphase();
		this.collisionWorld = new btCollisionWorld(dispatcher, broadphase, collisionConfig);
	}

	public void add(Entity entity)
	{
		add(entity, OBJECT_FLAG, GROUND_FLAG);
	}

	public void add(Entity entity, short group, short mask)
	{
		objects.add(entity);
		collisionWorld.addCollisionObject(entity.getBody(), group, mask);
	}

	public void addFloor(Entity entity)
	{
		add(entity, GROUND_FLAG, ALL_FLAG);
	}

	public void remove(Entity entity)
	{
		objects.remove(entity);
		collisionWorld.removeCollisionObject(entity.getBody());
	}

	public void update(float dt)
	{
		collisionWorld.performDiscreteCollisionDetection();
	}
}
