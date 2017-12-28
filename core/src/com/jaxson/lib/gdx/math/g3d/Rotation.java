package com.jaxson.lib.gdx.math.g3d;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class Rotation
{
    private Quaternion quaternion;

    public Rotation()
    {
        this(new Quaternion());
    }

    public Rotation(float yaw, float pitch, float roll)
    {
        this(new Vector3(yaw, pitch, roll));
    }

    public Rotation(float x, float y, float z, float w)
    {
        this(new Quaternion(x, y, z, w));
    }

    public Rotation(Quaternion quaternion)
    {
        this.quaternion = quaternion;
    }

    public Rotation(Vector3 eulerAngles)
    {
        this(new Quaternion().setEulerAngles(
                eulerAngles.x,
                eulerAngles.y,
                eulerAngles.z));
    }

    public Rotation(Vector3 axis, float angle)
    {
        this(new Quaternion(axis, angle));
    }

    public Vector3 eulerAngles()
    {
        return new Vector3(quaternion().getYaw(),
                quaternion().getPitch(),
                quaternion().getRoll());
    }

    public Vector3 eulerAnglesRad()
    {
        return new Vector3(quaternion().getYawRad(),
                quaternion().getPitchRad(),
                quaternion().getRollRad());
    }

    public Quaternion quaternion()
    {
        return quaternion;
    }
}
