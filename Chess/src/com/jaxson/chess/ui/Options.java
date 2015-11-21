package com.jaxson.chess.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;

import com.jaxson.lib.event.ButtonListener;
import com.jaxson.lib.ui.Panel;

public class Options extends Panel
{
	private static final Dimension COMBOSIZE = new Dimension(80, 20);
	private static final Dimension UNDOOSIZE = new Dimension(30, 26);
	private static final Dimension PANELSIZE = new Dimension(100, 30);
	private static final String EASY         = "Easy";
	private static final String HARD         = "Hard";
	private static final String PLAYER       = "Player";
	private static final String SEPERATOR    = "x";
	private static final String RESET        = "Reset";
	private static final String UNDO         = "<<";
	private static final String REDO         = ">>";

	private Board board;
	private JButton reset, undo, redo;
	private Panel undoPanel;
	private JComboBox playerMode, difficulty, gridSize;

	public Options(Board board)
	{
		super();
		this.board = board;

		playerMode = new JComboBox();
		playerMode.addItem("1 " + PLAYER);
		playerMode.addItem("2 " + PLAYER);
		playerMode.setPreferredSize(COMBOSIZE);
		add(playerMode);

		difficulty = new JComboBox();
		difficulty.addItem(EASY);
		difficulty.addItem(HARD);
		difficulty.setPreferredSize(COMBOSIZE);
		add(difficulty);

		gridSize = new JComboBox();
		gridSize.addItem("8" + SEPERATOR + " 8");
		gridSize.addItem("10 " + SEPERATOR + " 10");
		gridSize.addItem("12 " + SEPERATOR + " 12");
		gridSize.addItem("14 " + SEPERATOR + " 14");
		gridSize.addItem("16 " + SEPERATOR + " 16");
		gridSize.setPreferredSize(COMBOSIZE);
		add(gridSize);

		undoPanel = new Panel(new GridLayout(1, 2));
		undoPanel.setPreferredSize(PANELSIZE);
		add(undoPanel);

		undo = new JButton(UNDO);
		undo.setPreferredSize(UNDOOSIZE);
		undo.addActionListener(new UndoListener(this));
		undoPanel.add(undo);

		redo = new JButton(REDO);
		redo.setPreferredSize(UNDOOSIZE);
		redo.addActionListener(new RedoListener(this));
		undoPanel.add(redo);

		reset = new JButton(RESET);
		reset.addActionListener(new ResetListener(this));
		add(reset);

		reset();
	}

	private int getGridHeight()
	{
		return getGridSize(1);
	}

	private int getGridSize(int dimension)
	{
		String[] string = new String[2];
		string[0] = gridSize.getSelectedItem().toString().toLowerCase();
		string = string[0].split(SEPERATOR);
		return Integer.parseInt(string[dimension].trim());
	}

	private int getGridWidth()
	{
		return getGridSize(0);
	}

	public Boolean hasComputer()
	{
		return playerMode.getSelectedItem() == playerMode.getItemAt(0);
	}

	public Boolean isHard()
	{
		return difficulty.getSelectedItem() == difficulty.getItemAt(1);
	}

	private void reset()
	{
		resetGrid();
		updateControls();
	}

	private void resetGrid()
	{
		board.reset(getGridWidth(), getGridHeight());
	}

	public void onRedo()
	{
		board.redo();
		updateControls();
	}

	public void onReset()
	{
		reset();
	}

	public void onUndo()
	{
		board.undo();
		updateControls();
	}

	public void updateControls()
	{
		updateRedo();
		updateUndo();
	}

	private void updateRedo()
	{
		redo.setEnabled(board.hasRedo());
	}

	private void updateUndo()
	{
		undo.setEnabled(board.hasUndo());
	}
}

class ResetListener extends ButtonListener<Options>
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

class UndoListener extends ButtonListener<Options>
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

class RedoListener extends ButtonListener<Options>
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
