package com.jaxson.lib.gdx.states;

import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;

public class GameStateManager
{
	private Stack<State<?>> states;

	public GameStateManager()
	{
		states = new Stack<State<?>>();
	}

	public boolean isEmpty()
	{
		return states.isEmpty();
	}

	public void push(State<?> state)
	{
		states.push(state);
	}

	public void pop()
	{
		states.pop().dispose();
	}

	public void popAll()
	{
		if (isEmpty())
			return;
		pop();
		popAll();
	}

	public void set(State<?> state)
	{
		pop();
		states.push(state);
	}

	public void update(float dt)
	{
		states.peek().update(dt);
	}

	public void render(SpriteBatch spriteBatch)
	{
		render(spriteBatch, null);
	}

	public void render(ModelBatch modelBatch)
	{
		render(null, modelBatch);
	}

	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch)
	{
		states.peek().render(spriteBatch, modelBatch);
	}
}
