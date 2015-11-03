package com.jaxson.ui.board;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;

import com.jaxson.event.ButtonListener;
import com.jaxson.ui.Panel;

public class Options extends Panel
{
	private static final Dimension COMBOSIZE = new Dimension(80, 20);
	private static final Dimension UNDOOSIZE = new Dimension(30, 26);
	private static final Dimension PANELSIZE = new Dimension(100, 30);

	private Board board;
	private JButton reset, undo, redo;
	private Panel undoPanel;
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

		undoPanel = new Panel(new GridLayout(1, 2));
		undoPanel.setPreferredSize(PANELSIZE);
		add(undoPanel);

		undo = new JButton("<<");
		undo.setPreferredSize(UNDOOSIZE);
		undo.addActionListener(new UndoListener(this));
		undoPanel.add(undo);

		redo = new JButton(">>");
		redo.setPreferredSize(UNDOOSIZE);
		redo.addActionListener(new RedoListener(this));
		undoPanel.add(redo);

		reset = new JButton("Reset");
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
		if (board.hasRedo())
		{
			redo.setEnabled(true);
		}
		else
		{
			redo.setEnabled(false);
		}
	}

	private void updateUndo()
	{
		if (board.hasUndo())
		{
			undo.setEnabled(true);
		}
		else
		{
			undo.setEnabled(false);
		}
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
