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
	private static final Dimension COMBO_SIZE = new Dimension(80, 20);
	private static final Dimension UNDOO_SIZE = new Dimension(30, 26);
	private static final Dimension PANEL_SIZE = new Dimension(100, 30);
	private static final Dimension SIZE       = new Dimension(100, 100);
	private static final int UNDOPANEL_WIDTH  = 2;
	private static final int UNDOPANEL_HEIGHT = 1;
	private static final int[] GRID_SIZES     = {8, 10, 12, 14, 16};
	private static final int[] PLAYERMODES    = {1, 2};
	private static final String EASY          = "Easy";
	private static final String HARD          = "Hard";
	private static final String PLAYER        = "Player";
	private static final String RESET         = "Reset";
	private static final String UNDO          = "<<";
	private static final String REDO          = ">>";
	private static final String SEPERATOR     = "x";
	private static final String SPACE         = " ";

	private Board board;
	private Panel undoPanel;
	private JButton restButton, undoButton, redoButton;
	private JComboBox playerModeBox, difficultyBox, gridSizeBox;

	public Options(Board board)
	{
		super();
		this.board = board;
		setPreferredSize(SIZE);

		playerModeBox = new JComboBox();
		for (int mode: PLAYERMODES)
		{
			playerModeBox.addItem(mode + SPACE + PLAYER);
		}
		playerModeBox.setPreferredSize(COMBO_SIZE);
		add(playerModeBox);

		difficultyBox = new JComboBox();
		difficultyBox.addItem(EASY);
		difficultyBox.addItem(HARD);
		difficultyBox.setPreferredSize(COMBO_SIZE);
		add(difficultyBox);

		gridSizeBox = new JComboBox();
		for (int size: GRID_SIZES)
		{
			gridSizeBox.addItem(size + SEPERATOR + size);
		}
		gridSizeBox.setPreferredSize(COMBO_SIZE);
		add(gridSizeBox);

		undoPanel = new Panel(new GridLayout(UNDOPANEL_HEIGHT, UNDOPANEL_WIDTH));
		undoPanel.setPreferredSize(PANEL_SIZE);
		add(undoPanel);

		undoButton = new JButton(UNDO);
		undoButton.setPreferredSize(UNDOO_SIZE);
		undoButton.addActionListener(new UndoListener(this));
		undoPanel.add(undoButton);

		redoButton = new JButton(REDO);
		redoButton.setPreferredSize(UNDOO_SIZE);
		redoButton.addActionListener(new RedoListener(this));
		undoPanel.add(redoButton);

		restButton = new JButton(RESET);
		restButton.addActionListener(new ResetListener(this));
		add(restButton);

		reset();
	}

	private int getGridHeight()
	{
		return getGridSize(1);
	}

	private int getGridSize(int dimension)
	{
		String[] string = new String[2];
		string = gridSizeBox.getSelectedItem().toString().toLowerCase().split(SEPERATOR);
		return Integer.parseInt(string[dimension].trim());
	}

	private int getGridWidth()
	{
		return getGridSize(0);
	}

	public Boolean hasComputer()
	{
		return playerModeBox.getSelectedItem() == playerModeBox.getItemAt(0);
	}

	public Boolean isHard()
	{
		return difficultyBox.getSelectedItem() == difficultyBox.getItemAt(1);
	}

	private void reset()
	{
		restGrid();
		updateControls();
	}

	private void restGrid()
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
		redoButton.setEnabled(board.hasRedo());
	}

	private void updateUndo()
	{
		undoButton.setEnabled(board.hasUndo());
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
