package com.jaxson.lib.gdx.bullet.bodies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btConvexShape;
import com.badlogic.gdx.physics.bullet.collision.btGhostPairCallback;
import com.badlogic.gdx.physics.bullet.collision.btPairCachingGhostObject;
import com.badlogic.gdx.physics.bullet.dynamics.btKinematicCharacterController;
import com.badlogic.gdx.utils.UBJsonReader;
import com.jaxson.lib.gdx.input.KeyHandler;
import com.jaxson.lib.gdx.util.GdxMath;

public abstract class PlayerBody extends ShapeBody<btPairCachingGhostObject>
{
	private static final float STEP_HEIGHT = 0.3f;
	private static final float SPEED = 0.2f;
	private static final float ROTATION_SPEED = 2f;
	private static final float ACCELEROMETER_TOLERANCE = 0.5f;
	private static final float ROTATION_TOLERANCE = 8f;
	private static final float ACCELEROMETER_SCALE = 1f / 5f;

	private btKinematicCharacterController characterController;
	private btGhostPairCallback callback;
	private float speed;
	private float rotationSpeed;
	private float stepHeight;

	public PlayerBody(Model model, btConvexShape shape)
	{
		this(model, shape, DEFAULT_MASS);
	}

	public PlayerBody(Model model, btConvexShape shape, float mass)
	{
		super(model, shape, mass);
		setBody(new btPairCachingGhostObject());
		getBody().setCollisionShape(shape);
		this.characterController = new btKinematicCharacterController(getBody(), shape, STEP_HEIGHT);
		this.callback = new btGhostPairCallback();
		setSpeed(SPEED);
		setRotationSpeed(ROTATION_SPEED);
	}

	public PlayerBody(String modelPath, btConvexShape shape)
	{
		this(modelPath, shape, DEFAULT_MASS);
	}

	public PlayerBody(String modelPath, btConvexShape shape, float mass)
	{
		this(new G3dModelLoader(new UBJsonReader()).loadModel(Gdx.files.internal(modelPath)), shape, mass);
	}

	public boolean canJump()
	{
		return getCharacterController().canJump();
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

	public float getMaxSlope()
	{
		return getMaxSlopeRad() * GdxMath.RADIANS_TO_DEGREES;
	}

	public float getMaxSlopeRad()
	{
		return getCharacterController().getMaxSlope();
	}

	public float getRotationSpeed()
	{
		return rotationSpeed;
	}

	public float getSpeed()
	{
		return speed;
	}

	@Override
	protected void input()
	{
		Vector3 walkDirection = new Vector3();
		if (KeyHandler.hasHardwareKeyboard())
		{
			if (onGround())
			{
				if (KeyHandler.isDown(KeyHandler.ANY_LEFT))
				{
					rotate(getRotationSpeed(), 0, 0);
				}
				if (KeyHandler.isDown(KeyHandler.ANY_RIGHT))
				{
					rotate(-getRotationSpeed(), 0, 0);
				}
			}
			if (KeyHandler.isDown(KeyHandler.ANY_UP))
			{
				walkDirection.add(getDirection());
			}
			if (KeyHandler.isDown(KeyHandler.ANY_DOWN))
			{
				walkDirection.sub(getDirection());
			}
			if (KeyHandler.isDown(Keys.SPACE))
			{
				jump();
			}
		}
		if (KeyHandler.hasTouchScreen())
		{
			if (KeyHandler.justTouched())
			{
				jump();
			}
		}
		if (KeyHandler.hasAccelerometer())
		{
			Vector3 accelerometer = KeyHandler.getAccelerometer();
			//if (accelerometer.x < ROTATION_TOLERANCE)
			//{
			//	rotate(getRotationSpeed() * GdxMath.abs(accelerometer.x * ACCELEROMETER_SCALE), 0, 0);
			//}
			//if (accelerometer.x > ROTATION_TOLERANCE)
			//{
			//	rotate(-getRotationSpeed() * GdxMath.abs(accelerometer.x * ACCELEROMETER_SCALE), 0, 0);
			//}
			if (KeyHandler.isAccelerometerForward())
			{
				walkDirection.add(getDirection());
				walkDirection.scl(KeyHandler.getAccelerometerForward());
			}
			if (KeyHandler.isAccelerometerBack())
			{
				walkDirection.sub(getDirection());
				walkDirection.scl(KeyHandler.getAccelerometerBack());
			}
		}
		walkDirection.scl(getSpeed());
		getCharacterController().setWalkDirection(walkDirection);
		bodyToTransform();
	}

	public void jump()
	{
		getCharacterController().jump();
	}

	public boolean onGround()
	{
		return getCharacterController().onGround();
	}

	public void setFallSpeed(float fallSpeed)
	{
		getCharacterController().setFallSpeed(fallSpeed);
	}

	public void setJumpSpeed(float jumpSpeed)
	{
		getCharacterController().setJumpSpeed(jumpSpeed);
	}

	public void setMaxSlope(float maxSlope)
	{
		setMaxSlope(maxSlope * GdxMath.DEGREES_TO_RADIANS);
	}

	public void setMaxSlopeRad(float maxSlope)
	{
		getCharacterController().setMaxSlope(maxSlope);
	}

	public void setRotationSpeed(float rotationSpeed)
	{
		this.rotationSpeed = rotationSpeed;
	}

	public void setSpeed(float speed)
	{
		this.speed = speed;
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
	}
}
