package com.jaxson.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Window extends JFrame
{
	private static final String TITLE = "Window";
	private static final int WIDTH = 400;
	private static final int HEIGHT = 400;
	private static final LayoutManager LAYOUT = new BorderLayout();

	public Window()
	{
		this(LAYOUT);
	}

	public Window(LayoutManager layout)
	{
		this(WIDTH, HEIGHT, layout);
	}

	public Window(int width, int height)
	{
		this(width, height, LAYOUT);
	}

	public Window(int width, int height, LayoutManager layout)
	{
		super(TITLE);
		setWindowSize(width, height);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(layout);
		center();
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
		if (size.height == 0) return 1;
		return size.width / size.height;
	}

	private Dimension getScreenSize()
	{
		return Toolkit.getDefaultToolkit().getScreenSize();
	}

	public void setWindowSize(int width, int height)
	{
		setSize(width, height);
		setMinimumSize(new Dimension(width, height));
		setMaximumSize(getScreenSize());
	}

	public void showWindow()
	{
		setVisible(true);
	}
}
