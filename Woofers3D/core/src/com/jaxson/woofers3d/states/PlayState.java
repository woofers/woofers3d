package com.jaxson.woofers3d.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.jaxson.lib.gdx.graphics.cameras.TargetCamera;
import com.jaxson.lib.gdx.graphics.g3d.Box;
import com.jaxson.lib.gdx.graphics.MyColor;
import com.jaxson.lib.gdx.input.KeyHandler;
import com.jaxson.lib.gdx.math.collision.CollisionManager;
import com.jaxson.lib.gdx.states.GameStateManager;
import com.jaxson.lib.gdx.states.State;
import com.jaxson.lib.util.MyMath;
import com.jaxson.woofers3d.entities.Player;

public class PlayState extends State<TargetCamera>
{
	private static final Color FLOOR_COLOR = new MyColor(81, 101, 107);

	private FPSLogger fps;
	private Box floor;
	private Box box;
	private Player player;
	private CollisionManager collisionManager;

	public PlayState(GameStateManager gameStateManager)
	{
		super(gameStateManager);
		setCamera(new TargetCamera(getWidth(), getHeight()));

		collisionManager = new CollisionManager();

		fps = new FPSLogger();

		floor = new Box(FLOOR_COLOR);
		floor.setScale(new Vector3(100f, 0.1f, 100f));
		add(floor);
		collisionManager.addFloor(floor);

		box = new Box();
		add(box);

		player = new Player(getCamera());
		player.setLocation(new Vector3(0, 20, 0));
		add(player);
		collisionManager.add(player);
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	@Override
	protected void input()
	{

	}

	@Override
	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch)
	{
		super.render(spriteBatch, modelBatch);
		collisionManager.render(spriteBatch, modelBatch, getCamera());
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
		collisionManager.update(dt);
	}
}