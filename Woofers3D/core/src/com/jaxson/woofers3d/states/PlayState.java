package com.jaxson.woofers3d.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;
import com.jaxson.lib.gdx.backend.GameManager;
import com.jaxson.lib.gdx.backend.State;
import com.jaxson.lib.gdx.bullet.bodies.EntityBody;
import com.jaxson.lib.gdx.bullet.bodies.Floor;
import com.jaxson.lib.gdx.bullet.bodies.RigidBody;
import com.jaxson.lib.gdx.bullet.bodies.RigidBox;
import com.jaxson.lib.gdx.bullet.bodies.RigidSphere;
import com.jaxson.lib.gdx.bullet.bodies.SoftBox;
import com.jaxson.lib.gdx.input.InputHandler;
import com.jaxson.lib.gdx.util.GdxMath;
import com.jaxson.lib.util.MyMath;
import com.jaxson.woofers3d.entities.Player;
import com.badlogic.gdx.Gdx;

public class PlayState extends State
{
	private static final int BOX_AMOUNT = 50;
	private static final int SPHERE_AMOUNT = 50;
	private static final float IMPULSE_SPEED = 45f;

	private Floor floor;
	private RigidBox[] boxs;
	private RigidSphere[] spheres;
	private SoftBox softBox;
	private Player player;

	public PlayState(GameManager gameManager)
	{
		super(gameManager);
		setPauseState(new PauseState(gameManager));
		getTargetCamera().setWorld(getPhysicsWorld());

		floor = new Floor();
		applyPhysics(floor);
		add(floor);

		boxs = new RigidBox[BOX_AMOUNT];
		for (int i = 0; i < BOX_AMOUNT; i++)
		{
			boxs[i] = new RigidBox(GdxMath.randColor(255, 255, 95, 165, 0, 50));
			boxs[i].setLocation(GdxMath.randVector3(6f, 30f));
			boxs[i].setSize(new Vector3(MyMath.randFloat(1f, 4f), MyMath.randFloat(1f, 3f), MyMath.randFloat(1f, 4f)));
			boxs[i].setMass(GdxMath.randFloat(0.9f, 1.2f));
			applyPhysics(boxs[i]);
			add(boxs[i]);
		}

		spheres = new RigidSphere[BOX_AMOUNT];
		for (int i = 0; i < BOX_AMOUNT; i++)
		{
			if (InputHandler.hasTouchScreen()) break;
			spheres[i] = new RigidSphere(GdxMath.randColor());
			spheres[i].setLocation(GdxMath.randVector3(6f, 30f));
			spheres[i].setSize(new Vector3(2f, 2f, 2f));
			spheres[i].setMass(GdxMath.randFloat(0.9f, 1.2f));
			applyPhysics(spheres[i]);
			add(spheres[i]);
		}

		softBox = new SoftBox(getPhysicsWorld());
		applyPhysics(softBox);
		add(softBox);

		player = new Player(getTargetCamera());
		applyPhysics(player);
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
		if (InputHandler.justTouched())
		{
			Ray ray = new Ray(player.getLocation(), player.getDirection());
			if (InputHandler.hasTouchScreen()) ray = getCamera().getPickRay(InputHandler.getMouseX(), InputHandler.getMouseY());
			EntityBody<?> body = getPhysicsWorld().getBody(ray);
			if (body instanceof RigidBody)
			{
				RigidBody rigidBody = (RigidBody) body;
				rigidBody.applyCentralImpulse(ray.direction.scl(IMPULSE_SPEED));
				System.out.println(rigidBody);
			}
		}
	}

	@Override
	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch)
	{
		super.render(spriteBatch, modelBatch);
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
	}
}
