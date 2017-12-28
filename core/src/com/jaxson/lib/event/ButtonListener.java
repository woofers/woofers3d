package com.jaxson.lib.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class ButtonListener<T> implements ActionListener
{
    private T object;

    public ButtonListener(T object)
    {
        this.object = object;
    }

    @Override
    public abstract void actionPerformed(ActionEvent event);

    protected T getObject()
    {
        return object;
    }
}
