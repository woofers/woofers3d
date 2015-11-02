package com.jaxson.ui.board;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;

import com.jaxson.ui.ScaleContainer;
import com.jaxson.ui.Window;

public class ChessWindow extends Window
{
	private Board board;
	private ScaleContainer scaleContainer;
	private Options options;

	public ChessWindow(int width, int height)
	{
		super(width, height);
		setTitle("Chess");
		ImageIcon icon = new ImageIcon("assets/images/icon.png");
		setIconImage(icon.getImage());

		board = new Board<>(this);
		scaleContainer = new ScaleContainer<>(board);
		add(scaleContainer, BorderLayout.CENTER);

		options = new Options(board);
		options.setPreferredSize(new Dimension(100, 100));
		add(options, BorderLayout.LINE_END);

		draw();
	}
}
