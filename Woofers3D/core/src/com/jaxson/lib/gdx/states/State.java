package com.jaxson.lib.gdx.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector2;
import com.jaxson.lib.gdx.bullet.PhysicsWorld;
import com.jaxson.lib.gdx.bullet.bodies.Floor;
import com.jaxson.lib.gdx.bullet.bodies.PlayerBody;
import com.jaxson.lib.gdx.bullet.bodies.RigidBody;
import com.jaxson.lib.gdx.graphics.GameObject;
import com.jaxson.lib.gdx.graphics.g2d.Sprite;
import com.jaxson.lib.gdx.graphics.g3d.Entity;
import com.jaxson.lib.gdx.input.KeyHandler;
import com.jaxson.lib.gdx.states.renderables.MixedRenderable;

public abstract class State<C extends Camera> extends GameObject
{
	private static final boolean CURSOR_CATCHED = true;

	private C camera;
	private GameStateManager gameStateManager;
	private InputProcessor input;
	private MixedRenderable renderable;
	private PhysicsWorld world;

	public State(GameStateManager gameStateManager)
	{
		this(gameStateManager, null);
	}

	public State(GameStateManager gameStateManager, C camera)
	{
		this.gameStateManager = gameStateManager;
		this.camera = camera;
		this.renderable = new MixedRenderable();
		this.world = new PhysicsWorld();

		setCursorCatched(CURSOR_CATCHED);
		setInputProcessor(new KeyHandler());
	}

	public void add(Entity entity)
	{
		renderable.add(entity);
	}

	public void add(Sprite sprite)
	{
		renderable.add(sprite);
	}

	public void applyPhysics(PlayerBody entity)
	{
		world.add(entity);
	}

	public void applyPhysics(RigidBody entity)
	{
		world.add(entity);
	}

	public void applyPhysics(Floor entity)
	{
		world.add(entity);
	}

	@Override
	public void dispose()
	{
		renderable.dispose();
	}

	public float getAspectRatio()
	{
		return (float) (getWidth()) / (float) (getHeight());
	}

	public C getCamera()
	{
		return camera;
	}

	public Vector2 getCenter()
	{
		return new Vector2(getWidth() / 2, getHeight() / 2);
	}

	public int getHeight()
	{
		return Gdx.graphics.getHeight();
	}

	public int getWidth()
	{
		return Gdx.graphics.getWidth();
	}

	public PhysicsWorld getPhysicsWorld()
	{
		return world;
	}

	public boolean isCursorCatched()
	{
		return Gdx.input.isCursorCatched();
	}

	public void remove(Entity entity)
	{
		renderable.remove(entity);
	}

	public void remove(Sprite sprite)
	{
		renderable.remove(sprite);
	}

	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch)
	{
		renderable.render(spriteBatch, modelBatch, camera);
		world.render(spriteBatch, modelBatch, camera);
	}

	public void setCamera(C camera)
	{
		this.camera = camera;
	}

	public void setCursorCatched(boolean catched)
	{
		Gdx.input.setCursorCatched(catched);
	}

	public void setInputProcessor(InputProcessor inputProcessor)
	{
		this.input = inputProcessor;
		Gdx.input.setInputProcessor(input);
	}

	public void toggleCursorCatched()
	{
		setCursorCatched(!isCursorCatched());
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
		world.update(dt);
		renderable.update(dt);
		camera.update();
		KeyHandler.update(dt);
	}
}
