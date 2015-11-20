package com.jaxson.ui.board;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;

import com.jaxson.ui.ScaleContainer;
import com.jaxson.ui.Window;

public class ChessWindow extends Window
{
	public static final String TITLE         = "Chess";
	private static final String ICON_PATH    = "assets/images/icon.png";
	private static final double SCREEN_SCALE = 0.8;

	private Board board;
	private ScaleContainer scaleContainer;
	private Options options;

	public ChessWindow(int width, int height)
	{
		super(width, height);
		setTitle(TITLE);
		setScreenRatio(SCREEN_SCALE);
		center();

		ImageIcon icon = new ImageIcon(ICON_PATH);
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
