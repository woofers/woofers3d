package com.jaxson.lib.gdx.backend;

import com.jaxson.lib.gdx.graphics.views.View;
import com.jaxson.lib.gdx.states.State;
import com.jaxson.lib.gdx.util.GameObject;
import java.util.Stack;

/**
 * A class that contains {@link State}s.
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public class GameStates extends GameObject
{
	private Game game;
	private Stack<State> states;

	/**
	 * Constructs a empty {@link GameStates}
	 * @param game Reference to the {@link Game}
	 */
	public GameStates(Game game)
	{
		this.game = game;
		this.states = new Stack<>();
	}

	/**
	 * Disposes of all the states.
	 */
	@Override
	public void dispose()
	{
		makeEmpty();
	}


	/**
	 * Gets whether the current state has a sub state.
	 * @return {@link boolean} - Whether the state has a paused state
	 */
	public boolean hasPausedState()
	{
		return peek().hasPauseState();
	}

	/**
	 * Gets whether the {@link GameStates} is empty.
	 * @return {@link boolean} - Whether the {@link GameStates} is empty
	 */
	public boolean isEmpty()
	{
		return states.isEmpty();
	}

	@Override
	public void pause()
	{
		if (isEmpty()) return;
		peek().pause();
		if (updatesSubState()) peek().subState().pause();
	}

	/**
	 * Peeks at the current {@link State}.
	 * @return {@link State} - The current state
	 */
	public State peek()
	{
		if (isEmpty()) return null;
		return states.peek();
	}

	/**
	 * Disposes of the current {@link State}.
	 */
	public void pop()
	{
		if (isEmpty()) return;
		states.pop().dispose();
	}

	/**
	 * Pushes a {@link State} to the top {@link GameStates}.
	 * @param state The state to add
	 */
	public void push(State state)
	{
		states.push(state);
	}

	/**
	 * Renders the current {@link State} and sub state.
	 * @param view The view
	 */
	@Override
	public void render(View view)
	{
		if (isEmpty()) return;
		peek().render(view);
		if (updatesSubState()) peek().subState().render(view);
	}

	/**
	 * Resizes the current {@link State}.
	 */
	@Override
	public void resize(int width, int height)
	{
		if (isEmpty()) return;
		peek().resize(width, height);
		if (updatesSubState()) peek().subState().resize(width, height);
	}

	@Override
	public void resume()
	{
		if (isEmpty()) return;
		peek().resume();
		if (updatesSubState()) peek().subState().resume();
	}

	/**
	 * Sets the current {@link State} and disposes the previous one.
	 * @param state The state
	 */
	public void set(State state)
	{
		pop();
		push(state);
	}

	/**
	 * Gets the number of states in the {@link GameStates}.
	 * @return {@link int} - The amount of states in the
	 * {@link GameStates}
	 */
	public int size()
	{
		return states.size();
	}

	/**
	 * Update the current {@link State}.
	 */
	@Override
	public void update(float dt)
	{
		if (isEmpty()) return;
		if (!isPaused())
		{
			peek().update(dt);
		}
		else
		{
			if (hasPausedState()) peek().subState().update(dt);
		}
	}

	private boolean isFocused()
	{
		return game.isFocused();
	}

	private boolean isMinimized()
	{
		return game.isMinimized();
	}

	private boolean isPaused()
	{
		return game.isPaused();
	}

	private void makeEmpty()
	{
		if (isEmpty()) return;
		pop();
		makeEmpty();
	}

	private boolean updatesSubState()
	{
		return isPaused() && hasPausedState();
	}
}
