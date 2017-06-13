package com.jaxson.woofers3d.entities.g2d;

import com.jaxson.lib.gdx.bullet.simulation.bodies.types.CameraPlayerBody;
import com.jaxson.lib.gdx.graphics.views.TargetCamera;
import com.jaxson.lib.gdx.input.KeyboardKey;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.graphics.g2d.SpriteActor;
import com.jaxson.lib.gdx.input.Keyboard;
import com.jaxson.lib.gdx.input.Inputs;

public class Player extends SpriteActor
{
	private static final String PATH = "icon.png";
	private static final float SCALE = 0.6f;
	private static final int SPEED = 2;

	private Keyboard keyboard;
	private KeyboardKey forwardKey;
	private KeyboardKey backwardKey;
	private KeyboardKey leftKey;
	private KeyboardKey rightKey;
	private KeyboardKey jumpKey;

	public Player()
	{
		super(PATH);
		setScale(5);

		this.keyboard = Inputs.keyboard();
		this.forwardKey = keyboard.key("W");
		this.backwardKey = keyboard.key("S");
		this.leftKey = keyboard.key("A");
		this.rightKey = keyboard.key("D");
		this.jumpKey = keyboard.key("Space");
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	@Override
	protected void input(float dt)
	{
		super.input(dt);
		if (forwardKey.isDown()) translateY(SPEED);
		if (backwardKey.isDown()) translateY(-SPEED);
		if (leftKey.isDown()) translateX(-SPEED);
		if (rightKey.isDown()) translateX(SPEED);
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
		input(dt);
	}
}
