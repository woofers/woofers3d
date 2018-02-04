package com.jaxson.lib.gdx.bullet.simulation;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
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
import com.jaxson.lib.gdx.graphics.g3d.environment.MyEnvironment;
import com.jaxson.lib.gdx.graphics.views.TargetCamera;
import com.jaxson.lib.gdx.graphics.views.View;
import com.jaxson.lib.gdx.input.Inputs;
import com.jaxson.lib.gdx.input.Keyboard;
import com.jaxson.lib.gdx.input.KeyboardKey;
import com.jaxson.lib.gdx.input.TouchScreen;
import com.jaxson.lib.gdx.io.GdxFile;
import com.jaxson.lib.gdx.math.GdxMath;
import com.jaxson.lib.gdx.util.GameObject;
import com.jaxson.lib.util.MyArrayList;
import com.jaxson.lib.util.Optional;

public class BulletWorld extends GameObject
{
    protected static final short GROUND_FLAG = 1 << 8;
    protected static final short OBJECT_FLAG = 1 << 9;
    protected static final short ALL_FLAG = -1;

    protected static final int KINEMATIC_FLAG
            = btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT;

    protected static final int CALLBACK_FLAG
            = btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK;

    protected static final int CHARACTER_FLAG
            = btCollisionObject.CollisionFlags.CF_CHARACTER_OBJECT;

    protected static final int CHARACTER_FILTER
            = btBroadphaseProxy.CollisionFilterGroups.CharacterFilter;

    protected static final int STATIC_FILTER
            = btBroadphaseProxy.CollisionFilterGroups.StaticFilter;

    protected static final int DEFAULT_FILTER
            = btBroadphaseProxy.CollisionFilterGroups.DefaultFilter;

    protected static final int DISABLE_DEACTIVATION
            = CollisionConstants.DISABLE_DEACTIVATION;

    protected static final float VECOTR_TO_MAX = 2f;
    protected static final float VECOTR_TO_MIN = -VECOTR_TO_MAX;
    protected static final Vector3 WORLD_SIZE = new Vector3(100f, 100f, 100f);
    protected static final Vector3 GRAVITY
            = new Vector3(0, -GdxMath.GRAVITY_EARTH, 0);

    private MyArrayList<EntityBody> objects;
    private MyContactListener contactListener;
    private BulletDebug debug;
    private btDefaultCollisionConfiguration collisionConfig;
    private btCollisionDispatcher dispatcher;
    private btAxisSweep3 broadphase;
    private btSequentialImpulseConstraintSolver constraintSolver;
    private btSoftRigidDynamicsWorld world;
    private btSoftBodyWorldInfo worldInfo;
    private Vector3 worldSize;
    private BulletRay rayCallback;
    private WorldImporter importer;

    private Keyboard keyboard;
    private TouchScreen touchScreen;
    private KeyboardKey debugKey;

    public BulletWorld(MyEnvironment environment)
    {
        this(environment, WORLD_SIZE);
    }

    public BulletWorld(MyEnvironment environment, Vector3 worldSize)
    {
        this(environment,
                worldSize.cpy().scl(VECOTR_TO_MIN),
                worldSize.scl(VECOTR_TO_MAX));
    }

    public BulletWorld(MyEnvironment environment,
            Vector3 minSize,
            Vector3 maxSize)
    {
        BulletStarter.init();

        this.worldSize = maxSize.sub(minSize);
        environment.setWorldSize(worldSize);
        this.objects = new MyArrayList<>();
        this.contactListener = new MyContactListener();
        this.collisionConfig = new btDefaultCollisionConfiguration();
        this.dispatcher = new btCollisionDispatcher(collisionConfig);
        this.broadphase = new btAxisSweep3(minSize, maxSize);
        this.constraintSolver = new btSequentialImpulseConstraintSolver();
        this.world = new btSoftRigidDynamicsWorld(dispatcher,
                broadphase,
                constraintSolver,
                collisionConfig);
        this.debug = new BulletDebug(world);
        this.rayCallback = new BulletRay();

        this.worldInfo = new btSoftBodyWorldInfo();
        this.worldInfo.setBroadphase(broadphase);
        this.worldInfo.setDispatcher(dispatcher);
        this.worldInfo.getSparsesdf().Initialize();

        this.keyboard = Inputs.keyboard();
        this.touchScreen = Inputs.touchScreen();
        this.debugKey = keyboard.key("F5");

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
        entity.setActivationState(DISABLE_DEACTIVATION);
        add(entity, GROUND_FLAG, ALL_FLAG);
    }

