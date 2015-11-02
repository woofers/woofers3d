package com.jaxson.board.move;

import com.jaxson.board.IntBoard;
import com.jaxson.ui.board.Board;

public interface MoveType
{
	public Boolean isEmpty();

	public void move(Board board);

	public void move(IntBoard board);

	public void undo(Board board);

	public Boolean overwritesFriendly();

	public String toString();
}
