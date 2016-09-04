package com.jaxson.lib.gdx.graphics.g2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.util.GameObject;

public abstract class Sprite extends GameObject
{
	private float alpha = 1f;
	private float depth = 1f;

	public float getAlpha()
	{
		return alpha;
	}

	public Vector2 getCenter()
	{
		return getLocation().add(getLocation().cpy().scl(0.5f));
	}

	public float getDepth()
	{
		return depth;
	}

	public abstract float getHeight();

	public Vector2 getLocation()
	{
		return new Vector2(getLocationX(), getLocationY());
	}

	public abstract float getLocationX();

	public abstract float getLocationY();

	public Vector2 getOrigin()
	{
		return new Vector2(getOriginX(), getOriginY());
	}

	public abstract float getOriginX();

	public abstract float getOriginY();

	public abstract float getRotation();

	public Vector2 getScale()
	{
		return new Vector2(getScaleX(), getScaleY());
	}

	public abstract float getScaleX();

	public abstract float getScaleY();

	public Vector2 getSize()
	{
		return new Vector2(getWidth(), getHeight());
	}

	public abstract float getWidth();

	public void setAlpha(float alpha)
	{
		this.alpha = alpha;
	}

	public abstract void setCenter(float x, float y);

	public void setDepth(float depth)
	{
		this.depth = depth;
	}

	public void setFlip(boolean flipX, boolean flipY)
	{
		setFlip(flipX, flipY);
	}

	public abstract void setLocation(float x, float y);

	public void setLocation(float x, float y, float z)
	{
		setLocation(x, y);
		depth = z;
	}

	public void setLocation(Vector2 location)
	{
		setLocation(location.x, location.y);
	}

	public void setLocation(Vector3 location)
	{
		setLocation(location.x, location.y, location.z);
	}

	public abstract void setOrigin();

	public abstract void setOrigin(float originX, float originY);

	public abstract void setRotation(float roll);

	public void setScale(float scale)
	{
		setScale(scale, scale);
	}

	public abstract void setScale(float scaleX, float scaleY);

	public void setScale(Vector2 scale)
	{
		setScale(scale.x, scale.y);
	}

	public void setScaleX(float scale)
	{
		setScale(scale, getScaleY());
	}

	public void setScaleY(float scale)
	{
		setScale(getScaleX(), scale);
	}

	public abstract void setSize(float width, float height);

	public void setSize(Vector2 size)
	{
		setSize(size.x, size.y);
	}

	public void translate(float x, float y)
	{
		translateX(x);
		translateY(y);
	}

	public void translate(Vector2 translation)
	{
		translate(translation.x, translation.y);
	}

	public abstract void translateX(float x);

	public abstract void translateY(float y);
}
