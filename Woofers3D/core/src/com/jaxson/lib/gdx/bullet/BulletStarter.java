package com.jaxson.lib.gdx.bullet;

import com.badlogic.gdx.physics.bullet.Bullet;

public class BulletStarter
{
	private static final boolean REFERENCE_COUNTING = true;
	private static boolean isInit;

	public static void init()
	{
		if (isInit) return;
		Bullet.init(REFERENCE_COUNTING);
		isInit = true;
	}
}
