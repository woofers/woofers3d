package com.jaxson.woofers3d.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.jaxson.lib.gdx.bullet.bodies.EntityBody;
import com.jaxson.lib.gdx.bullet.bodies.Floor;
import com.jaxson.lib.gdx.bullet.bodies.RigidBox;
import com.jaxson.lib.gdx.graphics.cameras.TargetCamera;
import com.jaxson.lib.gdx.graphics.g3d.Box;
import com.jaxson.lib.gdx.graphics.MyColor;
import com.jaxson.lib.gdx.input.KeyHandler;
import com.jaxson.lib.gdx.states.GameStateManager;
import com.jaxson.lib.gdx.states.State;
import com.jaxson.lib.util.MyMath;
import com.jaxson.woofers3d.entities.Player;

public class PlayState extends State<TargetCamera>
{
	private FPSLogger fps;
	private Floor floor;
	private RigidBox box;
	private Player player;

	public PlayState(GameStateManager gameStateManager)
	{
		super(gameStateManager);
		setCamera(new TargetCamera(getWidth(), getHeight()));

		fps = new FPSLogger();

		floor = new Floor();
		applyPhysics(floor);
		add(floor);

		//box = new RigidBox();
		//box.setScale(new Vector3(4f, 2f, 2f));
		//box.setLocation(new Vector3(10f, 10f, 10f));
		//applyPhysics(box);
		//add(box);

		player = new Player(getCamera());
		//player.setScale(new Vector3(1, 5, 2));
		applyPhysics(player);
		add(player);

		getCamera().setWorld(getPhysicsWorld());
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
		fps.log();
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
		//EntityBody<?> entity = null;
		//entity = getPhysicsWorld().getBody(getCamera().getRay());
		//System.out.println(entity);
		//if (entity == floor) System.out.println("Woof");
	}
}