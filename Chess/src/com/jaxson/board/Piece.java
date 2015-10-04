package com.jaxson.board;

import com.jaxson.ui.Panel;
import java.awt.image.BufferedImage;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class Piece extends Panel
{
	public static final int KING   = 1;
	public static final int QUEEN  = 2;
	public static final int ROOK   = 3;
	public static final int BISHOP = 4;
	public static final int KNIGHT = 5;
	public static final int PAWN   = 6;
	public static final int BLACK  = 2;
	public static final int WHITE  = 1;

	public int color;
	public int type;
	private BufferedImage image;

	public Piece(int type, int color)
	{
		super(new BorderLayout());
		this.type = type;
		this.color = color;

		String path = "assets/images/pieces/" + color + "_" + type + ".png";
		try
		{
		  image = ImageIO.read(new File(path));
		}
		catch (IOException ex)
		{

		}
		JLabel picLabel = new JLabel(new ImageIcon(image));
		add(picLabel, BorderLayout.CENTER);
	}
}