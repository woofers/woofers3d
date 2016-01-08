package com.jaxson.lib.gdx.graphics.cameras;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.jaxson.lib.gdx.bullet.PhysicsWorld;
import com.jaxson.lib.gdx.graphics.g3d.Entity;
import com.jaxson.lib.gdx.input.InputHandler;

public class TargetCamera extends PerspectiveCamera
{
	private static final int FOV = 75;
	private static final float FAR = 300f;
	private static final float NEAR = 1f / 10f;
	private static final Vector3 OFFSET = new Vector3(0f, 5f, -5f);
	private static final Vector3 STAGE_LOCATION = Vector3.Zero;

	private Entity target;
	private Vector3 offset;
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
		this.near = NEAR;
		this.far = FAR;
		center(STAGE_LOCATION);
	}

	public void center()
	{
		if (hasTarget()) center(getTargetLocation());
	}

	public void center(Vector3 point)
	{
		Vector3 location = getTargetLocation();
		position.set(offset.cpy().add(point));
		lookAt(point);
		oldTargetLocation = location;
	}

	public Vector3 getDeltaLocation(Vector3 location)
	{
		return location.cpy().sub(getLocation());
	}

	public Vector3 getDeltaTargetLocation()
	{
		return getTargetLocation().cpy().sub(oldTargetLocation);
	}

	public Vector3 getLocation()
	{
		return position;
	}

	public Vector2 getMouse()
	{
		return InputHandler.getScaledMouse();
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
		return view.getRotation(new Quaternion());
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
		up.set(Vector3.Y);
		position.add(getDeltaTargetLocation());
	}

	public void rotate(float yaw, float pitch, float roll)
	{
		rotate(Vector3.Y, yaw);
		rotate(Vector3.X, pitch);
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
		rotateAround(location, yaw, pitch, roll, false);
	}

	public void rotateAround(Vector3 location, float yaw, float pitch, float roll, boolean keepInBounds)
	{
		rotateAround(location, Vector3.Y, yaw);
		rotateAround(location, Vector3.X, pitch);
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

	public void setOffset(Vector3 offset)
	{
		this.offset = offset;
	}

	public void setRotation(float yaw, float pitch, float roll)
	{
		view.setFromEulerAngles(yaw, pitch, roll);
	}

	public void setRotation(Vector3 angles)
	{
		setRotation(angles.x, angles.y, angles.z);
	}

	public void setTarget(Entity target)
	{
		this.target = target;
		center();
	}

	public void setWorld(PhysicsWorld world)
	{
		this.world = world;
	}

	@Override
	public void update()
	{
		input();
		oldTargetLocation = getTargetLocation();
		super.update();
	}
}
