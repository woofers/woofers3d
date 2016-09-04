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
		getSaveableConfig().save();

		// PrintStream originalStream = System.out;
		// System.setOut(new PrintStream(new EmptyOutputStream()));
	}

	@Override
	public void create()
	{
		super.create();
		push(new PlayState(getGame()));
	}
}
