package com.jaxson.board.move;

import com.jaxson.util.MyArrayList;
import com.jaxson.ui.board.Board;

public class MoveHistory
{
	private MoveList history;
	private int index;

	public MoveHistory()
	{
		history = new MoveList();
		index = -1;
	}

	public void add(Move move)
	{
		history.add(move);
		index = history.size() - 1;
	}

	public Boolean hasUndo()
	{
		return !isEmpty();
	}

	public Boolean hasRedo()
	{
		return index < history.size();
	}

	public Move get(int index)
	{
		return history.get(index);
	}

	public Boolean isEmpty()
	{
		return index == -1;
	}

	public void silce()
	{
		for (int i = index - 1; i < history.size(); i ++)
		{
			history.remove(i);
		}
	}

	public void redo(Board board)
	{
		if (isEmpty()) return;
		index ++;
		Move move = get(index);
		move.move(board);
	}

	public void undo(Board board)
	{
		if (isEmpty()) return;
		Move move = get(index);
		move.undo(board);
		index --;
	}
}
