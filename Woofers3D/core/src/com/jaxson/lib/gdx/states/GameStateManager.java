package com.jaxson.lib.gdx.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.jaxson.lib.gdx.input.KeyHandler;
import java.util.Stack;

public class GameStateManager
{
	private Stack<State<?>> states;
	private KeyHandler keyHandler;

	public GameStateManager()
	{
		this.states = new Stack<State<?>>();
		this.keyHandler = new KeyHandler();
	}

	public void dispose()
	{
		makeEmpty();
		keyHandler = null;
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

	public State<?> peek()
	{
		return states.peek();
	}

	public void pop()
	{
		states.pop().dispose();
	}

	public void push(State<?> state)
	{
		state.setInputProcessor(keyHandler);
		states.push(state);
	}

	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch)
	{
		peek().render(spriteBatch, modelBatch);
	}

	public void resume()
	{
		peek().resume();
	}

	public void set(State<?> state)
	{
		pop();
		push(state);
	}

	public void update(float dt)
	{
		peek().update(dt);
	}
}
