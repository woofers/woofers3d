package com.jaxson.lib.gdx.bullet;

import com.badlogic.gdx.physics.bullet.Bullet;

public class BulletStarter
{
    private static final boolean REFERENCE_COUNTING = true;
    private static boolean isInit;

    public static void init()
    {
        init(REFERENCE_COUNTING);
    }

    public static void init(boolean countReferences)
    {
        if (isInit) return;
        Bullet.init(countReferences);
        isInit = true;
    }
}
