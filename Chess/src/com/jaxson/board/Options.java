package com.jaxson.board;

import com.jaxson.ui.Panel;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class Options extends Panel
{
	private Board board;
	private JButton reset;
	private JComboBox playerMode, difficulty, gridSize;

	public Options(Board board)
	{
		super();
		this.board = board;

		playerMode = new JComboBox();
		playerMode.addItem("1 Player");
		playerMode.addItem("2 Player");
		playerMode.setPreferredSize(new Dimension(80, 20));
		add(playerMode);

		difficulty = new JComboBox();
		difficulty.addItem("Easy");
		difficulty.addItem("Hard");
		difficulty.setPreferredSize(new Dimension(80, 20));
		add(difficulty);

		gridSize = new JComboBox();
		gridSize.addItem("8 x 8");
		gridSize.addItem("10 x 10");
		gridSize.addItem("12 x 12");
		gridSize.addItem("14 x 14");
		gridSize.addItem("16 x 16");
		gridSize.setPreferredSize(new Dimension(80, 20));
		add(gridSize);

		reset = new JButton("Reset");
		reset.addActionListener(new MyActionListener(this));
		add(reset);

		resizeGrid(getGridWidth(), getGridHeight());
	}

	private int getGridSize(int dimension)
	{
		String[] string = new String[2];
		string[0] = gridSize.getSelectedItem().toString().toLowerCase();
		string = string[0].split("x");
		return Integer.parseInt(string[dimension].trim());
	}

	public int getGridWidth()
	{
		return getGridSize(0);
	}

	public int getGridHeight()
	{
		return getGridSize(1);
	}

	public Boolean isHard()
	{
		return difficulty.getSelectedItem() == difficulty.getItemAt(1);
	}

	public void resizeGrid(int width, int height)
	{
		board.removeGrid();
		board.createGrid(width, height);
	}
}

class MyActionListener implements ActionListener
{
	private Options object;

	public MyActionListener(Options object)
	{
		this.object = object;
	}

	public void actionPerformed(ActionEvent e)
	{
		object.resizeGrid(object.getGridWidth(), object.getGridHeight());
	}
}
