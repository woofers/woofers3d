package com.jaxson.lib.gdx.states;

import com.jaxson.lib.gdx.backend.Game;

public abstract class State extends BaseState
{
	private SubState subState;

	public State(Game game)
	{
		super(game);
	}

	public SubState subState()
	{
		return subState;
	}

	public boolean hasPauseState()
	{
		if (hasSubState()) return subState().isPauseState();
		return false;
	}

	public boolean hasSubState()
	{
		return subState() != null;
	}

	public void setSubState(SubState subState)
	{
		this.subState = subState;
	}
}
