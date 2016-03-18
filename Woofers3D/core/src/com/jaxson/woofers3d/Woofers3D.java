package com.jaxson.woofers3d;

import com.jaxson.lib.gdx.GameInstance;
import com.jaxson.woofers3d.states.PlayState;

public class Woofers3D extends GameInstance
{
	private static final String TITLE = "Woofers 3D";

	public Woofers3D()
	{
		super();
		getConfig().setTitle(TITLE);
		getConfig().smartRead();
	}

	@Override
	public void create()
	{
		super.create();
		push(new PlayState(getGame()));
	}
}
