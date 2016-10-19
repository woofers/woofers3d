package com.jaxson.lib.gdx.graphics.g2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.util.GameObject;

public abstract class Sprite extends GameObject
{
	private float alpha = 1f;
	private float depth = 1f;

	public float alpha()
	{
		return alpha;
	}

	public Vector2 center()
	{
		return location().add(location().scl(0.5f));
	}

	public float depth()
	{
		return depth;
	}

	public abstract float height();

	public Vector2 location()
	{
		return new Vector2(x(), y());
	}

	public Vector2 origin()
	{
		return new Vector2(originX(), originY());
	}

	public abstract float originX();

	public abstract float originY();

	public abstract float rotation();

	public Vector2 scale()
	{
		return new Vector2(scaleX(), scaleY());
	}

	public abstract float scaleX();

	public abstract float scaleY();

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
		setScale(scale, scaleY());
	}

	public void setScaleY(float scale)
	{
		setScale(scaleX(), scale);
	}

	public abstract void setSize(float width, float height);

	public void setSize(Vector2 size)
	{
		setSize(size.x, size.y);
	}

	public void setX(float x)
	{
		setLocation(x, y(), depth());
	}

	public void setY(float y)
	{
		setLocation(x(), y, depth());
	}

	public Vector2 size()
	{
		return new Vector2(width(), height());
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

	public abstract float width();

	public abstract float x();

	public abstract float y();
}
