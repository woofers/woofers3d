package com.jaxson.woofers3d.states;

import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.bullet.bodies.Floor;
import com.jaxson.lib.gdx.bullet.bodies.RigidBox;
import com.jaxson.lib.gdx.bullet.bodies.SoftBox;
import com.jaxson.lib.gdx.graphics.cameras.TargetCamera;
import com.jaxson.lib.gdx.graphics.g3d.Box;
import com.jaxson.lib.gdx.states.State;
import com.jaxson.lib.gdx.util.GdxMath;
import com.jaxson.lib.util.MyMath;
import com.jaxson.woofers3d.entities.Player;
import com.badlogic.gdx.utils.viewport.FillViewport;


public class PlayState extends State<TargetCamera>
{
	private static final int BOX_AMOUNT = 100;

	private FPSLogger fps;
	private Floor floor;
	private RigidBox[] boxs;
	private SoftBox softBox;
	private Box ghost;
	private Player player;

	public PlayState()
	{
		super();
		setCamera(new TargetCamera(getWidth(), getHeight()));
		setViewport(new FillViewport(getWidth(), getHeight(), getCamera()));

		fps = new FPSLogger();

		floor = new Floor();
		applyPhysics(floor);
		add(floor);

		boxs = new RigidBox[BOX_AMOUNT];
		for (int i = 0; i < BOX_AMOUNT; i++)
		{
			boxs[i] = new RigidBox(GdxMath.randColor());
			boxs[i].setLocation(new Vector3(10f, 15f, 0));
			boxs[i].setSize(new Vector3(MyMath.randFloat(1f, 4f), MyMath.randFloat(1f, 4f), MyMath.randFloat(1f, 4f)));
			boxs[i].setMass(0.0001f);
			applyPhysics(boxs[i]);
			add(boxs[i]);
		}

		softBox = new SoftBox(getPhysicsWorld());
		applyPhysics(softBox);
		add(softBox);

		player = new Player(getCamera());
		// player.setCollisionShape(player.getFittedHitbox());
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
	}
}
