package com.jaxson.lib.gdx.bullet.simulation.bodies.types;

import com.badlogic.gdx.graphics.g3d.Model;
import com.jaxson.lib.gdx.bullet.simulation.collision.types.ConvexShape;
import com.jaxson.lib.gdx.graphics.views.TargetCamera;
import com.jaxson.lib.util.Unwrapable;

public class CameraPlayerBody extends PlayerBody
{
    private CameraControlls cameraControlls;

    public CameraPlayerBody(Model model, ConvexShape shape, TargetCamera camera)
    {
        super(model, shape);
        this.cameraControlls = new CameraControlls<CameraPlayerBody>(this, camera);
    }

    public CameraPlayerBody(Model model, TargetCamera camera)
    {
        this(model, fittedShape(model), camera);
    }

    public CameraPlayerBody(Unwrapable<Model> model,
            ConvexShape shape,
            TargetCamera camera)
    {
        this(model.unwrap(), shape, camera);
    }

    public CameraPlayerBody(Unwrapable<Model> model, TargetCamera camera)
    {
        this(model.unwrap(), camera);
    }

    public TargetCamera camera()
    {
        return cameraControlls().camera();
    }

    public CameraControlls cameraControlls()
    {
        return cameraControlls;
    }

    @Override
    public void dispose()
    {
        super.dispose();
        cameraControlls().dispose();
    }

    @Override
    protected void input(float dt)
    {
        super.input(dt);
    }

    @Override
    public void reset()
    {
        super.reset();
        cameraControlls().reset();
    }

    @Override
    public void update(float dt)
    {
        super.update(dt);
    }
}
