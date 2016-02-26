package com.jaxson.lib.gdx.states;

import com.jaxson.lib.gdx.backend.Game;

public abstract class SubState extends BaseState
{
	public static enum SubStateBehavior
	{
		/**
		 * Uses the state as a pause screen.
		 */
		Pause,

		/**
		 * Handled by the user.
		 */
		Manual;
	}

	private SubStateBehavior behavior = SubStateBehavior.Pause;

	public SubState(Game gameManager)
	{
		super(gameManager);
	}

	public SubStateBehavior getBehavior()
	{
		return behavior;
	}

	public boolean isPauseState()
	{
		return behavior == SubStateBehavior.Pause;
	}

	public void setBehavior(SubStateBehavior behavior)
	{
		this.behavior = behavior;
	}
}
