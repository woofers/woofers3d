package com.jaxson.lib.woofers3d.input;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.util.Printer;
import com.jaxson.lib.gdx.input.GameAccelerometer;
import com.jaxson.lib.gdx.input.Accelerometer;

public class BallAccelerometer extends GameAccelerometer
{
    private static float Y_OFFSET = 0.085f;

    public BallAccelerometer(Accelerometer accelerometer)
    {
        super(accelerometer);
    }

    @Override
    public float y()
    {
        return super.y() + Y_OFFSET;
    }
}
