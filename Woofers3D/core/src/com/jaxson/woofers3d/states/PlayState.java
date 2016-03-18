package com.jaxson.woofers3d.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.jaxson.lib.gdx.backend.Game;
import com.jaxson.lib.gdx.bullet.BulletState;
import com.jaxson.lib.gdx.bullet.bodies.EntityBody;
import com.jaxson.lib.gdx.bullet.bodies.Floor;
import com.jaxson.lib.gdx.bullet.bodies.RigidBody;
import com.jaxson.lib.gdx.bullet.bodies.RigidBox;
import com.jaxson.lib.gdx.bullet.bodies.RigidSphere;
import com.jaxson.lib.gdx.bullet.bodies.SoftBox;
import com.jaxson.lib.gdx.graphics.color.MyColor;
import com.jaxson.lib.gdx.input.InputHandler;
import com.jaxson.lib.gdx.math.GdxMath;
import com.jaxson.lib.math.random.RandomNumber;
import com.jaxson.woofers3d.entities.Player;
import com.jaxson.lib.io.File;

public class PlayState extends BulletState
{
	private static final int BOX_AMOUNT = 25;
	private static final int SPHERE_AMOUNT = 25;
	private static final float IMPULSE_SPEED = 45f;

	private Floor floor;
	private RigidBox[] boxs;
	private RigidSphere[] spheres;
	private SoftBox softBox;
	private Player player;

	public PlayState(Game gameManager)
	{
		super(gameManager);
		setSubState(new PauseState(gameManager));
		getTargetCamera().setWorld(getPhysicsWorld());

		floor = new Floor();
		applyPhysics(floor);
		add(floor);

		RandomNumber boxSizeRange = new RandomNumber(1, 4);
		RandomNumber massRange = new RandomNumber(0.9f, 1.2f);
		boxs = new RigidBox[BOX_AMOUNT];
		for (int i = 0; i < BOX_AMOUNT; i ++)
		{
			boxs[i] = new RigidBox(new MyColor().random(255, 255, 95, 165, 0, 50));
			boxs[i].setLocation(GdxMath.randVector3(6f, 30f));
			boxs[i].setSize(new Vector3(boxSizeRange.floatValue(), boxSizeRange.floatValue(), boxSizeRange.floatValue()));
			boxs[i].setMass(massRange.floatValue());
			applyPhysics(boxs[i]);
			add(boxs[i]);
		}

		spheres = new RigidSphere[SPHERE_AMOUNT];
		for (int i = 0; i < SPHERE_AMOUNT; i ++)
		{
			if (InputHandler.hasTouchScreen()) break;
			spheres[i] = new RigidSphere(new MyColor().random());
			spheres[i].setLocation(GdxMath.randVector3(6f, 30f));
			spheres[i].setSize(new Vector3(2f, 2f, 2f));
			spheres[i].setMass(massRange.floatValue());
			applyPhysics(spheres[i]);
			add(spheres[i]);
		}

		softBox = new SoftBox(getPhysicsWorld());
		applyPhysics(softBox);
		add(softBox);

		player = new Player(getTargetCamera());
		applyPhysics(player);
		add(player);

		File file = new File("");
		file.createDirectory();
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	@Override
	protected void input(float dt)
	{
		if (InputHandler.justTouched())
		{
			Ray ray = player.getForwardRay();
			if (InputHandler.hasTouchScreen()) ray = getCamera().getPickRay(InputHandler.getMouseX(), InputHandler.getMouseY());
			EntityBody<?> body = getPhysicsWorld().getBody(ray);
			if (body instanceof RigidBody)
			{
				RigidBody rigidBody = (RigidBody) body;
				rigidBody.applyCentralImpulse(ray.direction.scl(IMPULSE_SPEED));
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
