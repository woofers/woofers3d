package com.jaxson.woofers3d.entities.g2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.jaxson.lib.gdx.box2d.bodies.types.SpriteBody;
import com.jaxson.lib.gdx.box2d.bodies.types.Hitbox;
import com.jaxson.lib.gdx.input.Inputs;
import com.jaxson.lib.gdx.input.Keyboard;
import com.jaxson.lib.gdx.input.KeyboardKey;
import com.jaxson.lib.gdx.io.GdxFile;
import com.jaxson.lib.gdx.io.TextureFromFile;
import com.jaxson.lib.math.MyMath;

public class Player extends SpriteBody
{
    private static final String PATH = "sprites/gazelle/gazelle.png";
    private static final float SCALE = 4f;
    private static final float SPEED = 4.5f;
    private static final float JUMP_VELOCITY = 7.4f;

    private Keyboard keyboard;
    private KeyboardKey forwardKey;
    private KeyboardKey backwardKey;
    private KeyboardKey leftKey;
    private KeyboardKey rightKey;
    private KeyboardKey jumpKey;
    private KeyboardKey resetKey;

    private Vector2 startLocation = new Vector2(5f, 5.5f);

    public Player()
    {
        super(new TextureFromFile(new GdxFile(PATH)),
                new Hitbox(1.15f, 1f, -0.6f, -1.8f),
                BodyDef.BodyType.DynamicBody,
                1f);
        scale(SCALE);
        reset();

        this.keyboard = Inputs.keyboard();
        this.forwardKey = keyboard.key("W");
        this.backwardKey = keyboard.key("S");
        this.leftKey = keyboard.key("A");
        this.rightKey = keyboard.key("D");
        this.jumpKey = keyboard.key("Space");
        this.resetKey = keyboard.key("Y");
    }

    @Override
    public void dispose()
    {
        super.dispose();
    }

    @Override
    protected void input(float dt)
    {
        super.input(dt);
        if (rightKey.isDown())
        {
            body().setLinearVelocity(SPEED, body().getLinearVelocity().y);
            setFlip(false, false);
         }
        else if (leftKey.isDown())
        {
            body().setLinearVelocity(-SPEED, body().getLinearVelocity().y);
            setFlip(true, false);
        }
        else
        {
            body().setLinearVelocity(0f, body().getLinearVelocity().y);
        }

        if (jumpKey.isPressed()
                && MyMath.abs(body().getLinearVelocity().y) < 0.1f)
        {
            body().setLinearVelocity(
                    body().getLinearVelocity().x, JUMP_VELOCITY);
        }

        if (resetKey.isPressed()) reset();
    }

    protected void reset()
    {
        moveTo(startLocation.cpy());
        setRotation(0f);
        resetVelocity();
    }
}
