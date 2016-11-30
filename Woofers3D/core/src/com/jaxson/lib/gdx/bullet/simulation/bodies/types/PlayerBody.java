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
import com.jaxson.lib.math.MyMath;
import com.jaxson.lib.math.Reciprocal;

public abstract class PlayerBody
		extends ShapeBody<btPairCachingGhostObject, ConvexShape>
{
	private static final float GRAVITY_SCALE = -1f;
	private static final float GHOST_MASS = -1f;
	private static final float ROUND_TOLERANCE_SCALE = 1.00000005f;

	private static final float ROUND_TOLERANCE = 0.00000001f;

	private static final float GRAVITY = -9.81f;
	private static final float STEP_HEIGHT = 0.035f;
	private static final float ROTATION_SPEED = 2f;
	private static final float MAX_FALL_VELOCITY = 55f;
	private static final float JUMP_VELOCITY = 2.4f;
	private static final float ACCELERATION_X = 0.0125f;
	private static final float DECCELERATION_X = ACCELERATION_X * 3f;
	private static final float MAX_VELOCITY_X = 1.04f;
	private static final float ACCELERATION_Z = 0.23f;
	private static final float DECCELERATION_Z = ACCELERATION_Z / 3f;
	private static final float MAX_VELOCITY_Z = 7f;

	private static final float Y_BALANCE = -0.5f;

	private btKinematicCharacterController characterController;
	private btGhostPairCallback callback;
	private Vector3 direction;
	private Vector3 velocityPerTick;
	private Vector3 velocity;
	private Vector3 maxVelocity;
	private float stepHeight;
	private Vector3 acceleration;
	private Vector3 decceleration;
	private float jumpTime;
	private boolean couldJump;
	private float jumpVelocity;

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
		this.velocityPerTick = new Vector3();
		this.velocity = new Vector3();
		this.maxVelocity = new Vector3();
		this.acceleration = new Vector3();
		this.decceleration = new Vector3();
		setJumpVelocity(JUMP_VELOCITY);
		setMaxFallVelocity(MAX_FALL_VELOCITY);
		setAcceleration(new Vector3(ACCELERATION_X, GRAVITY, ACCELERATION_Z));
		setDecceleration(
				new Vector3(DECCELERATION_X, GRAVITY, DECCELERATION_Z));
		setMaxVelocity(
				new Vector3(MAX_VELOCITY_X, MAX_FALL_VELOCITY, MAX_VELOCITY_Z));
		characterController().setUseGhostSweepTest(false);

		this.keyboard = Inputs.keyboard();
		this.accelerometer = new GameAccelerometer(Inputs.accelerometer());
		this.touchScreen = Inputs.touchScreen();
		this.forwardKey = keyboard.key("W");
		this.backwardKey = keyboard.key("S");
		this.leftKey = keyboard.key("A");
		this.rightKey = keyboard.key("D");
		this.jumpKey = keyboard.key("Space");

		for (float i = Accelerometer.MIN;
				i < Accelerometer.MAX + 0.1f; i += 0.1f)
			System.out.println(round(i) + ": " + round(yAccelerometer(i)));
	}

	protected void reset()
	{
		moveTo(Vector3.Zero);
		velocityPerTick().setZero();
		direction.setZero();
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

	public Vector3 decceleration()
	{
		return decceleration;
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
					if (MyMath.abs(velocityPerTick().x) < angle(maxSpeed().x) * dt)
					{
						if (angle(velocityPerTick().x) >= 0f)
						{
							velocityPerTick().x += angle(acceleration().x) * dt;
						}
						else
						{
							velocityPerTick().x += angle(decceleration().x) * dt;
						}
					}
				}
				else
				{
					if (velocityPerTick().x > 0f)
						velocityPerTick().x -= angle(decceleration().x) * dt;
				}
				if (rightKey.isDown())
				{
					if (MyMath.abs(velocityPerTick().x) < angle(maxSpeed().x) * dt)
					{
						if (angle(velocityPerTick().x) <= 0f)
						{
							velocityPerTick().x -= angle(acceleration().x) * dt;
						}
						else
						{
							velocityPerTick().x -= angle(decceleration().x) * dt;
						}
					}
				}
				else
				{
					if (velocityPerTick().x < 0f)
						velocityPerTick().x += angle(decceleration().x) * dt;
				}

				if (jumpKey.isPressed())
				{
					if (canJump()) jump();
				}
			}
			if (forwardKey.isDown())
			{
				if (MyMath.abs(velocityPerTick().z) < maxSpeed().z * dt)
				{
					if (velocityPerTick().z >= 0f)
					{
						velocityPerTick().z += acceleration().z * dt;
					}
					else
					{
						velocityPerTick().z += decceleration().z * dt;
					}
				}
			}
			else
			{
				if (velocityPerTick().z > 0f)
					velocityPerTick().z -= decceleration().z * dt;
			}
			if (backwardKey.isDown())
			{
				if (MyMath.abs(velocityPerTick().z) < maxSpeed().z * dt)
				{
					if (velocityPerTick().z <= 0f)
					{
						velocityPerTick().z -= acceleration().z * dt;
					}
					else
					{
						velocityPerTick().z -= decceleration().z * dt;
					}
				}
			}
			else
			{
				if (velocityPerTick().z < 0f)
					velocityPerTick().z += decceleration().z * dt;
			}
			if (leftKey.isDown() || rightKey.isDown())
			{
				if (MyMath.abs(velocityPerTick().z) > decceleration().z * dt
						* ROUND_TOLERANCE_SCALE)
				{
					velocityPerTick().z += -(MyMath.abs(velocityPerTick().z)
							/ velocityPerTick().z)
							* (dt * (acceleration().z + decceleration().z));
				}
			}
			if (MyMath.abs(velocityPerTick().z) < acceleration().z * dt
					* ROUND_TOLERANCE_SCALE)
				velocityPerTick().z = 0f;
		}
		if (MyMath.abs(angle(velocityPerTick().x)) <
				angle(acceleration().x) * dt * ROUND_TOLERANCE_SCALE)
		{
			velocityPerTick().x = 0f;
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

		}
		direction.add(direction());
		direction.scl(velocityPerTick().z);
		characterController().setWalkDirection(direction);
		bodyToTransform();
		if (MyMath.abs(angle(velocityPerTick().x)) > angle(acceleration().x)
				* angle(acceleration().x) * dt * ROUND_TOLERANCE_SCALE)
		{
			rotate(velocityPerTick().x, 0f, 0f);
		}
		else if (!leftKey.isDown() && !rightKey.isDown())
		{
			velocityPerTick().x = 0f;
		}
		velocity().set(velocityPerTick()).scl(new Reciprocal(dt).floatValue());
		velocity().x = turn(velocityPerTick().x / dt);
		couldJump = canJump();
		if (isJumping())
		{
			jumpTime += dt;
		}
		else
		{
			jumpTime = 0f;
		}
		velocity().y = JUMP_VELOCITY + acceleration().y * jumpTime;
		if (velocity().y >= JUMP_VELOCITY) velocity().y = 0f;
		System.out.println(velocity());
	}

	public boolean isJumping()
	{
		return !canJump() && !onGround();
	}

	private float angle(float length)
	{
		return length / radius() * MyMath.RADIANS_TO_DEGREES;
	}

	private float turn(float angle)
	{
		return angle * MyMath.DEGREES_TO_RADIANS * radius();
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

	public Vector3 maxSpeed()
	{
		return maxVelocity;
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

	public boolean justJumped()
	{
		return couldJump && !canJump();
	}

	protected void rotateLeft()
	{
		rotateLeft(1f);
	}

	protected void rotateLeft(float scale)
	{
		rotate(ROTATION_SPEED * scale, 0f, 0f);
	}

	protected void rotateRight()
	{
		rotateRight(1f);
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

	public void setGravity(float gravity)
	{
		acceleration().y = gravity;
		decceleration().y = gravity;
		characterController().setGravity(gravity() * GRAVITY_SCALE);
	}

	public void setJumpVelocity(float jumpVelocity)
	{
		this.jumpVelocity = jumpVelocity;
		characterController().setJumpSpeed(jumpVelocity);
	}


	public void setMaxFallVelocity(float maxFallVelocity)
	{
		characterController().setFallSpeed(maxFallVelocity);
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

	public Vector3 velocity()
	{
		return velocity;
	}

	protected Vector3 velocityPerTick()
	{
		return velocityPerTick;
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
