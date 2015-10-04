package com.jaxson;

import com.jaxson.ui.*;
import com.jaxson.board.*;
import java.awt.Dimension;
import java.awt.BorderLayout;

public class GameWindow extends Window
{
	private ScaleContainer scaleContainer;
	private Board board;
	private Options options;

	public GameWindow(int width, int height)
	{
		super(width, height);
		setTitle("Chess");

		board = new Board();
		scaleContainer = new ScaleContainer<Board>(board);
		add(scaleContainer, BorderLayout.CENTER);

		options = new Options(board);
		options.setPreferredSize(new Dimension(100, 100));
		add(options, BorderLayout.LINE_END);

		draw();
	}
}
