package com.jaxson.chess.board.move;

import com.jaxson.chess.ui.Board;
import com.jaxson.lib.util.MyArrayList;

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
		silce(index + 1);
		history.add(move);
		index = size() - 1;
	}

	public void clear()
	{
		history.clear();
	}

	public Boolean hasUndo()
	{
		return index >= 0 && size() >= index;
	}

	public Boolean hasRedo()
	{
		return index < size() - 1 && size() > 0;
	}

	public Move get(int index)
	{
		return history.get(index);
	}

	public Boolean isEmpty()
	{
		return history.isEmpty();
	}

	public void silce(int startIndex)
	{
		while (startIndex < size())
		{
			history.remove(startIndex);
		}
	}

	public int size()
	{
		return history.size();
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
