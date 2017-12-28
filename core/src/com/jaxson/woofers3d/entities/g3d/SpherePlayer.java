package com.jaxson.woofers3d.entities.g3d;

import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.bullet.simulation.bodies.types.RigidBody;
import com.jaxson.lib.gdx.bullet.simulation.collision.SphereShape;
import com.jaxson.lib.gdx.graphics.views.TargetCamera;
import com.jaxson.lib.gdx.input.GameAccelerometer;
import com.jaxson.lib.gdx.input.Inputs;
import com.jaxson.lib.gdx.input.Keyboard;
import com.jaxson.lib.gdx.input.KeyboardKey;

public class SpherePlayer extends RigidBody
{
    private static final String PATH = "entities/dogSphere/dogSphere.g3db";
    private static final float SCALE = 1f;
    private static final float RADIUS = 0.34231704f / 2f + 0.03f;
    private static final float HITBOX_SCALE = 1f;
    private static final float SPEED = 4f;
    private static final float JUMP_IMPULSE = 350f;

    private Keyboard keyboard;
    private GameAccelerometer accelerometer;
    private KeyboardKey forwardKey;
    private KeyboardKey backwardKey;
    private KeyboardKey leftKey;
    private KeyboardKey rightKey;
    private KeyboardKey jumpKey;

    private KeyboardKey cameraKey;
    private KeyboardKey resetKey;

    public SpherePlayer(TargetCamera camera)
    {
        super(readModel(PATH), new SphereShape(RADIUS), 1f);
        setCollisionShapeScale(HITBOX_SCALE);
        scale(SCALE);

        this.keyboard = Inputs.keyboard();
        this.accelerometer = new GameAccelerometer(Inputs.accelerometer());
        this.forwardKey = keyboard.key("W");
        this.backwardKey = keyboard.key("S");
        this.leftKey = keyboard.key("A");
        this.rightKey = keyboard.key("D");
        this.jumpKey = keyboard.key("Space");
        // this.cameraKey = keyboard().key("T");
        // this.resetKey = keyboard().key("Y");
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

        if (keyboard().exists())
        {
            if (true)
            {
                if (leftKey.isDown())
                {
                    applyCentralImpulse(new Vector3(dt * SPEED, 0f, 0f));
                }
                if (rightKey.isDown())
                {
                    applyCentralImpulse(new Vector3(-dt * SPEED, 0f, 0f));
                }
                if (onGround() && jumpKey.isDown())
                {
                    applyCentralImpulse(new Vector3(0f, dt * JUMP_IMPULSE, 0f));
                }
            }
            if (forwardKey.isDown())
            {
                applyCentralImpulse(new Vector3(0f, 0f, dt * SPEED));
            }
            if (backwardKey.isDown())
            {
                applyCentralImpulse(new Vector3(0f, 0f, -dt * SPEED));
            }
        }
        // if (cameraKey.isPressed()) toggleCamera();
        // if (resetKey.isPressed()) reset();
    }

    private Keyboard keyboard()
    {
        return keyboard;
    }

    @Override
    public void update(float dt)
    {
        super.update(dt);
    }
}
