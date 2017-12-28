package com.jaxson.lib.gdx.io;

import com.jaxson.lib.util.Unwrapable;

public abstract class FromFile<T> implements Unwrapable<T>
{
    private GdxFile file;

    public FromFile(GdxFile file)
    {
        this.file = file;
    }

    protected GdxFile file()
    {
        return file;
    }

    @Override
    public abstract T unwrap();
}
