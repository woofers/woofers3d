package com.jaxson.board;

import com.jaxson.ui.Panel;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class Options extends Panel
{
	private JButton button;
	private JComboBox difficulty;
	private JComboBox gridSize;


	public Options()
	{
		super();
		difficulty = new JComboBox();
		difficulty.addItem("Easy");
		difficulty.addItem("Hard");
		difficulty.setPreferredSize(new Dimension(80, 20));
		add(difficulty);

		gridSize = new JComboBox();
		gridSize.addItem("6 x 6");
		gridSize.addItem("8 x 8");
		gridSize.addItem("10 X 10");
		gridSize.setPreferredSize(new Dimension(80, 20));
		add(gridSize);

		button = new JButton("Reset");
		add(button);
	}
}
