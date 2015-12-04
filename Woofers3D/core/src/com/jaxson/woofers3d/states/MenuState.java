package com.jaxson.woofers3d.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.jaxson.lib.gdx.states.GameStateManager;
import com.jaxson.lib.gdx.states.State;

public class MenuState extends State<PerspectiveCamera>
{
	public MenuState(GameStateManager gameStateManager)
	{
		super(gameStateManager, new PerspectiveCamera(75, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
	}

	@Override
	public void dispose()
	{

	}

	@Override
	protected void input()
	{

	}

	@Override
	public void render(ModelBatch modelBatch)
	{

	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
	}
}