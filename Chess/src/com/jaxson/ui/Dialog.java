package com.jaxson.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Toolkit;

import javax.swing.JDialog;

public class Dialog<T extends Window> extends JDialog
{
	private static final String TITLE = "Dialog";
	private static final int WIDTH = 200;
	private static final int HEIGHT = 100;
	private static final LayoutManager LAYOUT = new BorderLayout();

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
		setDialogSize(width, height);
		setTitle(TITLE);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModalityType(DEFAULT_MODALITY_TYPE);
		setResizable(false);
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

	private Dimension getScreenSize()
	{
		return Toolkit.getDefaultToolkit().getScreenSize();
	}

	public void setDialogSize(int width, int height)
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
