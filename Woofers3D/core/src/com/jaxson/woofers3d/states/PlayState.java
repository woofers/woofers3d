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
import com.jaxson.lib.gdx.entities.Box;
import com.jaxson.lib.gdx.entities.CollisionManager;
import com.jaxson.lib.gdx.graphics.MyPerspectiveCamera;
import com.jaxson.lib.gdx.states.GameStateManager;
import com.jaxson.lib.gdx.states.State;
import com.jaxson.lib.gdx.util.MyInputProcessor;
import com.jaxson.lib.util.MyMath;
import com.jaxson.woofers3d.entities.Player;

public class PlayState extends State<MyPerspectiveCamera>
{
	private static final Color FLOOR_COLOR = new Color(MyMath.toRGB(81), MyMath.toRGB(101), MyMath.toRGB(107), 1f);

	private FPSLogger fps;
	private Box floor;
	private Box box;
	private Player player;
	private CollisionManager collisionManager;

	private static final int BOX_SIZE = 3;

	public PlayState(GameStateManager gameStateManager)
	{
		super(gameStateManager);
		setCamera(new MyPerspectiveCamera(getWidth(), getHeight()));

		Bullet.init();
		collisionManager = new CollisionManager();

		fps = new FPSLogger();

		floor = new Box(FLOOR_COLOR, new Vector3(100f, 0.1f, 100f));
		add(floor);
		collisionManager.addFloor(floor);

		box = new Box();
		add(box);

		player = new Player(getCamera());
		add(player);
		collisionManager.addObject(player);
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