    public void add(PlayerBody entity)
    {
        if (contains(entity)) return;
        objects.add(entity);
        entity.setCollisionFlags(CHARACTER_FLAG);
        broadphase.getOverlappingPairCache()
                .setInternalGhostPairCallback(entity.callback());
        world.addCollisionObject(
                entity.body(),
                (short) CHARACTER_FILTER,
                (short) (STATIC_FILTER | DEFAULT_FILTER));
        world.addAction(entity.characterController());
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
        world.addRigidBody(entity.body());
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
        world.addSoftBody(entity.body());
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

    @Override
    public void dispose()
    {
        debug.dispose();
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

    public MyArrayList<EntityBody> entities()
    {
        return objects;
    }

    public Vector3 gravity()
    {
        return world.getGravity();
    }

    @Override
    protected void input(float dt)
    {
        if (keyboard.exists() && debugKey.isPressed()
                || touchScreen.exists() && touchScreen.fingersTouched(3))
        {
            debug.toggle();
        }
    }

    public MyArrayList<RigidBody> load(GdxFile file)
    {
        importer = new WorldImporter(file, world);
        for (RigidBody body: importer.entities())
            add(body);
        return importer.entities();
    }

    protected void rayTest(Vector3 rayStart,
            Vector3 rayEnd, ClosestRayResultCallback callback)
    {
        world.rayTest(rayStart, rayEnd, callback);
    }

    public Optional<EntityBody> rayTrace(float x, float y, Camera camera)
    {
        return rayTrace(camera.getPickRay(x, y));
    }

    public Optional<EntityBody> rayTrace(Ray ray)
    {
        return rayTrace(ray, BulletRay.MAX_DISTANCE);
    }

    public Optional<EntityBody> rayTrace(Ray ray, float distance)
    {
        Optional<btCollisionObject> body
                = rayCallback.collisionObject(ray, distance, this);
        if (!body.exists()) return new Optional<>();
        for (EntityBody entity: objects)
            if (entity.isBody(body.unwrap())) return new Optional<>(entity);
        return new Optional<>();
    }

    public Optional<EntityBody> rayTrace(Vector2 location, Camera camera)
    {
        return rayTrace(location.x, location.y, camera);
    }

    public void remove(PlayerBody entity)
    {
        if (!contains(entity)) return;
        objects.remove(entity);
        broadphase.getOverlappingPairCache()
                .setInternalGhostPairCallback(null);
        world.removeAction(entity.characterController());
        world.removeCollisionObject(entity.body());
    }

    public void remove(RigidBody entity)
    {
        if (!contains(entity)) return;
        objects.remove(entity);
        world.removeRigidBody(entity.body());
    }

    public void remove(SoftBody entity)
    {
        if (!contains(entity)) return;
        objects.remove(entity);
        world.removeSoftBody(entity.body());
    }

    @Override
    public void render(View view)
    {
        debug.render(view);
    }

    public void setGravity(Vector3 gravity)
    {
        world.setGravity(gravity);
    }

    @Override
    public void update(float dt)
    {
        super.update(dt);
        world.stepSimulation(dt);
    }

    public btSoftBodyWorldInfo worldInfo()
    {
        return worldInfo;
    }

    public Vector3 worldSize()
    {
        return worldSize;
    }
}
