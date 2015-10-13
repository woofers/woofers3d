package com.jaxson.board;

import com.jaxson.ui.Panel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

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

	private static final double SCALE = 0.8;

	public int color;
	public int type;
	private Image image;

	public Piece(int type, int color)
	{
		super();
		this.type = type;
		this.color = color;
		setOpaque(false);

		String path = "assets/images/pieces/" + color + "_" + type + ".png";
		try
		{
		  image = ImageIO.read(new File(path));
		}
		catch (IOException ex)
		{

		}
	}

	private int toInt(double i)
	{
		return (int)(Math.floor(i));
	}

	@Override
	public void paint(Graphics g)
	{
		int width, height, x, y;
		width = toInt(getWidth() * SCALE);
		height = toInt(getHeight() * SCALE);
		x = toInt((getWidth() - width) / 2);
		y = toInt((getHeight() - height) / 2);
		g.drawImage(image, x, y, width, height, null);
	}
}