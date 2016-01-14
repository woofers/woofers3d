package com.jaxson.woofers3d.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.backend.GameManager;
import com.jaxson.lib.gdx.backend.State;
import com.jaxson.lib.gdx.bullet.bodies.Floor;
import com.jaxson.lib.gdx.bullet.bodies.RigidBox;
import com.jaxson.lib.gdx.bullet.bodies.SoftBox;
import com.jaxson.lib.gdx.util.GdxMath;
import com.jaxson.lib.util.MyMath;
import com.jaxson.woofers3d.entities.Player;
import com.jaxson.lib.gdx.bullet.bodies.RigidBody;
import com.jaxson.lib.gdx.bullet.bodies.EntityBody;
import com.badlogic.gdx.math.collision.Ray;
import com.jaxson.lib.gdx.input.InputHandler;

public class PlayState extends State
{
	private static final int BOX_AMOUNT = 25;

	private Floor floor;
	private RigidBox[] boxs;
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
			boxs[i] = new RigidBox(GdxMath.randColor());
			boxs[i].setLocation(GdxMath.randVector3(6f, 30f));
			boxs[i].setSize(new Vector3(GdxMath.randFloat(1f, 4f), GdxMath.randFloat(1f, 2f), GdxMath.randFloat(1f, 4f)));
			boxs[i].setMass(0.0001f);
			applyPhysics(boxs[i]);
			add(boxs[i]);
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
		//if (InputHandler.hasTouchScreen())
		//{
			if (InputHandler.justTouched())
			{
				Ray ray = getCamera().getPickRay(InputHandler.getMouseX(), InputHandler.getMouseY());
				EntityBody<?> body = getPhysicsWorld().getBody(ray);
				System.out.println(body);
				if (body instanceof RigidBody)
				{
					RigidBody rigidBody = (RigidBody)(body);
					rigidBody.applyCentralImpulse(ray.direction.cpy().scl(20f));
				}
			}
		//}
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
