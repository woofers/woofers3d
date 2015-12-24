package com.jaxson.lib.gdx.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.jaxson.lib.gdx.bullet.PhysicsWorld;
import com.jaxson.lib.gdx.bullet.bodies.Floor;
import com.jaxson.lib.gdx.bullet.bodies.PlayerBody;
import com.jaxson.lib.gdx.bullet.bodies.RigidBody;
import com.jaxson.lib.gdx.bullet.bodies.SoftBody;
import com.jaxson.lib.gdx.graphics.GameObject;
import com.jaxson.lib.gdx.graphics.g2d.Sprite;
import com.jaxson.lib.gdx.graphics.g3d.Entity;
import com.jaxson.lib.gdx.input.KeyHandler;
import com.jaxson.lib.gdx.states.renderables.MixedRenderable;

public abstract class State<C extends Camera> extends GameObject
{
	private C camera;
	private GameManager gameManager;
	private MixedRenderable renderable;
	private PhysicsWorld world;

	public State()
	{
		this(null);
	}

	public State(C camera)
	{
		this.camera = camera;
		this.renderable = new MixedRenderable();
		this.world = new PhysicsWorld();
	}

	public void add(Entity entity)
	{
		renderable.add(entity);
	}

	public void add(Sprite sprite)
	{
		renderable.add(sprite);
	}

	public void applyPhysics(Floor entity)
	{
		world.add(entity);
	}

	public void applyPhysics(PlayerBody entity)
	{
		world.add(entity);
	}

	public void applyPhysics(RigidBody entity)
	{
		world.add(entity);
	}

	public void applyPhysics(SoftBody entity)
	{
		world.add(entity);
	}

	@Override
	public void dispose()
	{
		renderable.dispose();
	}

	public C getCamera()
	{
		return camera;
	}

	private Graphics getGraphics()
	{
		return Gdx.graphics;
	}

	public int getHeight()
	{
		return getGraphics().getHeight();
	}

	private Input getInput()
	{
		return Gdx.input;
	}

	public PhysicsWorld getPhysicsWorld()
	{
		return world;
	}

	public int getWidth()
	{
		return getGraphics().getWidth();
	}

	@Override
	protected void input()
	{

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

	public void setGameManager(GameManager gameManager)
	{
		this.gameManager = gameManager;
	}

	public void setInputProcessor(InputProcessor inputProcessor)
	{
		getInput().setInputProcessor(inputProcessor);
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
		world.update(dt);
		renderable.update(dt);
		camera.update();
	}
}
