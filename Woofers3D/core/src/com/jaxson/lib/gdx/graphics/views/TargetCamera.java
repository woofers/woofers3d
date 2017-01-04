package com.jaxson.lib.gdx.graphics.views;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.jaxson.lib.gdx.bullet.simulation.PhysicsWorld;
import com.jaxson.lib.gdx.bullet.simulation.bodies.types.EntityBody;
import com.jaxson.lib.gdx.graphics.g3d.entities.types.Entity;
import com.jaxson.lib.gdx.input.Inputs;
import com.jaxson.lib.gdx.input.Keyboard;
import com.jaxson.lib.gdx.input.Mouse;
import com.jaxson.lib.gdx.input.KeyboardKey;
import com.jaxson.lib.util.Optional;
import com.jaxson.lib.util.exceptions.NegativeValueException;

public class TargetCamera extends PerspectiveCamera
{
	private static final float FOV = 95f;
	private static final float FAR = 300f;
	private static final float NEAR = 1f / 10f;
	private static final Vector3 OFFSET = new Vector3(0f, 0.55f, -0.75f);
	private static final Vector3 STAGE_LOCATION = Vector3.Zero;

	private static final float FOV_MIN = 0f;
	private static final float FOV_MAX = 180f;
	private static final float ZOOM_SCALE = 2.4f;

	private Entity target;
	private Vector3 offset;
	private Vector3 oldTargetLocation;
	private PhysicsWorld world;
	private float minFov = 70f;
	private float maxFov = 130f;

	private Keyboard keyboard;
	private Mouse mouse;
	private KeyboardKey centerKey;

	public TargetCamera(float width, float height)
	{
		this(FOV, width, height);
	}

	public TargetCamera(float fov, float width, float height)
	{
		this(fov, width, height, OFFSET);
	}

	public TargetCamera(float fov, float width, float height, Vector3 offset)
	{
		super(fov, width, height);
		this.offset = offset;
		setNear(NEAR);
		setFar(FAR);
		center();

		this.keyboard = Inputs.keyboard();
		this.mouse = Inputs.mouse();
		this.centerKey = keyboard.key("R");
	}

	public float maxFov()
	{
		return maxFov;
	}

	public float minFov()
	{
		return minFov;
	}

	public void setMaxFov(float maxFov)
	{
		this.maxFov = maxFov;
		calculateZoom();
	}

	public void setMinFov(float minFov)
	{
		this.minFov = minFov;
		calculateZoom();
	}

	private void calculateZoom()
	{
		float newFov = FOV + (mouse.scrollWheel() * ZOOM_SCALE);
		if (newFov < minFov()) newFov = minFov();
		if (newFov > maxFov()) newFov = maxFov();
		setFov(newFov);
	}


	public void center()
	{
		center(targetLocation());
	}

	public void center(Vector3 point)
	{
		if (point == null) point = STAGE_LOCATION;
		Vector3 newOffset = offset.cpy();
		if (hasTarget()) newOffset.rotate(Vector3.Y, targetRotation().x);
		setLocation(point);
		translate(newOffset);
		lookAt(point);
		oldTargetLocation = point;
	}

	public Vector3 deltaLocation(Vector3 location)
	{
		return location.cpy().sub(location());
	}

	public Vector3 deltaTargetLocation()
	{
		return targetLocation().cpy().sub(oldTargetLocation);
	}

	public Vector3 direction()
	{
		return direction;
	}

	public float far()
	{
		return far;
	}

	public float fov()
	{
		return fieldOfView;
	}

	public boolean hasTarget()
	{
		return target() != null;
	}

	public boolean hasWorld()
	{
		return world != null;
	}

	private void input()
	{
		if (!hasTarget()) return;
		rotateAround(targetLocation(), mosueLocation());
		translate(deltaTargetLocation());
		resetUp();
		if (centerKey.isPressed() || mouse.button("Middle").isDown())
		{
			center(targetLocation());
		}
		calculateZoom();
		System.out.println(fov());
	}

