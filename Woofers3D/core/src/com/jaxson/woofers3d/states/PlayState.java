package com.jaxson.woofers3d.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.woofers3d.entities.Box;
import com.jaxson.woofers3d.entities.Player;
import com.jaxson.woofers3d.states.GameStateManager;
import com.jaxson.woofers3d.states.State3D;

public class PlayState extends State3D
{
	private FPSLogger fps;
	private Box box;
	private Player player;

	public PlayState(GameStateManager gameStateManager)
	{
		super(gameStateManager);

		fps = new FPSLogger();

		box = new Box();
		add(box);

		player = new Player();
		add(player);
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	@Override
	protected void handleInput()
	{

	}

	@Override
	public void render(ModelBatch modelBatch)
	{
		super.render(modelBatch);
		fps.log();
	}

	@Override
	public void update(float dt)
	{
		camera.rotateAround(Vector3.Zero, new Vector3(0, 1, 0), 1f);
		super.update(dt);
	}
}