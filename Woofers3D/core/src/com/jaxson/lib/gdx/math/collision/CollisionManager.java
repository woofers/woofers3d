package com.jaxson.lib.gdx.math.collision;

import com.badlogic.gdx.math.Vector3;
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
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btSphereShape;
import com.badlogic.gdx.physics.bullet.collision.Collision;
import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;
import com.jaxson.lib.gdx.graphics.g3d.Entity;
import com.jaxson.lib.util.MyArrayList;

public class CollisionManager
{
	private final static short GROUND_FLAG = 1 << 8;
	private final static short OBJECT_FLAG = 1 << 9;
	private final static short ALL_FLAG    = -1;
	private final static Vector3 GRAVITY = new Vector3(0, -10f, 0);

	private MyArrayList<Entity> objects;
	private MyContactListener contactListener;
	private btDefaultCollisionConfiguration collisionConfig;
	private btCollisionDispatcher dispatcher;
	private btDbvtBroadphase broadphase;
	private btCollisionWorld collisionWorld;
	private btSequentialImpulseConstraintSolver constraintSolver;
	private btDiscreteDynamicsWorld dynamicsWorld;

	public CollisionManager()
	{
		this.objects = new MyArrayList<Entity>();
		this.contactListener = new MyContactListener();
		this.collisionConfig = new btDefaultCollisionConfiguration();
		this.dispatcher = new btCollisionDispatcher(collisionConfig);
		this.broadphase = new btDbvtBroadphase();
		this.collisionWorld = new btCollisionWorld(dispatcher, broadphase, collisionConfig);
		this.constraintSolver = new btSequentialImpulseConstraintSolver();
		this.dynamicsWorld = new btDiscreteDynamicsWorld(dispatcher, broadphase, constraintSolver, collisionConfig);
		this.dynamicsWorld.setGravity(new Vector3(0, -10f, 0));
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
		//dynamicsWorld.stepSimulation(delta, 5, 1f/60f);
	}
}

class MyContactListener extends ContactListener
{
	@Override
	public boolean onContactAdded(int userValue0, int partId0, int index0, int userValue1, int partId1, int index1)
	{
		System.out.println("floor");
		return true;
	}
}