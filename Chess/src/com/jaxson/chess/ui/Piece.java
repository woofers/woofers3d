package com.jaxson.chess.ui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import com.jaxson.chess.board.IntPiece;
import com.jaxson.chess.board.move.Promotion;
import com.jaxson.chess.ui.ChessWindow;
import com.jaxson.lib.geom.Point;
import com.jaxson.lib.ui.Panel;
import com.jaxson.lib.ui.Window;

public class Piece extends Panel
{
	public static final int KING   = 1;
	public static final int QUEEN  = 2;
	public static final int ROOK   = 3;
	public static final int BISHOP = 4;
	public static final int KNIGHT = 5;
	public static final int PAWN   = 6;
	public static final int WHITE  = 1;
	public static final int BLACK  = 2;

	private static final String IMAGEPATH     = "assets/images/pieces/";
	private static final String SEPARATOR     = "_";
	private static final String IMAGETYPE     = ".png";

	private static final double IMAGE_SCALE            = 0.8;
	private static final int PROMOTION_WIDTH           = 300;
	private static final int PROMOTION_HEIGHT          = 300;
	private static final double PROMOTION_SCREEN_SCALE = 0.4;

	public int type, color, direction, turn;
	public int passingIndex;
	private Image image;

	public Piece(int type, int color)
	{
		this(type, color, 0);
	}

	public Piece(int type, int color, int turn)
	{
		super();
		this.type = type;
		this.color = color;
		this.turn = turn;
		this.direction = getStartDirection();
		passingIndex = -1;
		setOpaque(false);
		setImage(IMAGEPATH + color + SEPARATOR + type + IMAGETYPE);
	}

	public Promotion getPromotion(ChessWindow window, IntPiece spot)
	{
		PromotionWindow promotionWindow = new PromotionWindow(PROMOTION_WIDTH, PROMOTION_HEIGHT, color, window);
		//promotionWindow.setScreenRatio(PROMOTION_SCREEN_SCALE);
		return new Promotion(spot, promotionWindow.getResult());
	}

	public static int getOppositeColor(int color)
	{
		if (color == WHITE) return BLACK;
		return WHITE;
	}

	private int getStartDirection()
	{
		if (color == BLACK) return +1;
		return -1;
	}

	public Boolean isFriendly(int color)
	{
		return this.color == color;
	}

	@Override
	public void paint(Graphics g)
	{
		int width, height, x, y;
		width = (int)(getWidth() * IMAGE_SCALE);
		height = (int)(getHeight() * IMAGE_SCALE);
		x = (int)((getWidth() - width) / 2);
		y = (int)((getHeight() - height) / 2);
		g.drawImage(image, x, y, width, height, null);
	}

	private void setImage(String imagePath)
	{
		try
		{
			image = ImageIO.read(new File(imagePath));
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}

	public IntPiece toIntPiece(Point location)
	{
		return new IntPiece(this, location);
	}
}
