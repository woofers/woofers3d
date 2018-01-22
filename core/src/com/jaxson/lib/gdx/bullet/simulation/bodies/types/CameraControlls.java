package com.jaxson.lib.gdx.bullet.simulation.bodies.types;

import com.badlogic.gdx.graphics.g3d.Model;
import com.jaxson.lib.gdx.bullet.simulation.collision.types.ConvexShape;
import com.jaxson.lib.gdx.graphics.views.TargetCamera;
import com.jaxson.lib.util.Unwrapable;
import com.jaxson.lib.util.Resetable;
import com.jaxson.lib.gdx.graphics.g3d.entities.types.Entity;

public class CameraControlls<T extends Entity> implements Resetable
{
    private TargetCamera camera;
    private T object;

    public CameraControlls(T object, TargetCamera camera)
    {
        this.camera = camera;
        this.object = object;
        camera.setTarget(object);
    }

    public TargetCamera camera()
    {
        return camera;
    }

    public boolean cameraIsLocked()
    {
        return !camera().hasTarget();
    }

    public void dispose()
    {
        unlockCamera();
    }

    public boolean hasCamera()
    {
        return camera() != null;
    }

    public void lockCamera()
    {
        if (hasCamera()) camera().setTarget(null);
    }

    public void reset()
    {
        unlockCamera();
    }

    public void setCamera(TargetCamera camera)
    {
        if (camera == camera()) return;
        if (camera == null) lockCamera();
        this.camera = camera;
        unlockCamera();
    }

    public void toggleCamera()
    {
        if (cameraIsLocked())
        {
            unlockCamera();
        }
        else
        {
            lockCamera();
        }
    }

    public void unlockCamera()
    {
        if (hasCamera()) camera().setTarget(object);
    }
}
