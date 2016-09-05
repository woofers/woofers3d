package com.jaxson.lib.gdx.bullet.simulation;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
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
import com.jaxson.lib.gdx.bullet.BulletStarter;
import com.jaxson.lib.gdx.bullet.simulation.bodies.Floor;
import com.jaxson.lib.gdx.bullet.simulation.bodies.types.EntityBody;
import com.jaxson.lib.gdx.bullet.simulation.bodies.types.PlayerBody;
import com.jaxson.lib.gdx.bullet.simulation.bodies.types.RigidBody;
import com.jaxson.lib.gdx.bullet.simulation.bodies.types.SoftBody;
import com.jaxson.lib.gdx.graphics.views.TargetCamera;
import com.jaxson.lib.gdx.graphics.views.View;
import com.jaxson.lib.gdx.input.Inputs;
import com.jaxson.lib.gdx.io.GdxFile;
import com.jaxson.lib.gdx.math.GdxMath;
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
	protected static final Vector3 WORLD_SIZE = new Vector3(100f, 100f, 100f);
	protected static final Vector3 GRAVITY = new Vector3(0, -GdxMath.GRAVITY_EARTH, 0);

	private MyArrayList<EntityBody> objects;
	private MyContactListener contactListener;
	private MyDebugDrawer debugDrawer;
	private btDefaultCollisionConfiguration collisionConfig;
	private btCollisionDispatcher dispatcher;
	private btAxisSweep3 broadphase;
	private btSequentialImpulseConstraintSolver constraintSolver;
	private btSoftRigidDynamicsWorld world;
	private btSoftBodyWorldInfo worldInfo;
	private Vector3 worldSize;
	private RayCallback rayCallback;
	private WorldImporter importer;

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

		this.worldSize = maxSize.sub(minSize);
		this.objects = new MyArrayList<>();
		this.contactListener = new MyContactListener();
		this.collisionConfig = new btDefaultCollisionConfiguration();
		this.dispatcher = new btCollisionDispatcher(collisionConfig);
		this.broadphase = new btAxisSweep3(minSize, maxSize);
		this.constraintSolver = new btSequentialImpulseConstraintSolver();
		this.world = new btSoftRigidDynamicsWorld(dispatcher, broadphase, constraintSolver, collisionConfig);
		this.debugDrawer = new MyDebugDrawer(world);
		this.rayCallback = new RayCallback();

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
		if (contains(entity)) return;
		if (hasMovement) entity.addCollisionFlag(KINEMATIC_FLAG);
		entity.setActivationState(CollisionConstants.DISABLE_DEACTIVATION);
		add(entity, GROUND_FLAG, ALL_FLAG);
	}

	public void add(PlayerBody entity)
	{
		if (contains(entity)) return;
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
		if (contains(entity)) return;
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
		if (contains(entity)) return;
		objects.add(entity);
		entity.addCollisionFlag(CALLBACK_FLAG);
		world.addSoftBody(entity.getBody());
		entity.setContactCallbackFlag(group);
		entity.setContactCallbackFilter(mask);
	}

	public void add(TargetCamera camera)
	{
		camera.setWorld(this);
	}

	public boolean contains(EntityBody entity)
	{
		return objects.contains(entity);
	}

	public void dispose()
	{
		debugDrawer.dispose();
		worldInfo.dispose();
		world.dispose();
		if (importer != null) importer.dispose();
		contactListener.dispose();
		collisionConfig.dispose();
		dispatcher.dispose();
		broadphase.dispose();
		constraintSolver.dispose();
		rayCallback.dispose();
	}

	public EntityBody getBody(float x, float y, Camera camera)
	{
		return getBody(camera.getPickRay(x, y));
	}

	public EntityBody getBody(Ray ray)
	{
		btCollisionObject object = rayCallback.getCollisionObject(ray, this);
		for (EntityBody entity: objects)
		{
			if (entity.isBody(object)) return entity;
		}
		return null;
	}

	public EntityBody getBody(Vector2 location, Camera camera)
	{
		return getBody(location.x, location.y, camera);
	}

	public int getDebugMode()
	{
		return debugDrawer.getDebugMode();
	}

	public MyArrayList<EntityBody> getEntities()
	{
		return objects;
	}

	public Vector3 getGravity()
	{
		return world.getGravity();
	}

	public btSoftBodyWorldInfo getWorldInfo()
	{
		return worldInfo;
	}

	public Vector3 getWorldSize()
	{
		return worldSize;
	}

	public MyArrayList<RigidBody> load(GdxFile file)
	{
		importer = new WorldImporter(file, world);
		return importer.getEntities();
	}

	public MyArrayList<RigidBody> load(String path)
	{
		return load(new GdxFile(path, FileType.Internal));
	}

	public void remove(PlayerBody entity)
	{
		if (!contains(entity)) return;
		objects.remove(entity);
		world.removeCollisionObject(entity.getBody());
		world.removeAction(entity.getCharacterController());
	}

	public void remove(RigidBody entity)
	{
		if (!contains(entity)) return;
		objects.remove(entity);
		world.removeRigidBody(entity.getBody());
	}

	public void remove(SoftBody entity)
	{
		if (!contains(entity)) return;
		objects.remove(entity);
		world.removeSoftBody(entity.getBody());
	}

	public void render(View view)
	{
		debugDrawer.render(view);
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
		if (Inputs.getKeyboard().exists() && Inputs.getKeyboard().getKey(Keys.F5).isPressed()) toggleDebugMode();
		if (Inputs.getTouchScreen().exists() && Inputs.getTouchScreen().twoFingerTouched()) toggleDebugMode();
	}

	protected void rayTest(Vector3 rayStart, Vector3 rayEnd, RayCallback callback)
	{
		world.rayTest(rayStart, rayEnd, callback);
	}
}
