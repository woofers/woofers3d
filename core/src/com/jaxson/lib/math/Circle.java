package com.jaxson.lib.math;

import com.jaxson.lib.util.MyComparable;
import com.jaxson.lib.util.Printer;

public class Circle implements MyComparable<Circle>
{
    public static final float PI = (float) Math.PI;

    /**
     * Constant to convert a radius to as diameter.
     */
    public static final float RADIUS_TO_DIAMETER = 2f;

    /**
     * Constant to convert a diameter to as radius.
     */
    public static final float DIAMETER_TO_RADIUS
            = new Reciprocal(RADIUS_TO_DIAMETER).floatValue();

    private float radius;

    public Circle(float radius)
    {
        this.radius = radius;
    }

    public float circumference()
    {
        return diamater() * PI;
    }

    @Override
    public int compareTo(Circle other)
    {
        return (int) (radius() - other.radius());
    }

    public float diamater()
    {
        return radius() * RADIUS_TO_DIAMETER;
    }

    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof Circle)) return false;
        Circle circle = (Circle) other;
        return radius() == circle.radius();
    }

    public float radius()
    {
        return radius;
    }

    @Override
    public String toString()
    {
        return new Printer(getClass(),
                new Printer.Label("Radius", radius()),
                new Printer.Label("Circumference", circumference())).toString();
    }
}
