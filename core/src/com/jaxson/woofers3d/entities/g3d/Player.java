package com.jaxson.woofers3d.entities.g3d;

import com.jaxson.lib.gdx.bullet.simulation.bodies.types.CameraPlayerBody;
import com.jaxson.lib.gdx.graphics.views.TargetCamera;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.input.KeyboardKey;

public class Player extends CameraPlayerBody
{
    private static final String PATH = "entities/dog/dog.g3dj";
    private static final float SCALE = 0.6f;
    private static final float HITBOX_SCALE = 90f / 100f;

    private KeyboardKey cameraKey;
    private KeyboardKey resetKey;

    public Player(TargetCamera camera)
    {
        super(readModel(PATH), camera);
        setCollisionShapeScale(HITBOX_SCALE);
        setScale(SCALE);
        moveTo(new Vector3(0f, -0.25f, -1f));

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
        if (cameraKey.isPressed()) cameraControlls().toggleCamera();
        if (resetKey.isPressed()) reset();
    }

    @Override
    public void update(float dt)
    {
        super.update(dt);
    }
}
