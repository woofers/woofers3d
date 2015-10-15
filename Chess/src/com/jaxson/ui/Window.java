package com.jaxson.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class Window extends JFrame
{
	public Window(int width, int height)
	{
		super("Window");
		setWindowSize(width, height);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// /dynamicResize(0.5);
		center();
		setVisible(true);
		setLayout(new BorderLayout());
		draw();
	}

	public void center()
	{
		setLocationRelativeTo(null);
	}

	public void draw()
	{
		getContentPane().revalidate();
		getContentPane().repaint();
	}

	public void dynamicResize(double scale)
	{
		double aspectRatio = getAspectRatio();
		Dimension screenSize = getScreenSize();
		setWindowSize((int)(screenSize.height * scale * aspectRatio), (int)(screenSize.height * scale));
	}

	private double getAspectRatio()
	{
		Dimension size = getPreferredSize();
		if (size.height == 0)
		{
			return 1;
		}
		return size.width / size.height;
	}

	public void setWindowSize(int width, int height)
	{
		setSize(width, height);
		setMinimumSize(new Dimension(width, height));
		setMaximumSize(getScreenSize());
	}

	private Dimension getScreenSize()
	{
		return Toolkit.getDefaultToolkit().getScreenSize();
	}
}
