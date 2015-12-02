package com.jaxson.lib.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Window extends JFrame implements AdvancedWindow
{
	private static final String TITLE         = "Window";
	private static final int WIDTH            = 800;
	private static final int HEIGHT           = 600;
	private static final int CLOSE_OPERATION  = EXIT_ON_CLOSE;
	private static final boolean RESIZEABLE   = true;
	private static final double MIN_SIZE      = AdvancedWindow.MIN_SIZE;
	private static final LayoutManager LAYOUT = new BorderLayout();

	private int width, height;

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
		setDefaultCloseOperation(CLOSE_OPERATION);
		setResizable(RESIZEABLE);
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

	private double getAspectRatio()
	{
		return getAspectRatio(width, height);
	}

	private double getAspectRatio(int width, int height)
	{
		if (height == 0) return 1;
		return (double)(width) / (double)(height);
	}

	public int getWindowHeight()
	{
		return height;
	}

	private int getWindowHeight(double scale)
	{
		return (int)(getScreenSize().height * scale);
	}

	public int getWindowWidth()
	{
		return width;
	}

	private int getWindowWidth(double scale)
	{
		return (int)(getScreenSize().height * scale * getAspectRatio());
	}

	private Dimension getScreenSize()
	{
		return Toolkit.getDefaultToolkit().getScreenSize();
	}

	public void setIcon(String iconPath)
	{
		setIconImage(new ImageIcon(iconPath).getImage());
	}

	public void setScreenRatio(double scale)
	{
		setWindowSize(getWindowWidth(scale), getWindowHeight(scale));
	}

	public void setWindowHeight(int height)
	{
		setWindowSize(width, height);
	}

	public void setWindowWidth(int width)
	{
		setWindowSize(width, height);
	}

	public void setWindowSize(int width, int height)
	{
		int minWidth  = (int)(width * MIN_SIZE);
		int minHeight = (int)(height * MIN_SIZE);
		this.width    = width;
		this.height   = height;
		setSize(width, height);
		setMinimumSize(new Dimension(minWidth, minHeight));
		setMaximumSize(getScreenSize());
	}

	public void showWindow()
	{
		setVisible(true);
		draw();
	}
}
