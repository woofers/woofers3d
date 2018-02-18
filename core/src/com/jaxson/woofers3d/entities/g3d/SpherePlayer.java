package com.jaxson.woofers3d.entities.g3d;

import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.bullet.simulation.bodies.types.RigidBody;
import com.jaxson.lib.gdx.bullet.simulation.bodies.types.CameraControlls;
import com.jaxson.lib.gdx.bullet.simulation.collision.SphereShape;
import com.jaxson.lib.gdx.graphics.views.TargetCamera;
import com.jaxson.lib.woofers3d.input.BallAccelerometer;
import com.jaxson.lib.gdx.input.Inputs;
import com.jaxson.lib.gdx.input.Keyboard;
import com.jaxson.lib.gdx.input.KeyboardKey;
import com.jaxson.lib.gdx.input.TouchScreen;
import com.jaxson.lib.util.Printer;
import com.jaxson.lib.gdx.math.GdxMath;

public class SpherePlayer extends RigidBody
{
    private static final String PATH = "entities/dogSphere/dogSphere.g3db";
    private static final float SCALE = 1f;
    private static final float RADIUS = 0.34231704f / 2f + 0.03f;
    private static final float HITBOX_SCALE = 1f;
    private static final float SPEED = 4f;
    private static final float MAX_SPEED = 5f;
    private static final float JUMP_IMPULSE = 365f;

    private CameraControlls cameraControlls;

    private Keyboard keyboard;
    private TouchScreen touchScreen;
    private BallAccelerometer accelerometer;
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
        setScale(SCALE);
        // rotate(180f, 0f, 0f);

        this.cameraControlls = new CameraControlls<SpherePlayer>(this, camera);

        this.keyboard = Inputs.keyboard();
        this.accelerometer = new BallAccelerometer(Inputs.accelerometer());
        this.touchScreen = Inputs.touchScreen();
        this.forwardKey = keyboard.key("W");
        this.backwardKey = keyboard.key("S");
        this.leftKey = keyboard.key("A");
        this.rightKey = keyboard.key("D");
        this.jumpKey = keyboard.key("Space");
        this.cameraKey = keyboard().key("T");
        this.resetKey = keyboard().key("Y");
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

        Vector3 velocity = linearVelocity();
        Vector3 absVelocity = GdxMath.absVector(velocity.cpy());
        if (keyboard().exists())
        {
            if (leftKey.isDown())
            {
                if (absVelocity.x <= MAX_SPEED)
                {
                    applyCentralImpulse(new Vector3(dt * SPEED, 0f, 0f));
                }
            }
            else if (velocity.x > 0f)
            {
                applyCentralImpulse(new Vector3(-dt * SPEED, 0f, 0f));
            }

            if (rightKey.isDown())
            {
                if (absVelocity.x <= MAX_SPEED)
                {
                    applyCentralImpulse(new Vector3(-dt * SPEED, 0f, 0f));
                }
            }
            else if (velocity.x < 0f)
            {
                applyCentralImpulse(new Vector3(dt * SPEED, 0f, 0f));
            }

            if (forwardKey.isDown())
            {
                if (absVelocity.z <= MAX_SPEED)
                {
                    applyCentralImpulse(new Vector3(0f, 0f, dt * SPEED));
                }
            }
            else if (velocity.z > 0f)
            {
                applyCentralImpulse(new Vector3(0f, 0f, -dt * SPEED));
            }

            if (backwardKey.isDown())
            {
                if (absVelocity.z <= MAX_SPEED)
                {
                    applyCentralImpulse(new Vector3(0f, 0f, -dt * SPEED));
                }
            }
            else if (velocity.z < 0f)
            {
                applyCentralImpulse(new Vector3(0f, 0f, dt * SPEED));
            }

            if (jumpKey.isDown() && onGround())
            {
                applyCentralImpulse(new Vector3(0f, dt * JUMP_IMPULSE, 0f));
            }
        }
        if (accelerometer.exists())
        {
            if (accelerometer.tiltsLeft())
            {
                if (absVelocity.x <= MAX_SPEED)
                {
                    applyCentralImpulse(new Vector3(dt * SPEED, 0f, 0f));
                }
            }
            else if (velocity.x > 0f)
            {
                applyCentralImpulse(new Vector3(-dt * SPEED, 0f, 0f));
            }

            if (accelerometer.tiltsRight())
            {
                if (absVelocity.x <= MAX_SPEED)
                {
                    applyCentralImpulse(new Vector3(-dt * SPEED, 0f, 0f));
                }
            }
            else if (velocity.x < 0f)
            {
                applyCentralImpulse(new Vector3(dt * SPEED, 0f, 0f));
            }

            if (accelerometer.tiltsForward())
            {
                if (absVelocity.z <= MAX_SPEED)
                {
                    applyCentralImpulse(new Vector3(0f, 0f, dt * SPEED));
                }
            }
            else if (velocity.z > 0f)
            {
                applyCentralImpulse(new Vector3(0f, 0f, -dt * SPEED));
            }

            if (accelerometer.tiltsBackward())
            {
                if (absVelocity.z <= MAX_SPEED)
                {
                    applyCentralImpulse(new Vector3(0f, 0f, -dt * SPEED));
                }
            }
            else if (velocity.z < 0f)
            {
                applyCentralImpulse(new Vector3(0f, 0f, dt * SPEED));
            }

            if (touchScreen.justTouched() && onGround())
            {
                applyCentralImpulse(new Vector3(0f, dt * JUMP_IMPULSE, 0f));
            }
        }
        if (cameraKey.isPressed()) cameraControlls.toggleCamera();
        if (resetKey.isPressed()) reset();
        System.out.println(accelerometer.values());
        //System.out.println("X " + round(linearVelocity().x) + "m/s, Y "
        //        + round(linearVelocity().y) + "m/s, Z "
        //        + round(linearVelocity().z) + "m/s");
    }

    private Keyboard keyboard()
    {
        return keyboard;
    }

    @Override
    public String toString()
    {
        return new Printer(getClass(),
                new Printer.Label("Linear Velocity", angularVelocity()),
                new Printer.Label("Angular Velocity", linearVelocity())).toString();
    }

    @Override
    public void update(float dt)
    {
        super.update(dt);
    }

    private static float round(float i)
    {
        return Math.round(i * 100.0f) / 100.0f;
    }
}
