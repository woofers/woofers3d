package com.jaxson.lib.gdx.states;

import com.jaxson.lib.gdx.backend.Game;

public abstract class State extends BaseState
{
	private SubState subState;

	public State(Game game)
	{
		super(game);
	}

	public SubState getSubState()
	{
		return subState;
	}

	public boolean hasPauseState()
	{
		if (hasSubState()) return getSubState().isPauseState();
		return false;
	}

	public boolean hasSubState()
	{
		return getSubState() != null;
	}

	public void setSubState(SubState subState)
	{
		this.subState = subState;
	}
}
