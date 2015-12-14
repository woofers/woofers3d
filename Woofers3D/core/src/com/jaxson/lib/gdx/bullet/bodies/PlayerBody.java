package com.jaxson.lib.gdx.bullet.bodies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btConvexShape;
import com.badlogic.gdx.physics.bullet.collision.btGhostPairCallback;
import com.badlogic.gdx.physics.bullet.collision.btPairCachingGhostObject;
import com.badlogic.gdx.physics.bullet.dynamics.btKinematicCharacterController;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.jaxson.lib.gdx.bullet.MyMotionState;
import com.jaxson.lib.gdx.input.KeyHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public abstract class PlayerBody extends EntityBody<btPairCachingGhostObject>
{
	private static final float STEP_HEIGHT    = 0.3f;
	private static final float SPEED          = 0.12f;
	private static final float ROTATION_SPEED = 2f;

	private btKinematicCharacterController characterController;
	private btGhostPairCallback callback;
	private float speed;
	private float stepHeight;

	public PlayerBody(String modelPath, btConvexShape shape)
	{
		this(modelPath, shape, MASS);
	}

	public PlayerBody(String modelPath, btConvexShape shape, float mass)
	{
		this(new ObjLoader().loadModel(Gdx.files.internal(modelPath)), shape, mass);
	}

	public PlayerBody(Model model, btConvexShape shape)
	{
		this(model, shape, MASS);
	}

	public PlayerBody(Model model, btConvexShape shape, float mass)
	{
		super(model, shape, mass);
		setBody(new btPairCachingGhostObject());
		getBody().setCollisionShape(shape);
		this.characterController = new btKinematicCharacterController(getBody(), shape, STEP_HEIGHT);
		this.callback = new btGhostPairCallback();
		setSpeed(SPEED);
		transformToBody();
	}

	private void bodyToTransform()
	{
		getBody().getWorldTransform(getTransform());
	}

	public boolean canJump()
	{
		return characterController.canJump();
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	public btGhostPairCallback getCallback()
	{
		return callback;
	}

	public btKinematicCharacterController getCharacterController()
	{
		return characterController;
	}

	public float getSpeed()
	{
		return speed;
	}

	public void jump()
	{
		characterController.jump();
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
		transformToBody();
	}

	public void setLocation(Vector3 location)
	{
		getTransform().set(location, getRoationQuat());
		transformToBody();
	}

	public void setRotation(Vector3 angles)
	{
		setRotation(angles.x, angles.y, angles.z);
	}

	public void setRotation(float yaw, float pitch, float roll)
	{
		getTransform().setFromEulerAngles(yaw, pitch, roll);
		transformToBody();
	}

	public void setSpeed(float speed)
	{
		this.speed = speed;
	}

	public void translate(Vector3 translation)
	{
		getTransform().translate(translation);
		transformToBody();
	}

	public void translateABS(Vector3 translation)
	{
		getTransform().trn(translation);
		transformToBody();
	}

	@Override
	public void update(float dt)
	{
		Vector3 walkDirection = new Vector3();
		if (KeyHandler.isDown(KeyHandler.LEFT))
		{
			rotate(ROTATION_SPEED, 0, 0);
		}
		if (KeyHandler.isDown(KeyHandler.RIGHT))
		{
			rotate(-ROTATION_SPEED, 0, 0);
		}
		if (KeyHandler.isDown(KeyHandler.FORWARD))
		{
			walkDirection.add(getDirection());
		}
		if (KeyHandler.isDown(KeyHandler.BACK))
		{
			walkDirection.sub(getDirection());
		}
		if (KeyHandler.isDown(KeyHandler.SPACE))
		{
			if (canJump()) jump();
		}
		walkDirection.scl(speed);
		characterController.setWalkDirection(walkDirection);
		bodyToTransform();
	}

	private void transformToBody()
	{
		getBody().setWorldTransform(getTransform());
	}
}
