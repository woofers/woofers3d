package com.jaxson.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jaxson.woofers3d.Woofers3D;

public class DesktopLauncher
{
	public static void main(String[] arg)
	{
		new Woofers3D().startDesktop();
	}
}
