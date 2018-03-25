package com.jaxson.lib.gdx.box2d.bodies;

import static com.jaxson.lib.gdx.box2d.simulation.Box2DWorld.METERS_TO_PIXELS;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.jaxson.lib.gdx.box2d.bodies.types.SpriteBody;
import com.badlogic.gdx.graphics.Color;

public class Floor extends SpriteBody
{
    private static final float WIDTH = 6f;
    private static final float HEIGHT = 0.5f;

    private static Texture getTex(float width, float height, Color color)
    {
        int pixelWidth = (int) (width * METERS_TO_PIXELS);
        int pixelHeight = (int) (height * METERS_TO_PIXELS);

        Pixmap pixmap
                = new Pixmap(pixelWidth, pixelHeight, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillRectangle(0, 0, pixelWidth, pixelHeight);
        return new Texture(pixmap);
    }

    public Floor(float x, float y)
    {
        this(x, y, Color.WHITE);
    }

    public Floor(float x, float y, Color color)
    {
        super(getTex(WIDTH, HEIGHT, color), BodyDef.BodyType.StaticBody, 1f);
        moveTo(new Vector2(x, y));
    }

    @Override
    public void dispose()
    {
        super.dispose();
    }
}
