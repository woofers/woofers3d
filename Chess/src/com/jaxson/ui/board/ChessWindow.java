package com.jaxson.ui.board;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;

import com.jaxson.ui.ScaleContainer;
import com.jaxson.ui.Window;

public class ChessWindow extends Window
{
	public static final String TITLE = "Chess";
	private static final String ICONPATH = "assets/images/icon.png";

	private Board board;
	private ScaleContainer scaleContainer;
	private Options options;

	public ChessWindow(int width, int height)
	{
		super(width, height);
		setTitle(TITLE);
		ImageIcon icon = new ImageIcon(ICONPATH);
		setIconImage(icon.getImage());

		board = new Board(this);
		scaleContainer = new ScaleContainer<>(board);
		add(scaleContainer, BorderLayout.CENTER);

		options = new Options(board);
		options.setPreferredSize(new Dimension(100, 100));
		board.setOptions(options);
		add(options, BorderLayout.LINE_END);

		showWindow();
		draw();
	}
}
