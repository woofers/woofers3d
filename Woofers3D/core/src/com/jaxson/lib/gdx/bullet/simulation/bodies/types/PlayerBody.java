package com.jaxson.lib.gdx.bullet.simulation.bodies.types;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btGhostPairCallback;
import com.badlogic.gdx.physics.bullet.collision.btPairCachingGhostObject;
import com.badlogic.gdx.physics.bullet.dynamics.btKinematicCharacterController;
import com.jaxson.lib.gdx.bullet.simulation.collision.types.ConvexShape;
import com.jaxson.lib.gdx.input.Accelerometer;
import com.jaxson.lib.gdx.input.GameAccelerometer;
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

	private static final float Y_BALANCE = -0.5f;

	private btKinematicCharacterController characterController;
	private btGhostPairCallback callback;
	private Vector3 walkDirection;
	private float speed;
	private float rotationSpeed;
	private float stepHeight;

	private Keyboard keyboard;
	private GameAccelerometer accelerometer;
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
		this.accelerometer = new GameAccelerometer(Inputs.accelerometer());
		this.touchScreen = Inputs.touchScreen();
		this.forwardKey = keyboard.key("W");
		this.backwardKey = keyboard.key("S");
		this.leftKey = keyboard.key("A");
		this.rightKey = keyboard.key("D");
		this.jumpKey = keyboard.key("Space");

		for (float i = Accelerometer.MIN; i < Accelerometer.MAX + 0.1f; i += 0.1f)
			System.out.println(round(i) + ": " + round(yAccelerometer(i)));
	}

	private static float round(float i)
	{
		return Math.round(i * 100.0f) / 100.0f;
	}

	public GameAccelerometer accelerometer()
	{
		return accelerometer;
	}

	public btGhostPairCallback callback()
	{
		return callback;
	}

	public boolean canJump()
	{
		return characterController().canJump();
	}

	public btKinematicCharacterController characterController()
	{
		return characterController;
	}

	private btKinematicCharacterController createController(float stepHeight)
	{
		return new btKinematicCharacterController(body(),
				shape().shape(),
				stepHeight);
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	public float gravity()
	{
		return characterController().getGravity();
	}

	@Override
	protected void input(float dt)
	{
		walkDirection.setZero();
		if (keyboard().exists())
		{
			if (onGround())
			{
				if (leftKey.isDown())
				{
					rotateLeft(1f);
				}
				if (rightKey.isDown())
				{
					rotateRight(1f);
				}
				if (jumpKey.isDown())
				{
					jump();
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
			walkDirection.add(direction());
			walkDirection.scl(yAccelerometer(5f) * 1.7f);
			if (onGround())
			{
				if (accelerometer.tiltsLeft())
				{
					rotateLeft(2f * (accelerometer.x()
							- accelerometer.deadZone().x));
				}
				else if (accelerometer.tiltsRight())
				{
					rotateRight(-2f * (accelerometer.x()
							- accelerometer.deadZone().x));
				}
			}
		}
		walkDirection.scl(speed());
		characterController().setWalkDirection(walkDirection);
		bodyToTransform();
	}

	public void jump()
	{
		characterController().jump();
	}

	protected Keyboard keyboard()
	{
		return keyboard;
	}

	public float maxSlope()
	{
		return maxSlopeRad() * MyMath.RADIANS_TO_DEGREES;
	}

	public float maxSlopeRad()
	{
		return characterController().getMaxSlope();
	}

	@Override
	public void moveTo(Vector3 location)
	{
		super.moveTo(location);
		characterController().warp(location);
	}

	public boolean onGround()
	{
		return characterController().onGround();
	}

	protected void rotateLeft(float scale)
	{
		rotate(rotationSpeed() * scale, 0f, 0f);
	}

	protected void rotateRight(float scale)
	{
		rotate(-rotationSpeed() * scale, 0f, 0f);
	}

	public float rotationSpeed()
	{
		return rotationSpeed;
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

	public float speed()
	{
		return speed;
	}

	public float stepHeight()
	{
		return stepHeight;
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
		accelerometer().update(dt);
	}

	protected float yAccelerometer(float test)
	{
		float y = test; //accelerometer.y();
		//if (y > 0f)
		//{
		//	if (y > Y_BALANCE + Accelerometer.MAX)
		//	{
		//		return 2f * Accelerometer.MIN + y - Y_BALANCE - 1f;
		//	}
		//}
		//return y - Y_BALANCE;
		return y;
	}
}
