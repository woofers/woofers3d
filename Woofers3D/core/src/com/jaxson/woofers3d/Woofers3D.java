package com.jaxson.woofers3d;

import com.jaxson.lib.gdx.Game;
import com.jaxson.woofers3d.states.PlayState;

public class Woofers3D extends Game
{
	private static final String TITLE = "Woofers 3D";
	private static final String ICON_PATH = "icon.png";

	public Woofers3D()
	{
		super();
		getConfig().setTitle(TITLE);
		getConfig().setIconPath(ICON_PATH);
		//getConfig().smartRead();
	}

	@Override
	public void create()
	{
		super.create();
		push(new PlayState(getGameManager()));
	}
}
