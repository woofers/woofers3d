package com.jaxson.lib.gdx.backend;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;

import java.util.Stack;

public class GameStateManager
{
	private GameManager gameManager;
	private Stack<State> states;

	public GameStateManager(GameManager gameManager)
	{
		this.gameManager = gameManager;
		this.states = new Stack<State>();
	}

	public void dispose()
	{
		makeEmpty();
	}

	public boolean hasPausedState()
	{
		return peek().hasPauseState();
	}

	public boolean isEmpty()
	{
		return states.isEmpty();
	}

	public boolean isFocused()
	{
		return gameManager.isFocused();
	}

	public boolean isMinimized()
	{
		return gameManager.isMinimized();
	}

	public boolean isPaused()
	{
		return gameManager.isPaused();
	}

	public void makeEmpty()
	{
		if (isEmpty()) return;
		pop();
		makeEmpty();
	}

	public void pause()
	{
		peek().pause();
	}

	public State peek()
	{
		return states.peek();
	}

	public void pop()
	{
		states.pop().dispose();
	}

	public void push(State state)
	{
		states.push(state);
	}

	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch)
	{
		peek().render(spriteBatch, modelBatch);
		if (isPaused() && hasPausedState()) peek().getPauseState().render(spriteBatch, modelBatch);
	}

	public void resize(int width, int height)
	{
		peek().resize(width, height);
	}

	public void resume()
	{
		peek().resume();
	}

	public void set(State state)
	{
		pop();
		push(state);
	}

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
