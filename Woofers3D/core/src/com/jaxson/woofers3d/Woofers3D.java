package com.jaxson.woofers3d;

import com.jaxson.lib.gdx.GameInstance;
import com.jaxson.woofers3d.states.FlatState;
import com.jaxson.woofers3d.states.PlayState;

public class Woofers3D extends GameInstance
{
	private static final String TITLE = "Woofers 3D";

	/**
	 * TODO
	 *
	 * -Fix State API
	 * -Eliminate Magic Numbers
	 * -Sync Sprite and Enity Units****
	 * -Fix Player Anchor
	 * -2D Camera
	 * -Adjust PPM Scaling
	 * -Fix File API
	 * -Fix Zoom on Pause
	 *
	 * RULES
	 *
	 * -Idealy keep constuctors under 4 per object
	 * -Keep objects to a single unit for each type of quanitity
	 * 		(Otherwise wrap or convert outside of object)
	 * -Lines no longer than 80 wide
	 */


	public Woofers3D()
	{
		super();
		config().setTitle(TITLE);
		saveableConfig().save();
	}

	@Override
	public void create()
	{
		super.create();
		pushState(new FlatState(game()));
	}
}
