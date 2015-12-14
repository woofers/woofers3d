package com.jaxson.lib.gdx.bullet;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.btAxisSweep3;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy;
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
import com.badlogic.gdx.physics.bullet.collision.btGhostPairCallback;
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
import com.jaxson.lib.gdx.bullet.bodies.EntityBody;
import com.jaxson.lib.gdx.bullet.bodies.PlayerBody;
import com.jaxson.lib.gdx.bullet.bodies.RigidBody;
import com.jaxson.lib.gdx.bullet.bodies.Floor;
import com.jaxson.lib.util.MyArrayList;
import java.lang.Math;

public class PhysicsWorld
{
	private final static short GROUND_FLAG  = 1 << 8;
	private final static short OBJECT_FLAG  = 1 << 9;
	private final static short ALL_FLAG     = -1;

	private final static int KINEMATIC_FLAG   = btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT;
	private final static int CALLBACK_FLAG    = btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK;

	private final static int CHARACTER_FILTER = btBroadphaseProxy.CollisionFilterGroups.CharacterFilter;
	private final static int STATIC_FILTER    = btBroadphaseProxy.CollisionFilterGroups.StaticFilter;
	private final static int DEFAULT_FILTER   = btBroadphaseProxy.CollisionFilterGroups.DefaultFilter;

	private final Vector3 GRAVITY = new Vector3(0, -5f, 0);

	private MyArrayList<EntityBody<?>> objects;
	private MyContactListener contactListener;
	private MyDebugDrawer debugDrawer;
	private btDefaultCollisionConfiguration collisionConfig;
	private btCollisionDispatcher dispatcher;
	private btAxisSweep3 sweep;
	private btSequentialImpulseConstraintSolver constraintSolver;
	private btDiscreteDynamicsWorld wolrd;

	public PhysicsWorld()
	{
		this(new Vector3(2000, 2000, 2000));
	}

	public PhysicsWorld(Vector3 worldSize)
	{
		BulletStarter.init();

		this.objects = new MyArrayList<EntityBody<?>>();
		this.contactListener = new MyContactListener();
		this.collisionConfig = new btDefaultCollisionConfiguration();
		this.dispatcher = new btCollisionDispatcher(collisionConfig);
		this.sweep = new btAxisSweep3(worldSize.scl(-1 / 2), worldSize.scl(1 / 2));
		this.constraintSolver = new btSequentialImpulseConstraintSolver();
		this.wolrd = new btDiscreteDynamicsWorld(dispatcher, sweep, constraintSolver, collisionConfig);
		this.debugDrawer = new MyDebugDrawer(wolrd);

		setGravity(GRAVITY);
		setDebugMode(MyDebugDrawer.MIXED);
	}

	public void add(PlayerBody entity)
	{
		objects.add(entity);
		//entity.setCollisionFlags(CHARACTER_FILTER);
		sweep.getOverlappingPairCache().setInternalGhostPairCallback(entity.getCallback());
		wolrd.addCollisionObject(entity.getBody(), (short)CHARACTER_FILTER, (short)(STATIC_FILTER | DEFAULT_FILTER));
		wolrd.addAction(entity.getCharacterController());
	}

	public void add(RigidBody entity)
	{
		add(entity, OBJECT_FLAG, GROUND_FLAG);
	}

	public void add(RigidBody entity, int group, int mask)
	{
		objects.add(entity);
		wolrd.addRigidBody(entity.getBody());
		entity.setContactCallbackFlag(group);
		entity.setContactCallbackFilter(mask);
		entity.addCollisionFlag(CALLBACK_FLAG);
	}

	public void add(Floor entity)
	{
		add(entity, GROUND_FLAG, ALL_FLAG);
		//entity.addCollisionFlag(KINEMATIC_FLAG);
		//entity.setActivationState(Collision.DISABLE_DEACTIVATION);
	}

	public int getDebugMode()
	{
		return debugDrawer.getDebugMode();
	}

	public EntityBody<?> getBody(Vector2 location, Camera camera)
	{
		return getBody((int)location.x, (int)location.y, camera);
	}

	public EntityBody<?> getBody(int x, int y, Camera camera)
	{
		return getBody(camera.getPickRay(x, y));
	}

	public EntityBody<?> getBody(Ray ray)
	{
		float length, newDistance, distance;
		Vector3 location;
		EntityBody<?> result = null;

		distance = -1f;
		for (EntityBody<?> entity: objects)
		{
			location = entity.getCenterLocation();
			length = ray.direction.dot(location.x - ray.origin.x, location.y - ray.origin.y, location.z - ray.origin.z);
			if (length < 0f) continue;
			newDistance = location.dst2(ray.origin.x + ray.direction.x * length, ray.origin.y +ray.direction.y * length, ray.origin.z + ray.direction.z * length);
			if (distance >= 0f && newDistance > distance) continue;
			//newDistance = ray.origin.dst2(location);
			//if (Intersector.intersectRaySphere(ray, location, entity.getRadius(), null))
			if (newDistance <= Math.pow(entity.getRadius(), 2))
			{
				result = entity;
				distance = newDistance;
			}
		}
		return result;
	}

	public Vector3 getGravity()
	{
		return wolrd.getGravity();
	}

	public void setDebugMode(int mode)
	{
		debugDrawer.setDebugMode(mode);
	}

	public void setGravity(Vector3 gravity)
	{
		wolrd.setGravity(gravity);
	}

	public void remove(RigidBody entity)
	{
		objects.remove(entity);
		wolrd.removeRigidBody(entity.getBody());
	}

	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch, Camera camera)
	{
		debugDrawer.render(spriteBatch, modelBatch, camera);
	}

	public void update(float dt)
	{
		wolrd.stepSimulation(dt);
	}
}

class MyContactListener extends ContactListener
{
	@Override
	public boolean onContactAdded(int userValue0, int partId0, int index0, int userValue1, int partId1, int index1)
	{
		//System.out.println("floor");
		return true;
	}
}
