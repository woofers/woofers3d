package com.jaxson.lib.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener<T> implements ActionListener
{
	protected T object;

	public ButtonListener(T object)
	{
		this.object = object;
	}

	public void actionPerformed(ActionEvent e)
	{
		System.out.println(e.toString());
	}
}
