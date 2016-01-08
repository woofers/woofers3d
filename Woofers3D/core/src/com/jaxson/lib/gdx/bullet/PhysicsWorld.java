package com.jaxson.lib.gdx.bullet;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.bullet.collision.CollisionConstants;
import com.badlogic.gdx.physics.bullet.collision.btAxisSweep3;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.badlogic.gdx.physics.bullet.softbody.btSoftBodyWorldInfo;
import com.badlogic.gdx.physics.bullet.softbody.btSoftRigidDynamicsWorld;
import com.jaxson.lib.gdx.bullet.bodies.EntityBody;
import com.jaxson.lib.gdx.bullet.bodies.Floor;
import com.jaxson.lib.gdx.bullet.bodies.PlayerBody;
import com.jaxson.lib.gdx.bullet.bodies.RigidBody;
import com.jaxson.lib.gdx.bullet.bodies.SoftBody;
import com.jaxson.lib.gdx.input.InputHandler;
import com.jaxson.lib.gdx.util.GdxMath;
import com.jaxson.lib.util.MyArrayList;

public class PhysicsWorld
{
	protected static final short GROUND_FLAG = 1 << 8;
	protected static final short OBJECT_FLAG = 1 << 9;
	protected static final short ALL_FLAG = -1;

	protected static final int KINEMATIC_FLAG = btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT;
	protected static final int CALLBACK_FLAG = btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK;
	protected static final int CHARACTER_FLAG = btCollisionObject.CollisionFlags.CF_CHARACTER_OBJECT;

	protected static final int CHARACTER_FILTER = btBroadphaseProxy.CollisionFilterGroups.CharacterFilter;
	protected static final int STATIC_FILTER = btBroadphaseProxy.CollisionFilterGroups.StaticFilter;
	protected static final int DEFAULT_FILTER = btBroadphaseProxy.CollisionFilterGroups.DefaultFilter;

	protected static final float VECOTR_TO_MAX = GdxMath.HALF;
	protected static final float VECOTR_TO_MIN = -GdxMath.HALF;
	protected static final Vector3 WORLD_SIZE = new Vector3(2000, 2000, 2000);
	protected static final Vector3 GRAVITY = new Vector3(0, -GdxMath.GRAVITY_EARTH, 0);

	private MyArrayList<EntityBody<?>> objects;
	private MyContactListener contactListener;
	private MyDebugDrawer debugDrawer;
	private btDefaultCollisionConfiguration collisionConfig;
	private btCollisionDispatcher dispatcher;
	private btAxisSweep3 broadphase;
	private btSequentialImpulseConstraintSolver constraintSolver;
	private btSoftRigidDynamicsWorld world;
	private btSoftBodyWorldInfo worldInfo;

	public PhysicsWorld()
	{
		this(WORLD_SIZE);
	}

	public PhysicsWorld(Vector3 worldSize)
	{
		this(worldSize.cpy().scl(VECOTR_TO_MIN), worldSize.scl(VECOTR_TO_MAX));
	}

	public PhysicsWorld(Vector3 minSize, Vector3 maxSize)
	{
		BulletStarter.init();

		this.objects = new MyArrayList<EntityBody<?>>();
		this.contactListener = new MyContactListener();
		this.collisionConfig = new btDefaultCollisionConfiguration();
		this.dispatcher = new btCollisionDispatcher(collisionConfig);
		this.broadphase = new btAxisSweep3(minSize, maxSize);
		this.constraintSolver = new btSequentialImpulseConstraintSolver();
		this.world = new btSoftRigidDynamicsWorld(dispatcher, broadphase, constraintSolver, collisionConfig);
		this.debugDrawer = new MyDebugDrawer(world);

		this.worldInfo = new btSoftBodyWorldInfo();
		this.worldInfo.setBroadphase(broadphase);
		this.worldInfo.setDispatcher(dispatcher);
		this.worldInfo.getSparsesdf().Initialize();

		setGravity(GRAVITY);
	}

	public void add(Floor entity)
	{
		add(entity, false);
	}

	public void add(Floor entity, boolean hasMovement)
	{
		if (hasMovement) entity.addCollisionFlag(KINEMATIC_FLAG);
		entity.setActivationState(CollisionConstants.DISABLE_DEACTIVATION);
		add(entity, GROUND_FLAG, ALL_FLAG);
	}

	public void add(PlayerBody entity)
	{
		objects.add(entity);
		entity.setCollisionFlags(CHARACTER_FLAG);
		broadphase.getOverlappingPairCache().setInternalGhostPairCallback(entity.getCallback());
		world.addCollisionObject(entity.getBody(), (short) CHARACTER_FILTER, (short) (STATIC_FILTER | DEFAULT_FILTER));
		world.addAction(entity.getCharacterController());
	}

	public void add(RigidBody entity)
	{
		add(entity, OBJECT_FLAG, GROUND_FLAG);
	}

	public void add(RigidBody entity, int group, int mask)
	{
		objects.add(entity);
		entity.addCollisionFlag(CALLBACK_FLAG);
		world.addRigidBody(entity.getBody());
		entity.setContactCallbackFlag(group);
		entity.setContactCallbackFilter(mask);
	}

	public void add(SoftBody entity)
	{
		add(entity, OBJECT_FLAG, GROUND_FLAG);
	}

	public void add(SoftBody entity, int group, int mask)
	{
		objects.add(entity);
		entity.addCollisionFlag(CALLBACK_FLAG);
		world.addSoftBody(entity.getBody());
		entity.setContactCallbackFlag(group);
		entity.setContactCallbackFilter(mask);
	}

	public EntityBody<?> getBody(float x, float y, Camera camera)
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
			newDistance = location.dst2(ray.origin.x + ray.direction.x * length,
					ray.origin.y + ray.direction.y * length, ray.origin.z + ray.direction.z * length);
			if (distance >= 0f && newDistance > distance) continue;
			if (newDistance <= Math.pow(entity.getRadius(), 2))
			{
				result = entity;
				distance = newDistance;
			}
		}
		return result;
	}

	public EntityBody<?> getBody(Vector2 location, Camera camera)
	{
		return getBody(location.x, location.y, camera);
	}

	public int getDebugMode()
	{
		return debugDrawer.getDebugMode();
	}

	public Vector3 getGravity()
	{
		return world.getGravity();
	}

	public btSoftBodyWorldInfo getWorldInfo()
	{
		return worldInfo;
	}

	public void remove(RigidBody entity)
	{
		objects.remove(entity);
		world.removeRigidBody(entity.getBody());
	}

	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch, Camera camera)
	{
		debugDrawer.render(spriteBatch, modelBatch, camera);
	}

	public void setDebugMode(int mode)
	{
		debugDrawer.setDebugMode(mode);
	}

	public void setGravity(Vector3 gravity)
	{
		world.setGravity(gravity);
	}

	public void toggleDebugMode()
	{
		debugDrawer.toggleDebugMode();
	}

	public void update(float dt)
	{
		world.stepSimulation(dt);
		if (InputHandler.isPressed(Keys.F5)) toggleDebugMode();
	}
}
