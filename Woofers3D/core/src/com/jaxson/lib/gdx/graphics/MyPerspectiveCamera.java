package com.jaxson.lib.gdx.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.entities.Entity;
import com.jaxson.lib.gdx.util.MyInputProcessor;

public class MyPerspectiveCamera extends PerspectiveCamera
{
	private static final int FOV                = 75;
	private static final float NEAR             = 0.1f;
	private static final float FAR              = 300f;
	private static final Vector3 OFFSET         = new Vector3(0f, 5f, -5f);
	private static final Vector3 LOCATION       = new Vector3(0f, 0f, -3f);
	private static final Vector3 STAGE_LOCATION = Vector3.Zero;

	private Entity target;
	private Vector3 offset;

	public MyPerspectiveCamera(float width, float height)
	{
		this(width, height, LOCATION);
	}

	public MyPerspectiveCamera(float width, float height, Entity target)
	{
		this(FOV, width, height, LOCATION, target);
	}

	public MyPerspectiveCamera(float width, float height, Vector3 location)
	{
		this(FOV, width, height, location);
	}

	public MyPerspectiveCamera(float fov, float width, float height, Vector3 location)
	{
		this(fov, width, height, location, null);
	}

	public MyPerspectiveCamera(float fov, float width, float height, Vector3 location, Entity target)
	{
		super(fov, width, height);
		this.target = target;
		this.near = NEAR;
		this.far = FAR;
		position.set(location);
		lookAt(STAGE_LOCATION);
	}

	public Entity getTarget()
	{
		return target;
	}

	public Vector3 getTargetLocation()
	{
		float[] matrix = target.transform.getValues();
		return new Vector3(matrix[12], matrix[13], matrix[14]);
	}

	public boolean hasTarget()
	{
		return target != null;
	}

	private void input()
	{
		//rotateAround(getTargetLocation(), new Vector3(0, 1, 0), 1f);
	}

	public void removeTarget()
	{
		setTarget(null);
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
			newLocation.add(OFFSET);
			position.set(newLocation);
			input();
		}
		super.update();
	}
}