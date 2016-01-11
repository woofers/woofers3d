package com.jaxson.lib.gdx.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;

import java.util.Stack;

public class GameStateManager
{
	private Stack<State> states;

	public GameStateManager()
	{
		this.states = new Stack<State>();
	}

	public void dispose()
	{
		makeEmpty();
	}

	public boolean isEmpty()
	{
		return states.isEmpty();
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

	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch, boolean isFocused)
	{
		if (isFocused)
		{
			peek().render(spriteBatch, modelBatch);
		}
		else
		{
			peek().getPauseState().render(spriteBatch, modelBatch);
		}
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

	public void update(float dt, boolean isFocused)
	{
		if (isFocused)
		{
			peek().update(dt);
		}
		else
		{
			peek().getPauseState().update(dt);
		}
	}
}
