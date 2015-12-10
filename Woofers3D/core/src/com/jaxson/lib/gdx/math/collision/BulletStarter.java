package com.jaxson.lib.gdx.math.collision;

import com.badlogic.gdx.physics.bullet.Bullet;

public class BulletStarter
{
	private static boolean isInit;

	public static void init()
	{
		if (isInit) return;
		Bullet.init();
		isInit = true;
	}
}
