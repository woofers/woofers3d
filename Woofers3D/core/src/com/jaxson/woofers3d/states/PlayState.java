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
	private static final Color FLOOR_COLOR = new Color(81f / 255f, 101f / 255f, 107f / 255f, 1f);

	private FPSLogger fps;
	private Box box;
	private Box floor;
	private Player player;

	private static final int BOX_SIZE = 3;

	public PlayState(GameStateManager gameStateManager)
	{
		super(gameStateManager);
		setCamera(new MyPerspectiveCamera(getWidth(), getHeight()));

		fps = new FPSLogger();

		floor = new Box(FLOOR_COLOR, new Vector3(100f, 0.1f, 100f));
		add(floor);

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
	}
}