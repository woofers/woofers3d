package com.jaxson.lib.gdx.graphics.g2d;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.jaxson.lib.gdx.graphics.views.View;

public class Text extends Sprite
{
	private static final String TEXT = "New Text";

	private BitmapFont font;
	private String text;
	private Vector2 location;
	private Vector2 size;
	private Vector2 scale;

	public Text(BitmapFont font)
	{
		this(TEXT, font);
	}

	public Text(String text)
	{
		this(text, new BitmapFont());
	}

	public Text(String text, BitmapFont font)
	{
		this.text = text;
		this.font = font;
		this.location = new Vector2();
		this.size = new Vector2(1f, 1f);
		this.scale = new Vector2(1f, 1f);
	}

	@Override
	public void dispose()
	{
		font.dispose();
	}

	public String getText()
	{
		return text;
	}

	@Override
	public float height()
	{
		return size.y;
	}

	@Override
	public Vector2 location()
	{
		return location;
	}

	@Override
	public float originX()
	{
		return size.x;
	}

	@Override
	public float originY()
	{
		return 0;
	}

	@Override
	public void render(View view)
	{
		font.draw(view.spriteBatch(), getText(), x(), y());
	}

	@Override
	public float rotation()
	{
		return 0;
	}

	@Override
	public float scaleX()
	{
		return scale.x;
	}

	@Override
	public float scaleY()
	{
		return scale.y;
	}

	@Override
	public void setCenter(float x, float y)
	{

	}

	@Override
	public void setLocation(float x, float y)
	{
		setLocation(new Vector2(x, y));
	}

	@Override
	public void setLocation(Vector2 location)
	{
		this.location = location;
	}

	@Override
	public void setOrigin()
	{

	}

	@Override
	public void setOrigin(float originX, float originY)
	{

	}

	@Override
	public void setRotation(float roll)
	{

	}

	@Override
	public void setScale(float scaleX, float scaleY)
	{
		scale.set(scaleX, scaleY);
	}

	@Override
	public void setSize(float width, float height)
	{
		size.set(width, height);
	}

	public void setText(String text)
	{
		this.text = text;
	}

	@Override
	public void translateX(float x)
	{
		location.x += x;
	}

	@Override
	public void translateY(float y)
	{
		location.y += y;
	}

	@Override
	public float width()
	{
		return size.x;
	}

	@Override
	public float x()
	{
		return location().x;
	}

	@Override
	public float y()
	{
		return location().y;
	}
}
