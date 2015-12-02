package com.jaxson.lib.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Toolkit;

import javax.swing.JDialog;

public class Dialog<T extends Window> extends JDialog implements AdvancedWindow
{
	private static final String TITLE          = "Dialog";
	private static final int WIDTH             = 200;
	private static final int HEIGHT            = 100;
	private static final int CLOSE_OPERATION   = DISPOSE_ON_CLOSE;
	private static final boolean RESIZEABLE    = false;
	private static final ModalityType MODALITY = DEFAULT_MODALITY_TYPE;
	private static final double MIN_SIZE       = AdvancedWindow.MIN_SIZE;
	private static final LayoutManager LAYOUT  = new BorderLayout();

	private int width, height;

	public Dialog(LayoutManager layout)
	{
		this(null, layout);
	}

	public Dialog(T window)
	{
		this(window, LAYOUT);
	}

	public Dialog(T window, LayoutManager layout)
	{
		this(WIDTH, HEIGHT, window, layout);
	}

	public Dialog(int width, int height, T window)
	{
		this(width, height, window, LAYOUT);
	}

	public Dialog(int width, int height, T window, LayoutManager layout)
	{
		super(window);
		setWindowSize(width, height);
		setTitle(TITLE);
		setDefaultCloseOperation(CLOSE_OPERATION);
		setModalityType(DEFAULT_MODALITY_TYPE);
		setResizable(RESIZEABLE);
		setLayout(layout);
		center();
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
