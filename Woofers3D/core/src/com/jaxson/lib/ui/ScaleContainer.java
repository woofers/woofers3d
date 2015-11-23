package com.jaxson.lib.ui;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ScaleContainer<T extends Panel> extends Panel
{
	private T panel;

	public ScaleContainer(T panel)
	{
		super();
		this.panel = panel;
		add(panel);
		setOpaque(false);
		addComponentListener(new ResizeAdapter(this));
	}

	private double getAspectRatio(T panel)
	{
		Dimension size = panel.getPreferredSize();
		if (size.height == 0) return 1;
		return size.width / size.height;
	}

	public void keepAspectRatio()
	{
		int size =  Math.min(getWidth(), getHeight());
		panel.setPanelSize(size, (int)(size / getAspectRatio(panel)));
		draw();
	}
}

class ResizeAdapter extends ComponentAdapter
{
	private ScaleContainer object;

	public ResizeAdapter(ScaleContainer object)
	{
		this.object = object;
	}

	@Override
	public void componentResized(ComponentEvent e)
	{
		object.keepAspectRatio();
	}
}
