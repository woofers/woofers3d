package com.jaxson.woofers3d.states;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;
import com.jaxson.lib.gdx.backend.Game;
import com.jaxson.lib.gdx.bullet.BulletState;
import com.jaxson.lib.gdx.box2d.Floor;
import com.jaxson.lib.gdx.bullet.simulation.bodies.RigidBox;
import com.jaxson.lib.gdx.bullet.simulation.bodies.RigidSphere;
import com.jaxson.lib.gdx.bullet.simulation.bodies.SoftBox;
import com.jaxson.lib.gdx.bullet.simulation.bodies.types.EntityBody;
import com.jaxson.lib.gdx.bullet.simulation.bodies.types.RigidBody;
import com.jaxson.lib.gdx.graphics.color.MyColor;
import com.jaxson.lib.gdx.graphics.color.RandomColor;
import com.jaxson.lib.gdx.graphics.g2d.FPSCounter;
import com.jaxson.lib.gdx.graphics.g2d.SpriteActor;
import com.jaxson.lib.gdx.graphics.g2d.Text;
import com.jaxson.lib.gdx.graphics.views.TargetCamera;
import com.jaxson.lib.gdx.graphics.views.View;
import com.jaxson.lib.gdx.input.Inputs;
import com.jaxson.lib.gdx.input.Mouse;
import com.jaxson.lib.gdx.math.random.RandomVector3;
import com.jaxson.lib.math.random.RandomNumber;
import com.jaxson.lib.util.Optional;
import com.jaxson.woofers3d.entities.g2d.Player;
import com.jaxson.lib.gdx.io.GdxFile;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class FlatState extends BulletState
{
	private Camera camera;
	private World world;
	private Player player;
	private Floor floor;
	private Box2DDebugRenderer debugRenderer;

	public FlatState(Game game)
	{
		super(game);
		setSubState(new PauseState(game));

		camera = view().spriteView().getCamera();

		world = new World(new Vector2(0f, -98f), true);

		player = new Player();
		player.createBodies(world);
		add(player);

		floor = new Floor(200, 100);
		floor.createBodies(world);
		add(floor);

		debugRenderer = new Box2DDebugRenderer();
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	@Override
	protected void input(float dt)
	{

	}

	@Override
	public void render(View view)
	{
		super.render(view);
		debugRenderer.render(world,  view.spriteBatch().getProjectionMatrix());
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
		world.step(dt, 6, 2);
	}
}
