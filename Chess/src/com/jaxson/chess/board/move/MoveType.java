package com.jaxson.chess.board.move;

import com.jaxson.chess.board.IntBoard;
import com.jaxson.chess.ui.Board;

public interface MoveType
{
	public Boolean isEmpty();

	public Boolean isPromotion();

	public void move(Board board);

	public void move(IntBoard board);

	public void undo(Board board);

	public void undo(IntBoard board);

	public Boolean overwritesFriendly();

	public String toString();
}
