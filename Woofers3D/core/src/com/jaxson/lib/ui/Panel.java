package com.jaxson.lib.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class Panel extends JPanel
{
	private static final LayoutManager LAYOUT = new FlowLayout();

	public Panel()
	{
		this(LAYOUT);
	}

	public Panel(LayoutManager layout)
	{
		super(layout);
	}

	public void draw()
	{
		revalidate();
		repaint();
	}

	public void setPanelSize(int width, int height)
	{
		setSize(width, height);
		setMinimumSize(new Dimension(width, height));
		setMaximumSize(new Dimension(width, height));
		setPreferredSize(new Dimension(width, height));
	}
}
