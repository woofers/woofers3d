package com.jaxson.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jaxson.woofers3d.Main;

public class DesktopLauncher
{
	public static void main(String[] arg)
	{
		Main game = new Main();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title         = Main.TITLE;
		config.width         = Main.WIDTH;
		config.height        = Main.HEIGHT;
		config.height        = Main.HEIGHT;
		config.vSyncEnabled  = Main.VSYNC;
		config.foregroundFPS = Main.FPS;
		config.backgroundFPS = Main.BACKGROUND_FPS;
		config.resizable     = Main.RESIZABLE;
		new LwjglApplication(game, config);
	}
}
