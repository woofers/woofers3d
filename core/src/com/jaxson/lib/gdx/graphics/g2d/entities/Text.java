package com.jaxson.lib.gdx.graphics.g2d.entities;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.jaxson.lib.gdx.graphics.g2d.entities.types.Sprite;
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
    public void moveCenterTo(Vector2 center)
    {

    }

    @Override
    public void moveTo(Vector2 location)
    {
        this.location = location;
    }

    @Override
    public Vector2 origin()
    {
        return Vector2.Zero;
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
    public Vector2 scale()
    {
        return scale;
    }

    @Override
    public void scale(Vector2 scale)
    {
        this.scale.set(scale);
    }

    @Override
    public void setFlip(boolean flipX, boolean flipY)
    {

    }

    @Override
    public void setOrigin()
    {

    }

    @Override
    public void setOrigin(Vector2 origin)
    {

    }

    @Override
    public void setRotation(float roll)
    {

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
    public void translate(Vector2 translation)
    {
        location.add(translation);
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
