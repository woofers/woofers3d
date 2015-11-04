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
		silce(index + 1);
		history.add(move);
		index = size() - 1;
		print();
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

	public void silce(int index)
	{
		for (int i = index; i < size(); i ++)
		{
			history.remove(i);
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
		print();
	}

	public void undo(Board board)
	{
		if (isEmpty()) return;
		Move move = get(index);
		move.undo(board);
		index --;
		print();
	}

	public void print()
	{
		int tmp = 0;
		System.out.println("-----history------");
		for (Move move: history)
		{
			System.out.println(move.toString() + "\nIndex " + tmp);
			tmp ++;
		}
		System.out.println("Curretn index: " + index);

	}
}
