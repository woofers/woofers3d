package com.jaxson.lib.gdx.graphics.g2d;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.jaxson.lib.gdx.graphics.color.MyColor;
import com.jaxson.lib.gdx.graphics.views.View;
import com.jaxson.lib.gdx.io.GdxFile;

public class SpriteActor extends Sprite
{
	private com.badlogic.gdx.graphics.g2d.Sprite sprite;

	public SpriteActor(String path)
	{
		this(new Texture(
				new GdxFile(path, FileType.Internal).getFileHandle()));
	}

	public SpriteActor(Texture texture)
	{
		this.sprite = new com.badlogic.gdx.graphics.g2d.Sprite(texture);
	}

	@Override
	public void dispose()
	{

	}

	public void flip()
	{
		flip(true, true);
	}

	public void flipX()
	{
		flip(true, false);
	}

	public void flipY()
	{
		flip(false, true);
	}

	@Override
	public float height()
	{
		return sprite.getHeight();
	}

	@Override
	public float originX()
	{
		return sprite.getOriginX();
	}

	@Override
	public float originY()
	{
		return sprite.getOriginY();
	}

	@Override
	public float rotation()
	{
		return sprite.getRotation();
	}

	@Override
	public float scaleX()
	{
		return sprite.getScaleX();
	}

	@Override
	public float scaleY()
	{
		return sprite.getScaleY();
	}

	public Color getTint()
	{
		return sprite.getColor();
	}

	@Override
	public float width()
	{
		return sprite.getWidth();
	}

	@Override
	public float x()
	{
		return sprite.getX();
	}

	@Override
	public float y()
	{
		return sprite.getY();
	}

	@Override
	public void render(View view)
	{
		sprite.draw(view.spriteBatch(), alpha());
	}

	public void setBounds(float x, float y, float width, float height)
	{
		sprite.setBounds(x, y, width, height);
	}

	@Override
	public void setCenter(float x, float y)
	{
		sprite.setCenter(x, y);
	}

	@Override
	public void setLocation(float x, float y)
	{
		sprite.setPosition(x, y);
	}

	@Override
	public void setOrigin()
	{
		sprite.setOriginCenter();
	}

	@Override
	public void setOrigin(float originX, float originY)
	{
		sprite.setOrigin(originX, originY);
	}

	@Override
	public void setRotation(float roll)
	{
		sprite.setRotation(roll);
	}

	@Override
	public void setScale(float scaleX, float scaleY)
	{
		sprite.setScale(scaleX, scaleY);
	}

	@Override
	public void setSize(float width, float height)
	{
		sprite.setSize(width, height);
	}

	public void setTint(Color color)
	{
		sprite.setColor(color);
	}

	public void setTint(int r, int g, int b, int a)
	{
		setTint(new MyColor(r, g, b, a));
	}

	public void setTintAlpha(float alpha)
	{
		sprite.setAlpha(alpha);
	}

	@Override
	public void translateX(float x)
	{
		sprite.translateX(x);
	}

	@Override
	public void translateY(float y)
	{
		sprite.translateY(y);
	}

	@Override
	public void update(float dt)
	{

	}

	private void flip(boolean flipX, boolean flipY)
	{
		sprite.flip(flipX, flipY);
	}
}
