package com.jaxson.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jaxson.woofers3d.Main;

public class DesktopLauncher
{
	public static void main(String[] arg)
	{
		Main game = new Main();
		new LwjglApplication(game, game.getLwjglConfig());
	}
}
