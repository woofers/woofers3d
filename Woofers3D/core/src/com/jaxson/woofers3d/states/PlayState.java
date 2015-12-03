package com.jaxson.woofers3d.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.jaxson.lib.gdx.entities.Box;
import com.jaxson.lib.gdx.graphics.MyPerspectiveCamera;
import com.jaxson.lib.gdx.states.GameStateManager;
import com.jaxson.lib.gdx.states.State;
import com.jaxson.woofers3d.entities.Player;

public class PlayState extends State<MyPerspectiveCamera>
{
	private FPSLogger fps;
	private Box box;
	private Player player;

	public PlayState(GameStateManager gameStateManager)
	{
		super(gameStateManager, new MyPerspectiveCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

		fps = new FPSLogger();

		box = new Box();
		add(box);

		player = new Player(getCamera());
		add(player);
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	@Override
	public void input()
	{

	}

	@Override
	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch)
	{
		super.render(spriteBatch, modelBatch);
		fps.log();
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
	}
}