package com.jaxson.chess;

import javax.swing.SwingUtilities;
import com.jaxson.chess.ui.ChessWindow;

public class Main
{
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				new ChessWindow(WIDTH, HEIGHT);
			}
		});
	}
}
