package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.Input;
import com.jaxson.lib.util.Uncertainty;

public abstract class Peripheral implements Uncertainty
{
    private Input input;

    public Peripheral(Input input)
    {
        this.input = input;
    }

    @Override
    public abstract boolean exists();

    protected Input input()
    {
        return input;
    }
}
