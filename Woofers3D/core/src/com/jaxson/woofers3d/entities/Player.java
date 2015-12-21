package com.jaxson.woofers3d.entities;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btConvexShape;
import com.jaxson.lib.gdx.bullet.bodies.PlayerBody;
import com.jaxson.lib.gdx.bullet.collision.BoxShape;
import com.jaxson.lib.gdx.graphics.cameras.TargetCamera;

public class Player extends PlayerBody
{
	private static final float SPEED = 0.06f;

	private static final String PATH = "entities/dog/dog.g3db";
	private static final float MASS = -1f;
	private static final Vector3 SIZE = new Vector3(0.671983f, 2.223813f, 3.852066f);
	private static final btConvexShape SHAPE = new BoxShape(SIZE);

	private TargetCamera camera;

	public Player(TargetCamera camera)
	{
		super(PATH, SHAPE, MASS);
		this.camera = camera;
		camera.setTarget(this);
	}

	@Override
	public void dispose()
	{
		super.dispose();
		camera.setTarget(null);
	}

	@Override
	protected void input()
	{
		super.input();
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
	}
}
