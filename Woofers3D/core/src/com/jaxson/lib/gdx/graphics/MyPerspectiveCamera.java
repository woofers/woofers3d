package com.jaxson.lib.gdx.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.entities.Entity;
import com.jaxson.lib.gdx.util.MyInputProcessor;
import java.lang.Math;

public class MyPerspectiveCamera extends PerspectiveCamera
{
	private static final int FOV                = 75;
	private static final float FAR              = 300f;
	private static final float NEAR             = 1f / 10f;
	private static final Vector2 MAX_CLAMP      = new Vector2(100f, 0f);
	private static final Vector2 MIN_CLAMP      = new Vector2(-100f, -55f);
	private static final Vector3 OFFSET         = new Vector3(0f, 5f, -5f);
	private static final Vector3 STAGE_LOCATION = Vector3.Zero;

	private Entity target;
	private Vector3 offset;
	private Vector3 oldTargetLocation;

	public MyPerspectiveCamera(float width, float height)
	{
		this(width, height, null);
	}

	public MyPerspectiveCamera(float width, float height, Entity target)
	{
		this(FOV, width, height, OFFSET, target);
	}
	public MyPerspectiveCamera(float fov, float width, float height, Vector3 offset)
	{
		this(fov, width, height, offset, null);
	}

	public MyPerspectiveCamera(float fov, float width, float height, Vector3 offset, Entity target)
	{
		super(fov, width, height);
		this.target = target;
		this.offset = offset;
		this.near = NEAR;
		this.far = FAR;

		position.set(offset);
		lookAt(STAGE_LOCATION);
		oldTargetLocation = STAGE_LOCATION;

		if (hasTarget())
		{
			center();
			oldTargetLocation = getTargetLocation();
		}
	}

	public void center()
	{
		Vector3 location = getTargetLocation();
		position.set(offset.cpy().add(location));
		lookAt(location);
	}

	public Vector3 getDeltaLocation()
	{
		return getTargetLocation().sub(oldTargetLocation);
	}

	public Vector3 getInverseOffset()
	{
		return offset.cpy().scl(-1, -1, -1);
	}

	public Vector2 getMouse()
	{
		return MyInputProcessor.getScaledMouse();
	}

	public Vector3 getLocation()
	{
		return position;
	}

	public Vector3 getOffset()
	{
		return offset;
	}

	public Vector3 getRotation()
	{
		Quaternion quaternion = view.getRotation(new Quaternion());
		return new Vector3(quaternion.getYaw(), quaternion.getPitch(), quaternion.getRoll());
	}
		//if (mouse.x > 0)
		//{
		//	if (rotation.x > MIN_CLAMP.x)
		//	{
		//		rotateAround(getTargetLocation(), Vector3.Y, mouse.x);
		//	}
		//}
		//else
		//{
		//	if (rotation.x < MAX_CLAMP.x)
		//	{
		//		rotateAround(getTargetLocation(), Vector3.Y, mouse.x);
		//	}
		//}
	public Entity getTarget()
	{
		return target;
	}

	public Vector3 getTargetLocation()
	{
		if (!hasTarget()) return null;
		return target.getLocation();
	}

	public boolean hasTarget()
	{
		return target != null;
	}

	private void input()
	{
		if (!hasTarget()) return;
		Vector2 mouse = getMouse();
		Vector3 rotation = getRotation();
		Vector3 targetLocation = getTargetLocation();

		rotateAround(targetLocation, Vector3.Y, mouse.x);
		if (mouse.y > 0)
		{
			if (rotation.y > MIN_CLAMP.y)
			{
				rotateAround(targetLocation, Vector3.X, mouse.y);
			}
		}
		else
		{
			if (rotation.y < MAX_CLAMP.y)
			{
				rotateAround(targetLocation, Vector3.X, mouse.y);
			}
		}
		up.set(Vector3.Y);
		position.add(getDeltaLocation());
		oldTargetLocation = targetLocation;
	}

	public void removeTarget()
	{
		setTarget(null);
	}

	public void setOffset(Vector3 offset)
	{
		this.offset = offset;
	}

	public void setTarget(Entity target)
	{
		this.target = target;
	}

	@Override
	public void update()
	{
		input();
		super.update();
	}

	/*
	if (mouse.x > 0)
	{
		if (rotation.x > MIN_CLAMP.x)
		{
			rotateAround(getTargetLocation(), Vector3.Y, mouse.x);
		}
	}
	else
	{
		if (rotation.x < MAX_CLAMP.x)
		{
			rotateAround(getTargetLocation(), Vector3.Y, mouse.x);
		}
	}
	*/
}