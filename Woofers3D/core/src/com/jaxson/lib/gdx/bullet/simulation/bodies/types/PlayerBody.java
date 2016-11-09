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
	private static final float GRAVITY_SCALE = -5f;
	private static final float GHOST_MASS = -1f;

	private static final float GRAVITY = -7.84f;
	private static final float STEP_HEIGHT = 1f / 6f;
	private static final float ROTATION_SPEED = 2f;
	private static final float MAX_FALL_VELOCITY = 55f;
	private static final float JUMP_IMUPLSE = 10f;
	private static final float ACCELERATION_X = 5f;
	private static final float DECCELERATION_X = ACCELERATION_X * 2f;
	private static final float MAX_VELOCITY_X = 2.5f;
	private static final float ACCELERATION_Z = 1.8f;
	private static final float DECCELERATION_Z = ACCELERATION_Z / 3f;
	private static final float MAX_VELOCITY_Z = 0.45f;

	private static final float Y_BALANCE = -0.5f;

	private btKinematicCharacterController characterController;
	private btGhostPairCallback callback;
	private Vector3 direction;
	private Vector3 velocity;
	private Vector3 maxVelocity;
	private float stepHeight;
	private Vector3 acceleration;
	private Vector3 decceleration;

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
		this(model, fittedShape(model));
	}

	public PlayerBody(Model model, ConvexShape shape)
	{
		super(model, new btPairCachingGhostObject(), shape, GHOST_MASS);
		setStepHeight(STEP_HEIGHT);
		this.callback = new btGhostPairCallback();
		this.direction = new Vector3();
		this.velocity = new Vector3();
		this.maxVelocity = new Vector3();
		this.acceleration = new Vector3();
		this.decceleration = new Vector3();
		setJumpImpulse(JUMP_IMUPLSE);
		setMaxFallVelocity(MAX_FALL_VELOCITY);
		setAcceleration(new Vector3(ACCELERATION_X, GRAVITY, ACCELERATION_Z));
		setDecceleration(new Vector3(DECCELERATION_X, GRAVITY, DECCELERATION_Z));
		setMaxVelocity(new Vector3(MAX_VELOCITY_X, MAX_FALL_VELOCITY, MAX_VELOCITY_Z));

		this.keyboard = Inputs.keyboard();
		this.accelerometer = new GameAccelerometer(Inputs.accelerometer());
		this.touchScreen = Inputs.touchScreen();
		this.forwardKey = keyboard.key("W");
		this.backwardKey = keyboard.key("S");
		this.leftKey = keyboard.key("A");
		this.rightKey = keyboard.key("D");
		this.jumpKey = keyboard.key("Space");

		System.out.println(maxSpeed());

		for (float i = Accelerometer.MIN;
				i < Accelerometer.MAX + 0.1f; i += 0.1f)
			System.out.println(round(i) + ": " + round(yAccelerometer(i)));
	}

	public Vector3 acceleration()
	{
		return acceleration;
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
				shape().bulletShape(),
				stepHeight);
	}

	@Override
	public void dispose()
	{
		super.dispose();
		characterController.dispose();
		callback.dispose();
	}

	public float gravity()
	{
		return acceleration().y;
	}

	@Override
	protected void input(float dt)
	{
		direction.setZero();
		if (keyboard().exists())
		{
			if (onGround())
			{
				if (leftKey.isDown())
				{
					if (MyMath.abs(velocity().x) < maxSpeed().x)
					{
						if (velocity().x >= 0f)
						{
							velocity().x += acceleration().x * dt;
						}
						else
						{
							velocity().x += decceleration().x * dt;
						}
					}
				}
				else
				{
					if (velocity().x > 0f) velocity().x -= decceleration().x * dt;
				}
				if (rightKey.isDown())
				{
					if (MyMath.abs(velocity().x) < maxSpeed().x)
					{
						if (velocity().x <= 0f)
						{
							velocity().x -= acceleration().x * dt;
						}
						else
						{
							velocity().x -= decceleration().x * dt;
						}
					}
				}
				else
				{
					if (velocity().x < 0f) velocity().x += decceleration().x * dt;
				}
				if (MyMath.abs(velocity().x) < acceleration().x * dt) velocity().x = 0f;

				if (jumpKey.isPressed())
				{
					jump();
				}
			}
			if (forwardKey.isDown())
			{
				if (MyMath.abs(velocity().z) < maxSpeed().z)
				{
					if (velocity().z >= 0f)
					{
						velocity().z += acceleration().z * dt;
					}
					else
					{
						velocity().z += decceleration().z * dt;
					}
				}
			}
			else
			{
				if (velocity().z > 0f) velocity().z -= decceleration().z * dt;
			}
			if (backwardKey.isDown())
			{
				if (MyMath.abs(velocity().z) < maxSpeed().z)
				{
					if (velocity().z <= 0f)
					{
						velocity().z -= acceleration().z * dt;
					}
					else
					{
						velocity().z -= decceleration().z * dt;
					}
				}
			}
			else
			{
				if (velocity().z < 0f) velocity().z += decceleration().z * dt;
			}
			if (leftKey.isDown() || rightKey.isDown())
			{
				if (MyMath.abs(velocity().z) > decceleration().z * dt)
				{
					velocity().z += -(MyMath.abs(velocity().z) / velocity().z)
							* (acceleration().z + decceleration().z) * dt;
				}
			}
			if (MyMath.abs(velocity().z) < acceleration().z * dt) velocity().z = 0f;
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
			direction.add(direction());
			direction.scl(yAccelerometer(5f) * 1.7f);
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
		if (velocity().x != 0) rotate(velocity().x, 0f, 0f);
		direction.add(direction());
		direction.scl(velocity().z);
		System.out.println(velocity().x);
		characterController().setWalkDirection(direction);
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

	protected void rotateLeft()
	{
		rotateLeft(1f);
	}

	protected void rotateRight()
	{
		rotateRight(1f);
	}

	protected void rotateLeft(float scale)
	{
		rotate(ROTATION_SPEED * scale, 0f, 0f);
	}

	protected void rotateRight(float scale)
	{
		rotate(-ROTATION_SPEED * scale, 0f, 0f);
	}

	public void setAcceleration(float acceleration)
	{
		setAcceleration(new Vector3(acceleration, gravity(), acceleration));
	}

	public void setAcceleration(Vector3 acceleration)
	{
		this.acceleration = acceleration;
		setGravity(acceleration().y);
	}

	public void setDecceleration(float decceleration)
	{
		setDecceleration(new Vector3(decceleration, gravity(), decceleration));
	}

	public void setDecceleration(Vector3 decceleration)
	{
		this.decceleration = decceleration;
		setGravity(decceleration().y);
	}

	public Vector3 decceleration()
	{
		return decceleration;
	}

	public void setMaxFallVelocity(float maxFallVelocity)
	{
		characterController().setFallSpeed(maxFallVelocity);
	}

	public void setGravity(float gravity)
	{
		acceleration().y = gravity;
		decceleration().y = gravity;
		characterController().setGravity(gravity() * GRAVITY_SCALE);
	}

	public void setJumpImpulse(float jumpImpulse)
	{
		characterController().setJumpSpeed(jumpImpulse);
	}

	public void setMaxSlope(float maxSlope)
	{
		setMaxSlope(maxSlope * MyMath.DEGREES_TO_RADIANS);
	}

	public void setMaxSlopeRad(float maxSlope)
	{
		characterController().setMaxSlope(maxSlope);
	}

	public void setMaxVelocity(float maxVelocity)
	{
		setMaxVelocity(new Vector3(maxVelocity, maxVelocity, maxVelocity));
	}

	public void setMaxVelocity(Vector3 maxVelocity)
	{
		this.maxVelocity = maxVelocity;
	}

	public void setStepHeight(float stepHeight)
	{
		this.stepHeight = stepHeight;
		this.characterController = createController(stepHeight);
	}

	public Vector3 maxSpeed()
	{
		return maxVelocity;
	}

	public Vector3 velocity()
	{
		return velocity;
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
		float y = test; // accelerometer.y();
		// if (y > 0f)
		// {
		// if (y > Y_BALANCE + Accelerometer.MAX)
		// {
		// return 2f * Accelerometer.MIN + y - Y_BALANCE - 1f;
		// }
		// }
		// return y - Y_BALANCE;
		return y;
	}

	private static float round(float i)
	{
		return Math.round(i * 100.0f) / 100.0f;
	}
}
