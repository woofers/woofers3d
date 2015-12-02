package com.jaxson.chess.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.jaxson.chess.board.move.Promotion;
import com.jaxson.chess.ui.ChessWindow;
import com.jaxson.lib.geom.Point;
import com.jaxson.lib.ui.Dialog;
import com.jaxson.lib.ui.Panel;
import com.jaxson.lib.ui.ScaleContainer;
import com.jaxson.lib.ui.Window;

public class PromotionWindow extends Dialog
{
	private static final String MESSAGE        = "Promote your pawn.";
	private static final int GRID_SIZE         = 2;
	private static final int FONT_SIZE         = 30;
	private static final int DEFAULT_PIECE     = Piece.QUEEN;
	private static final String GRID_LOCATION  = BorderLayout.CENTER;
	private static final String LABEL_LOCATION = BorderLayout.PAGE_END;
	private static final int TEXT_ALIGMENT     = SwingConstants.CENTER;

	private Spot[][] spots;
	private ScaleContainer scaleContainer;
	private Panel panel;
	private JLabel label;
	private int result;

	public PromotionWindow(int width, int height, int color, ChessWindow window)
	{
		super(width, height, window);
		setTitle(ChessWindow.TITLE);

		label = new JLabel(MESSAGE, TEXT_ALIGMENT);
		label.setFont(new Font(label.getName(), Font.PLAIN, FONT_SIZE));
		add(label, LABEL_LOCATION);

		panel = new Panel(new GridLayout(GRID_SIZE, GRID_SIZE));
		scaleContainer = new ScaleContainer<>(panel);
		add(scaleContainer, GRID_LOCATION);

		spots = new Spot[GRID_SIZE][GRID_SIZE];
		for (int y = 0; y < GRID_SIZE; y ++)
		{
			for (int x = 0; x < GRID_SIZE; x ++)
			{
				spots[x][y] = new Spot(new Point(x, y));
				spots[x][y].addMouseListener(new PieceAdapter(spots[x][y], this));
				panel.add(spots[x][y]);
			}
		}
		spots[0][0].createPiece(Piece.QUEEN, color);
		spots[1][0].createPiece(Piece.ROOK, color);
		spots[0][1].createPiece(Piece.BISHOP, color);
		spots[1][1].createPiece(Piece.KNIGHT, color);
		result = DEFAULT_PIECE;

		showWindow();
	}

	public int getResult()
	{
		return result;
	}

	public void onSelect(Spot spot)
	{
		spot.select();
		result = spot.getPiece().type;
		dispose();
	}
}

class PieceAdapter extends MouseAdapter
{
	private Spot object;
	private PromotionWindow window;

	public PieceAdapter(Spot object, PromotionWindow window)
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
		window.onSelect(object);
	}
}
