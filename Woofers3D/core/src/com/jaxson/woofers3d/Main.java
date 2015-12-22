package com.jaxson.woofers3d;

import com.jaxson.lib.gdx.Game;
import com.jaxson.woofers3d.states.PlayState;

public class Main extends Game
{
	private static final String TITLE = "Woofers 3D";

	public Main()
	{
		super();
		getConfig().setTitle(TITLE);
	}

	@Override
	public void create()
	{
		super.create();
		push(new PlayState());
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	@Override
	public void pause()
	{
		super.pause();
	}

	@Override
	public void render()
	{
		super.render();
	}

	@Override
	public void resize(int width, int height)
	{

	}

	@Override
	public void resume()
	{
		super.resume();
	}

}
