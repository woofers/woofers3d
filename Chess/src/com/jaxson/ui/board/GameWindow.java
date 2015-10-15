package com.jaxson.ui.board;

import com.jaxson.board.*;
import com.jaxson.ui.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.*;
import java.awt.Dialog.ModalityType;

public class GameWindow extends Window
{
	private ScaleContainer scaleContainer;
	private Board board;
	private Options options;

	public GameWindow(int width, int height)
	{
		super(width, height);
		setTitle("Chess");

		board = new Board(this);
		scaleContainer = new ScaleContainer(board);
		add(scaleContainer, BorderLayout.CENTER);

		options = new Options(board);
		options.setPreferredSize(new Dimension(100, 100));
		add(options, BorderLayout.LINE_END);

		draw();
	}
}
