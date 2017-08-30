package com.jaxson.woofers3d.states;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.jaxson.lib.gdx.box2d.Box2DWorld;
import com.jaxson.lib.gdx.box2d.Box2DState;
import com.badlogic.gdx.physics.box2d.World;
import com.jaxson.lib.gdx.backend.Game;
import com.jaxson.lib.gdx.box2d.Floor;
import com.jaxson.lib.gdx.bullet.BulletState;
import com.jaxson.lib.gdx.graphics.views.View;
import com.jaxson.woofers3d.entities.g2d.Player;

public class FlatState extends Box2DState
{
	private Camera camera;
	private World world;
	private Player player;
	private Floor floor;

	public FlatState(Game game)
	{
		super(game);
		setSubState(new PauseState(game));

		camera = view().spriteView().getCamera();

		player = new Player();
		add(player);
		applyPhysics(player);

		floor = new Floor(50, 100);
		add(floor);
		applyPhysics(floor);
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	@Override
	protected void input(float dt)
	{

	}

	@Override
	public void render(View view)
	{
		super.render(view);
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
	}
}
