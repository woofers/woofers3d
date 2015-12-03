package com.jaxson.lib.gdx.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.entities.Entity;
import com.jaxson.lib.gdx.util.MyInputProcessor;

public class MyPerspectiveCamera extends PerspectiveCamera
{
	private static final int FOV                = 75;
	private static final float FAR              = 300f;
	private static final float NEAR             = 1f / 10f;
	private static final float MOUSE_SCALE      = 1f / 10f;
	private static final float SENSITIVITY      = 1.05f;
	private static final Vector3 OFFSET         = new Vector3(0f, 5f, -5f);
	private static final Vector3 STAGE_LOCATION = Vector3.Zero;

	private Entity target;
	private Vector3 offset;

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
	}

	public Vector3 getOffset()
	{
		return offset;
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
		Vector2 mouse = MyInputProcessor.getDeltaMouse();
		float scale = MOUSE_SCALE * SENSITIVITY;
		mouse.scl(scale, scale);
		System.out.println(getTarget().getCenterLocation());
		//rotateAround(getTarget().getCenterLocation(), Vector3.Y, 0.5f);
	}

	public void rotate(float pitch, float yaw, float roll)
	{
		Quaternion quaternion = new Quaternion();
		Quaternion tempQuaterion = new Quaternion();
		tempQuaterion.setEulerAngles(-pitch, -yaw, roll);
		rotate(tempQuaterion);
		//lookAt(getTargetLocation());
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
		if (hasTarget())
		{
			Vector3 newLocation = getTargetLocation();
			newLocation.add(offset);
			position.set(newLocation);
			input();
		}
		super.update();
	}
}