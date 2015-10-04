package com.jaxson.ui;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ScaleContainer<T extends Panel> extends Panel
{
	private T panel;
	private double aspectRatio;

	public ScaleContainer(T panel)
	{
		super();
		add(panel);
		aspectRatio = getAspectRatio(panel);
		this.panel = panel;
		addComponentListener(new MyComponentAdapter<ScaleContainer>(this));
	}

	public void keepAspectRatio()
	{
		int size =  Math.min(getWidth(), getHeight());
		panel.setPanelSize(size, (int)(size / aspectRatio));
		draw();
	}

	private double getAspectRatio(T panel)
	{
		Dimension size = panel.getPreferredSize();
		if (size.height == 0)
		{
			return 1;
		}
		return size.width / size.height;
	}
}

class MyComponentAdapter<T extends ScaleContainer> extends ComponentAdapter
{
	private T object;

	public MyComponentAdapter(T object)
	{
		this.object = object;
	}

	@Override
	public void componentResized(ComponentEvent e)
	{
		object.keepAspectRatio();
	}
}
