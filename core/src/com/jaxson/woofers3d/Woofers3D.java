package com.jaxson.woofers3d;

import com.jaxson.lib.gdx.GameInstance;
import com.jaxson.woofers3d.states.PlayState;
import com.jaxson.woofers3d.states.FlatState;
import com.jaxson.woofers3d.states.SphereState;
import com.jaxson.lib.io.DataFile;
import com.jaxson.lib.util.MyArrayList;

public class Woofers3D extends GameInstance
{
    private static final String TITLE = "Woofers 3D";

    public Woofers3D()
    {
        super();
        config().setTitle(TITLE);
        saveableConfig().save();
    }

    @Override
    public void create()
    {
        super.create();
        pushState(new SphereState(game()));
    }
}
