package com.jaxson.board;

import com.jaxson.board.containers.Point;
import com.jaxson.ui.*;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class PromotionWindow<T extends Window> extends Dialog
{
	private static final int SIZE = 2;

	private Spot[][] spots;
	private ScaleContainer scaleContainer;
	private Panel panel;
	private JLabel label;
	private int result;

	public PromotionWindow(int width, int height, int color, T window)
	{
		super(width, height, window);
		System.out.println(window.toString());
		setTitle("Chess");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new MyWindowAdapter(this));

		label = new JLabel("Upgrade your pawn", SwingConstants.CENTER);
		label.setFont(new Font(label.getName(), Font.PLAIN, 30));
		add(label, BorderLayout.PAGE_END);

		panel = new Panel(new GridLayout(SIZE, SIZE));
		scaleContainer = new ScaleContainer(panel);
		add(scaleContainer, BorderLayout.CENTER);

		spots = new Spot[SIZE][SIZE];
		for (int y = 0; y < SIZE; y ++)
		{
			for (int x = 0; x < SIZE; x ++)
			{
				spots[x][y] = new Spot(new Point(x, y));
				spots[x][y].addMouseListener(new MyMouseAdapter2(spots[x][y], this));
				panel.add(spots[x][y]);
			}
		}
		spots[0][0].createPiece(Piece.QUEEN, color);
		spots[1][0].createPiece(Piece.ROOK, color);
		spots[0][1].createPiece(Piece.BISHOP, color);
		spots[1][1].createPiece(Piece.KNIGHT, color);
	}

	public void setResult(int value)
	{
		result = value;
	}

	public int getResult()
	{
		return result;
	}

	public void close()
	{
		dispose();
	}

	public void xClose()
	{
		setResult(rand(Piece.QUEEN, Piece.KNIGHT));
		close();
	}

	private int rand(int min, int max)
	{
		Random random = new Random();
		return random.nextInt((max - min) + 1) + min;
	}
}

class MyMouseAdapter2 extends MouseAdapter
{
	private Spot object;
	private PromotionWindow window;

	public MyMouseAdapter2(Spot object, PromotionWindow window)
	{
		this.object = object;
		this.window = window;
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		object.holdSelect();
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		object.select();
		window.setResult(object.getPiece().type);
		window.close();
	}
}

class MyWindowAdapter extends WindowAdapter
{
	private PromotionWindow window;

	public MyWindowAdapter(PromotionWindow window)
	{
		this.window = window;
	}

	public void windowClosing(WindowEvent e)
	{
		window.xClose();
	}
}
