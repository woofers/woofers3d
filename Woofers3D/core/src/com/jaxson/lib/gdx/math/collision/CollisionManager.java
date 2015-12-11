package com.jaxson.lib.gdx.math.collision;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
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
import com.badlogic.gdx.physics.bullet.DebugDrawer;
import com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;
import com.jaxson.lib.gdx.graphics.g3d.RigidBody;
import com.jaxson.lib.util.MyArrayList;

public class CollisionManager
{
	private final static short GROUND_FLAG  = 1 << 8;
	private final static short OBJECT_FLAG  = 1 << 9;
	private final static short ALL_FLAG     = -1;
	private final static int KINEMATIC_FLAG = btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT;
	private final static int CALLBACK_FLAG  = btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK;

	private final Vector3 GRAVITY = new Vector3(0, -10f, 0);

	private MyArrayList<RigidBody> objects;
	private MyContactListener contactListener;
	private MyDebugDrawer debugDrawer;
	private btDefaultCollisionConfiguration collisionConfig;
	private btCollisionDispatcher dispatcher;
	private btDbvtBroadphase broadphase;
	private btSequentialImpulseConstraintSolver constraintSolver;
	private btDiscreteDynamicsWorld dynamicsWorld;

	public CollisionManager()
	{
		BulletStarter.init();

		this.objects = new MyArrayList<RigidBody>();
		this.contactListener = new MyContactListener();
		this.collisionConfig = new btDefaultCollisionConfiguration();
		this.dispatcher = new btCollisionDispatcher(collisionConfig);
		this.broadphase = new btDbvtBroadphase();
		this.constraintSolver = new btSequentialImpulseConstraintSolver();
		this.dynamicsWorld = new btDiscreteDynamicsWorld(dispatcher, broadphase, constraintSolver, collisionConfig);
		this.debugDrawer = new MyDebugDrawer(dynamicsWorld);

		setGravity(GRAVITY);
		setDebugMode(MyDebugDrawer.WIREFRAME);
	}

	public void add(RigidBody entity)
	{
		add(entity, OBJECT_FLAG, GROUND_FLAG);
		entity.addCollisionFlag(CALLBACK_FLAG);
	}

	public void add(RigidBody entity, int group, int mask)
	{
		objects.add(entity);
		dynamicsWorld.addRigidBody(entity.getBody());
		entity.setContactCallbackFlag(group);
		entity.setContactCallbackFilter(mask);
	}

	public void addFloor(RigidBody entity)
	{
		add(entity, GROUND_FLAG, ALL_FLAG);
		entity.addCollisionFlag(KINEMATIC_FLAG);
		entity.setActivationState(Collision.DISABLE_DEACTIVATION);
	}

	public int getDebugMode()
	{
		return debugDrawer.getDebugMode();
	}

	public Vector3 getGravity()
	{
		return dynamicsWorld.getGravity();
	}

	public void setDebugMode(int mode)
	{
		debugDrawer.setDebugMode(mode);
	}

	public void setGravity(Vector3 gravity)
	{
		dynamicsWorld.setGravity(gravity);
	}

	public void remove(RigidBody entity)
	{
		objects.remove(entity);
		dynamicsWorld.removeRigidBody(entity.getBody());
	}

	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch, Camera camera)
	{
		debugDrawer.render(spriteBatch, modelBatch, camera);
	}

	public void update(float dt)
	{
		dynamicsWorld.stepSimulation(dt);
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
