package com.jaxson.lib.gdx.graphics.g3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCapsuleShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.jaxson.lib.gdx.graphics.GameObject;
import com.jaxson.lib.gdx.math.collision.MyMotionState;
import java.lang.Math;

public abstract class Entity extends GameObject
{
	protected static final Vector3 LOCATION = Vector3.Zero;
	private final btCollisionShape SHAPE = new btCapsuleShape(1f, 1f);
	private static final float MASS = 1f;

	private ModelInstance modelInstance;
	private MyMotionState motionState;
	private float mass;
	private btRigidBody body;
	private btCollisionShape shape;

	public Entity(String modelPath)
	{
		this(modelPath, LOCATION);
	}

	public Entity(String modelPath, Vector3 location)
	{
		this(new ObjLoader().loadModel(Gdx.files.internal(modelPath)), location);
	}

	public Entity(Model model)
	{
		this(model, LOCATION);
	}

	public Entity(Model model, Vector3 location)
	{
		this(model, location, MASS);
	}

	public Entity(Model model, Vector3 location, float mass)
	{
		this.modelInstance = new ModelInstance(model, location);
		this.motionState = new MyMotionState();
		this.mass = mass;
		this.shape = SHAPE;
		this.motionState.setWorldTransform(getTransform());
		this.body = new btRigidBody(mass, motionState, shape, getInertia());
		setCollisionShape(shape);
	}

	public void addCollisionFlag(int flag)
	{
		setCollisionFlags(getCollisionFlags() | flag);
	}

	@Override
	public void dispose()
	{
		modelInstance.model.dispose();
	}

	public int getActivationState()
	{
		return body.getActivationState();
	}

	public btRigidBody getBody()
	{
		return body;
	}

	public BoundingBox getBoundingBox()
	{
		return modelInstance.calculateBoundingBox(new BoundingBox());
	}

	public Vector3 getCenterLocation()
	{
		return getRadius().add(getLocation());
	}

	public int getContactCallbackFlag()
	{
		return body.getContactCallbackFlag();
	}

    public int getContactCallbackFilter()
    {
		return body.getContactCallbackFilter();
    }

	public int getCollisionFlags()
	{
		return body.getCollisionFlags();
	}

	public Vector3 getDirection()
	{
		float[] matrix = getTransform().getValues();
		return new Vector3(matrix[8], matrix[9], matrix[10]);
	}

	public Vector3 getInertia()
	{
		Vector3 inertia = new Vector3();
		if (mass <= 0) return inertia;
		shape.calculateLocalInertia(mass, inertia);
		return inertia;
	}

	public float getMass()
	{
		return mass;
	}

	public ModelInstance getModelInstance()
	{
		return modelInstance;
	}

	public Model getModel()
	{
		return modelInstance.model;
	}

	public Vector3 getLocation()
	{
		return getTransform().getTranslation(new Vector3());
	}

	public Vector3 getScale()
	{
		return getTransform().getScale(new Vector3());
	}

	public Vector3 getRadius()
	{
		return getBoundingBox().getCenter(new Vector3());
	}

	public Vector3 getRotation()
	{
		Quaternion rotation = getRoationQuat();
		return new Vector3(rotation.getYaw(), rotation.getPitch(), rotation.getRoll());
	}

	public Quaternion getRoationQuat()
	{
		return getTransform().getRotation(new Quaternion());
	}

	public Matrix4 getTransform()
	{
		return modelInstance.transform;
	}

	public boolean hasBody()
	{
		return body != null;
	}

	public void rotate(Vector3 angles)
	{
		rotate(angles.x, angles.y, angles.z);
	}

	public void rotate(float yaw, float pitch, float roll)
	{
		getTransform().rotate(Vector3.Y, yaw);
		getTransform().rotate(Vector3.X, pitch);
		getTransform().rotate(Vector3.Z, roll);
		updateBody();
	}

	public void setActivationState(int state)
	{
		if (!hasBody()) return;
		body.setActivationState(state);
	}

	public void setContactCallbackFlag(int flag)
	{
		body.setContactCallbackFlag(flag);
	}

    public void setContactCallbackFilter(int flag)
    {
		body.setContactCallbackFilter(flag);
    }

	public void setCollisionFlags(int flags)
	{
		body.setContactCallbackFilter(flags);
	}

	public void setCollisionShape(btCollisionShape shape)
	{
		body.setCollisionShape(shape);
	}

	public void setLocation(Vector3 location)
	{
		getTransform().set(location, getRoationQuat());
		updateBody();
	}

	public void setRotation(Vector3 angles)
	{
		setRotation(angles.x, angles.y, angles.z);
	}

	public void setRotation(float yaw, float pitch, float roll)
	{
		modelInstance.transform.setFromEulerAngles(yaw, pitch, roll);
		updateBody();
	}

	public void setScale(Vector3 scale)
	{
		modelInstance.transform.scl(scale);
		updateBody();
	}

	public void translate(Vector3 translation)
	{
		getTransform().translate(translation);
		updateBody();
	}

	public void translateABS(Vector3 translation)
	{
		getTransform().trn(translation);
		updateBody();
	}

	@Override
	public void update(float dt)
	{
		body.getWorldTransform(getTransform());
		body.transform.rotate(Vector3.Y, 1f);
	}

	private void updateBody()
	{
		body.proceedToTransform(getTransform());
	}
}
