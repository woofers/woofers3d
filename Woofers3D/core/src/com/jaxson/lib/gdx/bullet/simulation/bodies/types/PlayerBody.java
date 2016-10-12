package com.jaxson.lib.gdx.bullet.simulation.bodies.types;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btGhostPairCallback;
import com.badlogic.gdx.physics.bullet.collision.btPairCachingGhostObject;
import com.badlogic.gdx.physics.bullet.dynamics.btKinematicCharacterController;
import com.jaxson.lib.gdx.bullet.simulation.collision.types.ConvexShape;
import com.jaxson.lib.gdx.input.Accelerometer;
import com.jaxson.lib.gdx.input.Inputs;
import com.jaxson.lib.gdx.input.Keyboard;
import com.jaxson.lib.gdx.input.KeyboardKey;
import com.jaxson.lib.gdx.input.TouchScreen;
import com.jaxson.lib.gdx.math.GdxMath;
import com.jaxson.lib.math.MyMath;

public abstract class PlayerBody
		extends ShapeBody<btPairCachingGhostObject, ConvexShape>
{
	private static final float GHOST_MASS = -1f;
	private static final float STEP_HEIGHT = 1f / 5f;
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

	private Keyboard keyboard;
	private Accelerometer accelerometer;
	private TouchScreen touchScreen;
	private KeyboardKey forwardKey;
	private KeyboardKey backwardKey;
	private KeyboardKey leftKey;
	private KeyboardKey rightKey;
	private KeyboardKey jumpKey;

	public PlayerBody(Model model)
	{
		this(model, fittedHitbox(model));
	}

	public PlayerBody(Model model, ConvexShape shape)
	{
		super(model, new btPairCachingGhostObject(), shape, GHOST_MASS);
		setStepHeight(STEP_HEIGHT);
		this.callback = new btGhostPairCallback();
		this.walkDirection = new Vector3();
		setGravity(GRAVITY);
		setSpeed(SPEED);
		setRotationSpeed(ROTATION_SPEED);
		setJumpSpeed(JUMP_SPEED);
		setFallSpeed(FALL_SPEED);

		this.keyboard = Inputs.keyboard();
		this.accelerometer = Inputs.accelerometer();
		this.touchScreen = Inputs.touchScreen();
		this.forwardKey = keyboard.key("W");
		this.backwardKey = keyboard.key("S");
		this.leftKey = keyboard.key("A");
		this.rightKey = keyboard.key("D");
		this.jumpKey = keyboard.key("Space");
	}

	public boolean canJump()
	{
		return characterController().canJump();
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	public btGhostPairCallback callback()
	{
		return callback;
	}

	public btKinematicCharacterController characterController()
	{
		return characterController;
	}

	public float gravity()
	{
		return characterController().getGravity();
	}

	public float maxSlope()
	{
		return maxSlopeRad() * MyMath.RADIANS_TO_DEGREES;
	}

	public float maxSlopeRad()
	{
		return characterController().getMaxSlope();
	}

	public float rotationSpeed()
	{
		return rotationSpeed;
	}

	public float speed()
	{
		return speed;
	}

	public float stepHeight()
	{
		return stepHeight;
	}

	public void jump()
	{
		characterController().jump();
	}

	public boolean onGround()
	{
		return characterController().onGround();
	}

	public void setFallSpeed(float fallSpeed)
	{
		characterController().setFallSpeed(fallSpeed);
	}

	public void setGravity(float gravity)
	{
		characterController().setGravity(gravity);
	}

	public void setJumpSpeed(float jumpSpeed)
	{
		characterController().setJumpSpeed(jumpSpeed);
	}

	@Override
	public void moveTo(Vector3 location)
	{
		super.moveTo(location);
		characterController().warp(location);
	}

	public void setMaxSlope(float maxSlope)
	{
		setMaxSlope(maxSlope * MyMath.DEGREES_TO_RADIANS);
	}

	public void setMaxSlopeRad(float maxSlope)
	{
		characterController().setMaxSlope(maxSlope);
	}

	public void setRotationSpeed(float rotationSpeed)
	{
		this.rotationSpeed = rotationSpeed;
	}

	public void setSpeed(float speed)
	{
		this.speed = speed;
	}

	public void setStepHeight(float stepHeight)
	{
		this.stepHeight = stepHeight;
		this.characterController = createController(stepHeight);
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
	}

	protected Keyboard keyboard()
	{
		return keyboard;
	}

	@Override
	protected void input(float dt)
	{
		walkDirection.setZero();
		if (keyboard.exists())
		{
			if (onGround())
			{
				if (leftKey.isDown())
				{
					rotate(rotationSpeed(), 0f, 0f);
				}
				if (rightKey.isDown())
				{
					rotate(-rotationSpeed(), 0f, 0f);
				}
			}
			if (forwardKey.isDown())
			{
				walkDirection.add(direction());
			}
			if (backwardKey.isDown())
			{
				walkDirection.sub(direction());
			}
			if (jumpKey.isDown())
			{
				jump();
			}
		}
		if (touchScreen.exists())
		{
			if (touchScreen.justTouched())
			{
				jump();
			}
		}

		if (accelerometer.exists())
		{
			if (accelerometer.tiltsForward() || accelerometer.tiltsBackward())
			{
				walkDirection.add(direction());
				walkDirection.scl(accelerometer.y());
			}
		}

		walkDirection.scl(speed());
		characterController().setWalkDirection(walkDirection);
		bodyToTransform();
	}

	private btKinematicCharacterController createController(float stepHeight)
	{
		return new btKinematicCharacterController(body(),
				shape().shape(),
				stepHeight);
	}
}
