package com.jaxson.woofers3d.states;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.bullet.bodies.Floor;
import com.jaxson.lib.gdx.bullet.bodies.RigidBox;
import com.jaxson.lib.gdx.graphics.cameras.TargetCamera;
import com.jaxson.lib.gdx.graphics.g3d.Box;
import com.jaxson.lib.gdx.states.GameStateManager;
import com.jaxson.lib.gdx.states.State;
import com.jaxson.lib.util.MyMath;
import com.jaxson.woofers3d.entities.Player;

public class PlayState extends State<TargetCamera>
{
	private static final int BOX_AMOUNT = 100;

	private FPSLogger fps;
	private Floor floor;
	private RigidBox[] boxs;
	private Box ghost;
	private Player player;

	public PlayState(GameStateManager gameStateManager)
	{
		super(gameStateManager);
		setCamera(new TargetCamera(getWidth(), getHeight()));

		fps = new FPSLogger();

		floor = new Floor();
		applyPhysics(floor);
		add(floor);

		boxs = new RigidBox[BOX_AMOUNT];
		for (int i = 0; i < BOX_AMOUNT; i++)
		{
			boxs[i] = new RigidBox(Color.ORANGE);
			boxs[i].setLocation(new Vector3(10f, 15f, 0));
			boxs[i].setSize(new Vector3(MyMath.randFloat(1f, 4f), MyMath.randFloat(1f, 4f), MyMath.randFloat(1f, 4f)));
			boxs[i].setMass(0.0001f);
			applyPhysics(boxs[i]);
			add(boxs[i]);
		}

		player = new Player(getCamera());
		applyPhysics(player);
		add(player);
		System.out.println(player.getSize());

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
		// EntityBody<?> entity = null;
		// entity = getPhysicsWorld().getBody(getCamera().getRay());
		// System.out.println(entity);
		// if (entity == floor) System.out.println("Woof");
	}
}
