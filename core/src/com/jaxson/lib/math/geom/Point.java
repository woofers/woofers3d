package com.jaxson.lib.math.geom;

import com.jaxson.lib.util.Printer;

public class Point
{
    private int x;
    private int y;

    public Point()
    {
        this(0, 0);
    }

    public Point(int x, int y)
    {
        set(x, y);
    }

    public Point(Point point)
    {
        this(point.x, point.y);
    }

    @Override
    public Point clone()
    {
        return new Point(this);
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public Point set(int x, int y)
    {
        setX(x);
        setY(y);
        return this;
    }

    public Point setX(int x)
    {
        this.x = x;
        return this;
    }

    public Point setY(int y)
    {
        this.y = y;
        return this;
    }

    @Override
    public String toString()
    {
        return new Printer(getClass(),
                new Printer.Label("X", getX()),
                new Printer.Label("Y", getY())).toString();
    }
}
