package com.jaxson.lib.gdx.graphics.views;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.jaxson.lib.gdx.bullet.simulation.PhysicsWorld;
import com.jaxson.lib.gdx.graphics.g3d.entities.types.Entity;
import com.jaxson.lib.gdx.input.Inputs;
import com.jaxson.lib.util.exceptions.NegativeValueException;

public class TargetCamera extends PerspectiveCamera
{
	private static final int FOV = 95;
	private static final float FAR = 300f;
	private static final float NEAR = 1f / 10f;
	private static final Vector3 OFFSET = new Vector3(0f, 5f, -5f);
	private static final Vector3 STAGE_LOCATION = Vector3.Zero;

	private Entity target;
	private Vector3 offset;
	private Vector3 zoom;
	private Vector3 oldTargetLocation;
	private PhysicsWorld world;

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
		this.zoom = new Vector3(1f, 1f, 1f);
		setNear(NEAR);
		setFar(FAR);
		center();
	}

	public void center()
	{
		center(getTargetLocation());
	}

	public void center(Vector3 point)
	{
		if (point == null) point = STAGE_LOCATION;
		Vector3 newOffset = offset.cpy();
		if (hasTarget()) newOffset.rotate(Vector3.Y, getTargetRotation().x);
		setLocation(point);
		translate(newOffset);
		lookAt(point);
		oldTargetLocation = point;
	}

	public Vector3 getDeltaLocation(Vector3 location)
	{
		return location.cpy().sub(getLocation());
	}

	public Vector3 getDeltaTargetLocation()
	{
		return getTargetLocation().cpy().sub(oldTargetLocation);
	}

	public Vector3 getDirection()
	{
		return direction;
	}

	public float getFar()
	{
		return far;
	}

	public float getFov()
	{
		return fieldOfView;
	}

	public Vector3 getLocation()
	{
		return position;
	}

	public Vector2 getMouse()
	{
		return Inputs.getScaledMouse();
	}

	public float getNear()
	{
		return near;
	}

	public Vector3 getOffset()
	{
		return offset;
	}

	public Ray getRay()
	{
		if (!hasTarget()) return null;
		return new Ray(getLocation(), getDeltaLocation(getTarget().getLocation()));
	}

	public Quaternion getRoationQuat()
	{
		return getView().getRotation(new Quaternion());
	}

	public Vector3 getRotation()
	{
		Quaternion rotation = getRoationQuat();
		return new Vector3(rotation.getYaw(), rotation.getPitch(), rotation.getRoll());
	}

	public Entity getTarget()
	{
		return target;
	}

	public Vector3 getTargetLocation()
	{
		if (!hasTarget()) return null;
		return getTarget().getLocation();
	}

	public Vector3 getTargetRotation()
	{
		if (!hasTarget()) return null;
		return getTarget().getRotation();
	}

	public Vector3 getUp()
	{
		return up;
	}

	public Matrix4 getView()
	{
		return view;
	}

	public Vector3 getZoom()
	{
		return zoom;
	}

	public boolean hasTarget()
	{
		return getTarget() != null;
	}

	public boolean hasWorld()
	{
		return world != null;
	}

	private void input()
	{
		if (!hasTarget()) return;
		rotateAround(getTargetLocation(), getMouse());
		translate(getDeltaTargetLocation());
		resetUp();
		if (Inputs.isPressed(Keys.R))
		{
			center(getTargetLocation());
		}
	}

	public void resetDirection()
	{
		setDirection(Vector3.Z);
	}

	public void resetUp()
	{
		setUp(Vector3.Y);
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

	public void rotateAround(Vector3 location, float yaw, float pitch, float roll)
	{
		rotateAround(location, yaw, pitch, roll, true);
	}

	public void rotateAround(Vector3 location, float yaw, float pitch, float roll, boolean keepInBounds)
	{
		rotateAround(location, Vector3.X, pitch);
		rotateAround(location, Vector3.Y, yaw);
		rotateAround(location, Vector3.Z, roll);
		if (!keepInBounds) return;
		if (hasWorld() && hasTarget())
		{
			if (world.getBody(getRay()) != getTarget())
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

	public void setDirection(Vector3 direction)
	{
		getDirection().set(direction);
	}

	public void setFar(float far)
	{
		if (far <= 0f) throw new NegativeValueException("far");
		this.far = far;
	}

	public void setFov(float fov)
	{
		this.fieldOfView = fov;
	}

	public void setLocation(Vector3 location)
	{
		getLocation().set(location);
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
		getUp().set(location);
	}

	public void setWorld(PhysicsWorld world)
	{
		this.world = world;
	}

	public void setZoom(Vector3 zoom)
	{
		this.zoom = zoom;
	}

	@Override
	public void update()
	{
		input();
		oldTargetLocation = getTargetLocation();
		super.update();
	}
}
