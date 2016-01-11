package com.jaxson.woofers3d.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector2;
import com.jaxson.lib.gdx.bullet.bodies.Floor;
import com.jaxson.lib.gdx.bullet.bodies.RigidBox;
import com.jaxson.lib.gdx.bullet.bodies.SoftBox;
import com.jaxson.lib.gdx.graphics.g3d.Box;
import com.jaxson.lib.gdx.states.GameManager;
import com.jaxson.lib.gdx.states.State;
import com.jaxson.lib.gdx.util.GdxMath;
import com.jaxson.lib.util.MyMath;
import com.jaxson.woofers3d.entities.Player;
import com.jaxson.lib.gdx.graphics.g2d.GdxSprite;

public class PauseState extends State
{
	private GdxSprite image;

	public PauseState(GameManager gameManager)
	{
		super(gameManager);

		image = new GdxSprite("test.png");
		image.setLocation(new Vector2(500, 500));
		add(image);
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
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
		System.out.println("woof");
	}
}
