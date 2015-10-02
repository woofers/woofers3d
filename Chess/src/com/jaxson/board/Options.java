package com.jaxson.board;

import com.jaxson.ui.Panel;
import javax.swing.JButton;

public class Options extends Panel
{
	private JButton[] buttons;

	public Options()
	{
		super();
		buttons = new JButton[2];
		buttons[0] = new JButton("Reset");
		add(buttons[0]);
		buttons[1] = new JButton("2 Player");
		add(buttons[1]);
	}
}
