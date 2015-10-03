package com.jaxson.ui;

import com.jaxson.GameWindow;

public class WindowStarter<T extends Window>
{
	public WindowStarter(int width, int height)
	{
		javax.swing.SwingUtilities.invokeLater(new MyRunnable<T>(width, height));
	}
}

class MyRunnable<T extends Window> implements Runnable
{
	private int width, height;

	public MyRunnable(int width, int height)
	{
		this.width = width;
		this.height = height;
	}

	public void run()
	{
		GameWindow window = new GameWindow(width, height);
	}
}