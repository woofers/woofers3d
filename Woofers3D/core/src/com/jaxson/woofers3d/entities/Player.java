package com.jaxson.woofers3d.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCapsuleShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.jaxson.lib.gdx.bullet.bodies.PlayerBody;
import com.jaxson.lib.gdx.bullet.bodies.RigidBody;
import com.jaxson.lib.gdx.graphics.cameras.TargetCamera;
import com.badlogic.gdx.physics.bullet.collision.btConvexShape;
import com.jaxson.lib.gdx.input.KeyHandler;
import java.lang.Math;

public class Player extends PlayerBody
{
	private static final float SPEED = 0.03f;
	private static final String PATH = "entities/dog/dog.obj";
	private static final btConvexShape SHAPE = new btBoxShape(new Vector3(1f, 1f, 1f));
	private static final float MASS = 100f;

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

	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
	}
}
