package com.jaxson.ui.board;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;

import com.jaxson.ui.Panel;

public class Options extends Panel
{
	private static final Dimension COMBOSIZE = new Dimension(80, 20);

	private Board board;
	private JButton reset, undo, redo;
	private JComboBox playerMode, difficulty, gridSize;

	public Options(Board board)
	{
		super();
		this.board = board;

		playerMode = new JComboBox();
		playerMode.addItem("1 Player");
		playerMode.addItem("2 Player");
		playerMode.setPreferredSize(COMBOSIZE);
		add(playerMode);

		difficulty = new JComboBox();
		difficulty.addItem("Easy");
		difficulty.addItem("Hard");
		difficulty.setPreferredSize(COMBOSIZE);
		add(difficulty);

		gridSize = new JComboBox();
		gridSize.addItem("8 x 8");
		gridSize.addItem("10 x 10");
		gridSize.addItem("12 x 12");
		gridSize.addItem("14 x 14");
		gridSize.addItem("16 x 16");
		gridSize.setPreferredSize(COMBOSIZE);
		add(gridSize);

		reset = new JButton("Reset");
		reset.addActionListener(new ResetListener(this));
		add(reset);

		undo = new JButton("Undo");
		undo.addActionListener(new UndoListener(this));
		add(undo);

		redo = new JButton("Redo");
		redo.addActionListener(new RedoListener(this));
		add(redo);

		resizeGrid(getGridWidth(), getGridHeight());
	}

	private int getGridHeight()
	{
		return getGridSize(1);
	}

	private int getGridSize(int dimension)
	{
		String[] string = new String[2];
		string[0] = gridSize.getSelectedItem().toString().toLowerCase();
		string = string[0].split("x");
		return Integer.parseInt(string[dimension].trim());
	}

	private int getGridWidth()
	{
		return getGridSize(0);
	}

	public Boolean isHard()
	{
		return difficulty.getSelectedItem() == difficulty.getItemAt(1);
	}

	public void onRedo()
	{

	}

	public void onReset()
	{
		resizeGrid(getGridWidth(), getGridHeight());
	}

	public void onUndo()
	{
		board.undo();
	}

	private void resizeGrid(int width, int height)
	{
		board.removeGrid();
		board.createGrid(width, height);
	}
}

class ButtonListener implements ActionListener
{
	protected Options object;

	public ButtonListener(Options object)
	{
		this.object = object;
	}

	public void actionPerformed(ActionEvent e)
	{
	}
}

class ResetListener extends ButtonListener
{
	public ResetListener(Options object)
	{
		super(object);
	}

	public void actionPerformed(ActionEvent e)
	{
		object.onReset();
	}
}

class UndoListener extends ButtonListener
{
	public UndoListener(Options object)
	{
		super(object);
	}

	public void actionPerformed(ActionEvent e)
	{
		object.onUndo();
	}
}

class RedoListener extends ButtonListener
{
	public RedoListener(Options object)
	{
		super(object);
	}

	public void actionPerformed(ActionEvent e)
	{
		object.onRedo();
	}
}
