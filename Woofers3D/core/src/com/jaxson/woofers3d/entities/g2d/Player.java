package com.jaxson.woofers3d.entities.g2d;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.jaxson.lib.gdx.box2d.bodies.types.SpriteBody;
import com.jaxson.lib.gdx.input.Inputs;
import com.jaxson.lib.gdx.input.Keyboard;
import com.jaxson.lib.gdx.input.KeyboardKey;
import com.jaxson.lib.gdx.io.GdxFile;
import com.jaxson.lib.gdx.io.TextureFromFile;

public class Player extends SpriteBody
{
	private static final String PATH = "icon.png";
	private static final float SCALE = 3.7f;
	private static final int SPEED = 2;

	private Keyboard keyboard;
	private KeyboardKey forwardKey;
	private KeyboardKey backwardKey;
	private KeyboardKey leftKey;
	private KeyboardKey rightKey;
	private KeyboardKey jumpKey;

	public Player()
	{
		super(new TextureFromFile(new GdxFile(PATH)),
				BodyDef.BodyType.DynamicBody,
				1f);
		setScale(SCALE);
		setLocation(4, 5);

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
		if (leftKey.isDown()) translateX(-SPEED);
		if (rightKey.isDown()) translateX(SPEED);
	}
}
