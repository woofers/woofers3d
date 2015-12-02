package com.jaxson.chess.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import com.jaxson.lib.ui.ScaleContainer;
import com.jaxson.lib.ui.Window;

public class ChessWindow extends Window
{
	public static final String TITLE             = "Chess";
	private static final String ICON_PATH        = "assets/images/icon.png";
	private static final double SCREEN_SCALE     = 0.8;
	private static final String BOARD_LOCATION   = BorderLayout.CENTER;
	private static final String OPTIONS_LOCATION = BorderLayout.LINE_END;

	private Board board;
	private ScaleContainer scaleContainer;
	private Options options;

	public ChessWindow(int width, int height)
	{
		super(width, height);
		setTitle(TITLE);
		setIcon(ICON_PATH);
		setScreenRatio(SCREEN_SCALE);
		center();

		board = new Board(this);
		scaleContainer = new ScaleContainer<>(board);
		add(scaleContainer, BOARD_LOCATION);

		options = new Options(board);
		board.setOptions(options);
		add(options, OPTIONS_LOCATION);

		showWindow();
	}
}
