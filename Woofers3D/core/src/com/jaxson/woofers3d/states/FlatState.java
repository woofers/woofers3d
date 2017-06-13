package com.jaxson.woofers3d.states;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.jaxson.lib.gdx.backend.Game;
import com.jaxson.lib.gdx.bullet.BulletState;
import com.jaxson.lib.gdx.bullet.simulation.bodies.Floor;
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

public class FlatState extends BulletState
{
	private Camera camera;
	private Player player;

	public FlatState(Game game)
	{
		super(game);
		setSubState(new PauseState(game));

		camera = view().spriteView().getCamera();

		player = new Player();
		add(player);


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
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
	}
}
