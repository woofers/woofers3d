package com.jaxson.woofers3d.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.entities.Box;
import com.jaxson.lib.gdx.graphics.MyPerspectiveCamera;
import com.jaxson.lib.gdx.states.GameStateManager;
import com.jaxson.lib.gdx.states.State;
import com.jaxson.lib.gdx.util.MyInputProcessor;
import com.jaxson.woofers3d.entities.Player;

public class PlayState extends State<MyPerspectiveCamera>
{
	private FPSLogger fps;
	private Box[] boxs;
	private Player player;

	private static final int BOX_SIZE = 3;

	public PlayState(GameStateManager gameStateManager)
	{
		super(gameStateManager);
		setCamera(new MyPerspectiveCamera(getWidth(), getHeight()));

		fps = new FPSLogger();

		boxs = new Box[BOX_SIZE];

		boxs[0] = new Box();
		//add(boxs[0]);

		boxs[1] = new Box(Color.GRAY, new Vector3(2f, 2f, 2f), new Vector3(0f, 0f, 5f));
		add(boxs[1]);

		boxs[2] = new Box(Color.GREEN);
		add(boxs[2]);

		player = new Player(getCamera());
		add(player);
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
		//fps.log();
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
		boxs[2].transform.set(player.getDirection(), new Quaternion());
		System.out.println(getCamera().direction);
	}
}