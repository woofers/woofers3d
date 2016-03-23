package com.jaxson.lib.gdx.bullet.bodies;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btConvexShape;
import com.badlogic.gdx.physics.bullet.collision.btGhostPairCallback;
import com.badlogic.gdx.physics.bullet.collision.btPairCachingGhostObject;
import com.badlogic.gdx.physics.bullet.dynamics.btKinematicCharacterController;
import com.jaxson.lib.gdx.input.InputHandler;
import com.jaxson.lib.gdx.math.GdxMath;
import com.jaxson.lib.math.MyMath;

public abstract class PlayerBody extends ShapeBody<btPairCachingGhostObject>
{
	private static final float GHOST_MASS = -1f;
	private static final float STEP_HEIGHT = 1f / 4f;
	private static final float SPEED = 0.2f;
	private static final float ROTATION_SPEED = 2f;
	private static final float GRAVITY_SCALE = 3f;
	private static final float GRAVITY = GdxMath.GRAVITY_EARTH * GRAVITY_SCALE;
	private static final float FALL_SPEED = 55f;
	private static final float JUMP_SPEED = 10f;

	private btKinematicCharacterController characterController;
	private btGhostPairCallback callback;
	private Vector3 walkDirection;
	private float speed;
	private float rotationSpeed;
	private float stepHeight;

	public PlayerBody(Model model, btConvexShape shape)
	{
		super(model, shape, GHOST_MASS);
		setBody(new btPairCachingGhostObject());
		getBody().setCollisionShape(shape);
		this.characterController = new btKinematicCharacterController(getBody(), shape, STEP_HEIGHT);
		this.callback = new btGhostPairCallback();
		this.walkDirection = new Vector3();
		setGravity(GRAVITY);
		setSpeed(SPEED);
		setRotationSpeed(ROTATION_SPEED);
		setJumpSpeed(JUMP_SPEED);
		setFallSpeed(FALL_SPEED);
	}

	public PlayerBody(String modelPath, btConvexShape shape)
	{
		this(readModel(modelPath), shape);
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

	public float getGravity()
	{
		return getCharacterController().getGravity();
	}

	public float getMaxSlope()
	{
		return getMaxSlopeRad() * MyMath.RADIANS_TO_DEGREES;
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
	protected void input(float dt)
	{
		walkDirection.setZero();
		if (InputHandler.hasHardwareKeyboard())
		{
			if (onGround())
			{
				if (InputHandler.isDown(InputHandler.ANY_LEFT))
				{
					rotate(getRotationSpeed(), 0f, 0f);
				}
				if (InputHandler.isDown(InputHandler.ANY_RIGHT))
				{
					rotate(-getRotationSpeed(), 0f, 0f);
				}
			}
			if (InputHandler.isDown(InputHandler.ANY_UP))
			{
				walkDirection.add(getDirection());
			}
			if (InputHandler.isDown(InputHandler.ANY_DOWN))
			{
				walkDirection.sub(getDirection());
			}
			if (InputHandler.isDown(Keys.SPACE))
			{
				jump();
			}
		}
		if (InputHandler.hasTouchScreen())
		{
			if (InputHandler.justTouched())
			{
				jump();
			}
		}
		if (InputHandler.hasAccelerometer())
		{
			if (InputHandler.isAccelerometerForward())
			{
				walkDirection.add(getDirection());
				walkDirection.scl(InputHandler.getAccelerometerForward());
			}
			if (InputHandler.isAccelerometerBack())
			{
				walkDirection.sub(getDirection());
				walkDirection.scl(InputHandler.getAccelerometerBack());
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

	public void setGravity(float gravity)
	{
		getCharacterController().setGravity(gravity);
	}

	public void setJumpSpeed(float jumpSpeed)
	{
		getCharacterController().setJumpSpeed(jumpSpeed);
	}

	public void setMaxSlope(float maxSlope)
	{
		setMaxSlope(maxSlope * MyMath.DEGREES_TO_RADIANS);
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
