package com.jaxson.board;

import com.jaxson.ui.Window;
import com.jaxson.board.containers.Point;
import java.awt.GridLayout;

public class PromotionWindow extends Window
{
	private static final int SIZE = 2;
	private Spot[][] spots;

	public PromotionWindow(int width, int height, int color)
	{
		super(width, height);
		setTitle("Promote your Pawn");
		setLayout(new GridLayout(SIZE, SIZE));
		spots = new Spot[SIZE][SIZE];
		for (int y = 0; y < SIZE; y ++)
		{
			for (int x = 0; x < SIZE; x ++)
			{
				spots[x][y] = new Spot(new Point(x, y), null);
				add(spots[x][y]);
			}
		}
		spots[0][0].createPiece(Piece.QUEEN, color);
		spots[1][0].createPiece(Piece.ROOK, color);
		spots[0][1].createPiece(Piece.BISHOP, color);
		spots[1][1].createPiece(Piece.KNIGHT, color);
	}

	public int getResult()
	{
		return Piece.QUEEN;
	}
}
