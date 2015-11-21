package com.jaxson.chess.board.move;

import java.lang.Exception;

public class NoDestinationException extends Exception
{
	public NoDestinationException()
	{
		super();
	}

	public NoDestinationException(String message)
	{
		super(message);
	}
}