	public Vector3 location()
	{
		return position;
	}

	private Vector2 mosueLocation()
	{
		return mouse.scaledLocation();
	}

	public float near()
	{
		return near;
	}

	public Vector3 offset()
	{
		return offset;
	}

	public Ray ray()
	{
		if (!hasTarget()) return null;
		return new Ray(location(),
				deltaLocation(target().location()));
	}

	public void resetDirection()
	{
		setDirection(Vector3.Z);
	}

	public void resetUp()
	{
		setUp(Vector3.Y);
	}

	public Quaternion roationQuaternion()
	{
		return view().getRotation(new Quaternion());
	}

	public void rotate(float yaw, float pitch, float roll)
	{
		rotate(Vector3.X, pitch);
		rotate(Vector3.Y, yaw);
		rotate(Vector3.Z, roll);
	}

	public void rotate(Vector2 angles)
	{
		rotate(angles.x, angles.y, 0);
	}

	public void rotate(Vector3 angles)
	{
		rotate(angles.x, angles.y, angles.z);
	}

	public void rotateAround(Vector3 location,
			float yaw,
			float pitch,
			float roll)
	{
		rotateAround(location, yaw, pitch, roll, true);
	}

	public void rotateAround(Vector3 location,
			float yaw,
			float pitch,
			float roll,
			boolean keepInBounds)
	{
		rotateAround(location, Vector3.X, pitch);
		rotateAround(location, Vector3.Y, yaw);
		rotateAround(location, Vector3.Z, roll);
		if (!keepInBounds) return;
		if (hasWorld() && hasTarget())
		{
			Optional<EntityBody> trace = world.rayTrace(ray());
			if (trace.exists() && world.rayTrace(ray()).unwrap() != target())
			{
				rotateAround(location, -yaw, -pitch, -roll, !keepInBounds);
			}
		}
	}

	public void rotateAround(Vector3 location, Vector2 angles)
	{
		rotateAround(location, angles.x, angles.y, 0);
	}

	public void rotateAround(Vector3 location, Vector3 angles)
	{
		rotateAround(location, angles.x, angles.y, angles.z);
	}

	public Vector3 rotation()
	{
		Quaternion rotation = roationQuaternion();
		return new Vector3(rotation.getYaw(),
				rotation.getPitch(),
				rotation.getRoll());
	}

	public void setDirection(Vector3 direction)
	{
		direction().set(direction);
	}

	public void setFar(float far)
	{
		if (far <= 0f) throw new NegativeValueException("far");
		this.far = far;
	}

	public void setFov(float fov)
	{
		if (fov > FOV_MAX) fov = FOV_MAX;
		if (fov < FOV_MIN) fov = FOV_MIN;
		this.fieldOfView = fov;
	}

	public void setLocation(Vector3 location)
	{
		location().set(location);
	}

	public void setNear(float near)
	{
		if (near <= 0f) throw new NegativeValueException("near");
		this.near = near;
	}

	public void setOffset(Vector3 offset)
	{
		this.offset = offset;
	}

	public void setRotation(float yaw, float pitch, float roll)
	{
		resetUp();
		resetDirection();
		rotate(yaw, pitch, roll);
	}

	public void setRotation(Vector3 angles)
	{
		setRotation(angles.x, angles.y, angles.z);
	}

	public void setTarget(Entity target)
	{
		this.target = target;
		if (hasTarget()) center();
	}

	public void setUp(Vector3 location)
	{
		up().set(location);
	}

	public void setWorld(PhysicsWorld world)
	{
		this.world = world;
	}

	public Entity target()
	{
		return target;
	}

	public Vector3 targetLocation()
	{
		if (!hasTarget()) return null;
		return target().location();
	}

	public Vector3 targetRotation()
	{
		if (!hasTarget()) return null;
		return target().rotation();
	}

	public Vector3 up()
	{
		return up;
	}

	@Override
	public void update()
	{
		input();
		oldTargetLocation = targetLocation();
		super.update();
	}

	public Matrix4 view()
	{
		return view;
	}
}