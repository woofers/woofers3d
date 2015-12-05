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
	private static final float MOUSE_SCALE      = 1f / 10f;
	private static final float SENSITIVITY      = 1.05f;
	private static final boolean INVERT_MOUSE   = true;
	private static final Vector3 MAX_CLAMP      = new Vector3(0.7f, 0f, 0f);
	private static final Vector3 MIN_CLAMP      = new Vector3(-0.7f, -0.7f, 0f);
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
			position.set(offset.cpy().add(getTargetLocation()));
			lookAt(getTargetLocation());
			oldTargetLocation = getTargetLocation();
		}
	}

	public Vector3 getDeltaLocation()
	{
		return getTargetLocation().sub(oldTargetLocation);
	}

	public Vector3 getInverseOffset()
	{
		return offset.cpy().scl(-1, -1, -1);
	}

	public Vector3 getLocation()
	{
		return position;
	}

	private Vector2 getMouse()
	{
		final float scale = MOUSE_SCALE * SENSITIVITY;
		Vector2 mouse = MyInputProcessor.getDeltaMouse();
		mouse.scl(scale, -scale);
		if (INVERT_MOUSE) mouse.scl(-1f, -1f);
		return mouse;
	}

	public Vector3 getOffset()
	{
		return offset;
	}

	public Quaternion getRotation()
	{
		return view.getRotation(new Quaternion());
	}

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
		if (mouse.x > 0)
		{
			if (direction.x < MAX_CLAMP.x)
			{
				rotateAround(getTargetLocation(), Vector3.Y, mouse.x);
			}
		}
		else
		{
			if (direction.x > MIN_CLAMP.x)
			{
				rotateAround(getTargetLocation(), Vector3.Y, mouse.x);
			}
		}
		if (mouse.y > 0)
		{
			if (direction.y > MIN_CLAMP.y)
			{
				rotateAround(getTargetLocation(), Vector3.X, mouse.y);
			}
		}
		else
		{
			if (direction.y < MAX_CLAMP.y)
			{
				rotateAround(getTargetLocation(), Vector3.X, mouse.y);
			}
		}
		up.set(Vector3.Y);
		position.add(getDeltaLocation());
		oldTargetLocation = getTargetLocation();
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
}