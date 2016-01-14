package com.jaxson.lib.gdx.graphics.g2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.graphics.MyColor;
import com.jaxson.lib.gdx.util.GameObject;
import com.jaxson.lib.gdx.util.GdxFileReader;

public class GdxSprite extends GameObject
{
	private Sprite sprite;
	private float depth;

	public GdxSprite(String path)
	{
		this(new Texture(GdxFileReader.getInternalFile(path)));
	}

	public GdxSprite(Texture texture)
	{
		this.sprite = new Sprite(texture);
	}

	@Override
	public void dispose()
	{

	}

	public void draw(SpriteBatch spriteBatch)
	{
		sprite.draw(spriteBatch);
	}

	public void flip(boolean flipX, boolean flipY)
	{
		sprite.flip(flipX, flipY);
	}

	public void flipX()
	{
		flip(true, false);
	}

	public void flipY()
	{
		flip(false, true);
	}

	public Vector2 getCenter()
	{
		return getLocation().add(getOrigin());
	}

	public float getDepth()
	{
		return depth;
	}

	public float getHeight()
	{
		return sprite.getHeight();
	}

	public Vector2 getLocation()
	{
		return new Vector2(getLocationX(), getLocationY());
	}

	public float getLocationX()
	{
		return sprite.getX();
	}

	public float getLocationY()
	{
		return sprite.getY();
	}

	public Vector2 getOrigin()
	{
		return new Vector2(getOriginX(), getOriginY());
	}

	public float getOriginX()
	{
		return sprite.getOriginX();
	}

	public float getOriginY()
	{
		return sprite.getOriginY();
	}

	public float getRotation()
	{
		return sprite.getRotation();
	}

	public Vector2 getScale()
	{
		return new Vector2(getScaleX(), getScaleY());
	}

	public float getScaleX()
	{
		return sprite.getScaleX();
	}

	public float getScaleY()
	{
		return sprite.getScaleY();
	}

	public Vector2 getSize()
	{
		return new Vector2(getWidth(), getHeight());
	}

	public Color getTint()
	{
		return sprite.getColor();
	}

	public float getWidth()
	{
		return sprite.getWidth();
	}

	public void setAlpha(float alpha)
	{
		sprite.setAlpha(alpha);
	}

	public void setBounds(float x, float y, float width, float height)
	{
		sprite.setBounds(x, y, width, height);
	}

	public void setCenter(float x, float y)
	{
		sprite.setCenter(x, y);
	}

	public void setDepth(float depth)
	{
		this.depth = depth;
	}

	public void setFlip(boolean flipX, boolean flipY)
	{
		setFlip(flipX, flipY);
	}

	public void setLocation(float x, float y)
	{
		sprite.setPosition(x, y);
	}

	public void setLocation(Vector2 location)
	{
		setLocation(location.x, location.y);
	}

	public void setLocation(Vector3 location)
	{
		setLocation(location);
		depth = location.z;
	}

	public void setOrigin()
	{
		sprite.setOriginCenter();
	}

	public void setOrigin(float originX, float originY)
	{
		sprite.setOrigin(originX, originY);
	}

	public void setRotation(float roll)
	{
		sprite.setRotation(roll);
	}

	public void setScale(float scale)
	{
		sprite.setScale(scale);
	}

	public void setScale(float scaleX, float scaleY)
	{
		sprite.setScale(scaleX, scaleY);
	}

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

	public void setSize(float width, float height)
	{
		sprite.setSize(width, height);
	}

	public void setSize(Vector2 size)
	{
		setSize(size.x, size.y);
	}

	public void setTint(Color color)
	{
		sprite.setColor(color);
	}

	public void setTint(int r, int g, int b, int a)
	{
		setTint(new MyColor(r, g, b, a));
	}

	public void translate(float x, float y)
	{
		sprite.translate(x, y);
	}

	public void translate(Vector2 translation)
	{
		translate(translation.x, translation.y);
	}

	public void translateX(float x)
	{
		sprite.translateX(x);
	}

	public void translateY(float y)
	{
		sprite.translateY(y);
	}

	@Override
	public void update(float dt)
	{

	}
}
