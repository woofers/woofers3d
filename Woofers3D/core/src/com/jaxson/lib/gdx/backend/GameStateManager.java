package com.jaxson.lib.gdx.backend;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.jaxson.lib.gdx.util.GameObject;

import java.util.Stack;

/**
 * A class that mangers states.
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public class GameStateManager extends GameObject
{
	private GameManager gameManager;
	private Stack<State> states;

	/**
	 * Constructs a empty {@link GameStateManager}
	 * @param gameManager Reference to the {@link GameManager}
	 */
	public GameStateManager(GameManager gameManager)
	{
		this.gameManager = gameManager;
		this.states = new Stack<State>();
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
	 * Gets whether the {@link GameStateManager} is empty.
	 * @return {@link boolean} - Whether the {@link GameStateManager} is empty
	 */
	public boolean isEmpty()
	{
		return states.isEmpty();
	}

	private boolean isFocused()
	{
		return gameManager.isFocused();
	}

	private boolean isMinimized()
	{
		return gameManager.isMinimized();
	}

	private boolean isPaused()
	{
		return gameManager.isPaused();
	}

	private void makeEmpty()
	{
		if (isEmpty()) return;
		pop();
		makeEmpty();
	}

	@Override
	public void pause()
	{
		peek().pause();
		if (isPaused() && hasPausedState()) peek().getPauseState().pause();
	}

	/**
	 * Peeks at the current {@link State}.
	 * @return {@link State} - The current state
	 */
	public State peek()
	{
		return states.peek();
	}

	/**
	 * Disposes of the current {@link State}.
	 */
	public void pop()
	{
		states.pop().dispose();
	}

	/**
	 * Pushes a {@link State} to the top {@link GameStateManager}.
	 * @param state The state to add
	 */
	public void push(State state)
	{
		states.push(state);
	}

	/**
	 * Renders the current {@link State} and sub state.
	 * @param spriteBatch SpriteBatch
	 * @param modelBatch ModelBatch
	 */
	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch)
	{
		peek().render(spriteBatch, modelBatch);
		if (isPaused() && hasPausedState()) peek().getPauseState().render(spriteBatch, modelBatch);
	}

	/**
	 * Resizes the current {@link State}.
	 */
	public void resize(int width, int height)
	{
		peek().resize(width, height);
		if (isPaused() && hasPausedState()) peek().getPauseState().resize(width, height);
	}

	@Override
	public void resume()
	{
		peek().resume();
		if (isPaused() && hasPausedState()) peek().getPauseState().resume();
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
	 * Gets the number of states in the {@link GameStateManager}.
	 * @return {@link int} - Whether the {@link GameStateManager} is empty
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
		if (!isPaused())
		{
			peek().update(dt);
		}
		else
		{
			if (hasPausedState()) peek().getPauseState().update(dt);
		}
	}
}
