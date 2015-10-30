package com.jaxson.board.move;

import com.jaxson.util.MyArrayList;
import com.jaxson.ui.board.Board;

public class MoveHandler
{
	private MoveList history;
	private int index;

	public MoveHandler()
	{
		history = new MoveList();
		index = -1;
	}

	public void add(Move move)
	{
		history.add(move);
		index = history.size() - 1;
	}

	public Boolean isEmpty()
	{
		return index == -1;
	}

	public void silce()
	{
		//history = history.subList(0, index - 1);
	}

	public void redo(Board board)
	{
		if (isEmpty()) return;
		Move move = history.get(index);
		move.move(board);
	}

	public void undo(Board board)
	{
		if (isEmpty()) return;
		Move move = history.get(index);
		move.undo(board);
		history.remove(move);
		index --;
	}
}
