package com.jaxson.lib.gdx.graphics.g2d.entities.types;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.jaxson.lib.gdx.graphics.views.View;
import com.jaxson.lib.util.Unwrapable;

public class SpriteActor extends Sprite
{
    private com.badlogic.gdx.graphics.g2d.Sprite sprite;

    public SpriteActor(Texture texture)
    {
        this.sprite = new com.badlogic.gdx.graphics.g2d.Sprite(texture);
    }

    public SpriteActor(Unwrapable<Texture> texture)
    {
        this(texture.unwrap());
    }

    @Override
    public void dispose()
    {

    }

    public void flip()
    {
        flip(true, true);
    }

    private void flip(boolean flipX, boolean flipY)
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

    @Override
    public float height()
    {
        return originalHeight() * scale().y;
    }

    @Override
    public void moveCenterTo(Vector2 center)
    {
        sprite.setCenter(center.x, center.y);
    }

    @Override
    public void moveTo(Vector2 location)
    {
        sprite.setPosition(location.x, location.y);
    }

    @Override
    public Vector2 origin()
    {
        return new Vector2(sprite.getOriginX(), sprite.getOriginY());
    }

    public float originalHeight()
    {
        return sprite.getHeight();
    }

    public float originalWidth()
    {
        return sprite.getWidth();
    }

    @Override
    public void render(View view)
    {
        sprite.draw(view.spriteBatch(), alpha());
        // view.spriteBatch().draw(sprite, x(), y());
    }

    @Override
    public float rotation()
    {
        return sprite.getRotation();
    }

    @Override
    public Vector2 scale()
    {
        return new Vector2(sprite.getScaleX(), sprite.getScaleY());
    }

    @Override
    public void scale(Vector2 scale)
    {
        Vector2 newScale = scale().scl(scale);
        sprite.setScale(newScale.x, newScale.y);
    }

    @Override
    public void setAlpha(float alpha)
    {
        super.setAlpha(alpha);
        sprite.setAlpha(alpha);
    }

    public void setBounds(float x, float y, float width, float height)
    {
        sprite.setBounds(x, y, width, height);
    }

    @Override
    public void setOrigin()
    {
        sprite.setOriginCenter();
    }

    @Override
    public void setOrigin(Vector2 origin)
    {
        sprite.setOrigin(origin.x, origin.y);
    }

    @Override
    public void setRotation(float roll)
    {
        sprite.setRotation(roll);
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

    public Color tint()
    {
        return sprite.getColor();
    }

    @Override
    public void translate(Vector2 translation)
    {
        sprite.translateX(translation.x);
        sprite.translateY(translation.y);
    }

    @Override
    public void setFlip(boolean flipX, boolean flipY)
    {
        if (flipX)
        {
            sprite.setScale(-Math.abs(sprite.getScaleX()), sprite.getScaleY());
        }
        else
        {
            sprite.setScale(Math.abs(sprite.getScaleX()), sprite.getScaleY());
        }
        if (flipY)
        {
            sprite.setScale(sprite.getScaleX(), -Math.abs(sprite.getScaleY()));
        }
        else
        {
            sprite.setScale(sprite.getScaleX(), Math.abs(sprite.getScaleY()));
        }
    }

    @Override
    public float width()
    {
        return originalWidth() * scale().x;
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
}
