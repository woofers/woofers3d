package com.jaxson.lib.gdx.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jaxson.lib.gdx.bullet.PhysicsWorld;
import com.jaxson.lib.gdx.bullet.bodies.Floor;
import com.jaxson.lib.gdx.bullet.bodies.PlayerBody;
import com.jaxson.lib.gdx.bullet.bodies.RigidBody;
import com.jaxson.lib.gdx.bullet.bodies.SoftBody;
import com.jaxson.lib.gdx.graphics.GameObject;
import com.jaxson.lib.gdx.graphics.cameras.TargetCamera;
import com.jaxson.lib.gdx.graphics.g2d.GdxSprite;
import com.jaxson.lib.gdx.graphics.g3d.Entity;
import com.jaxson.lib.gdx.states.renderables.MixedRenderable;

public abstract class State extends GameObject
{
	private GameManager gameManager;
	private MixedRenderable renderable;
	private PhysicsWorld world;
	private State pauseState;

	public State(GameManager gameManager)
	{
		this.gameManager = gameManager;
		this.renderable = new MixedRenderable();
		this.world = new PhysicsWorld();
	}

	public void add(Entity entity)
	{
		renderable.add(entity);
	}

	public void add(GdxSprite sprite)
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

	public Camera getCamera()
	{
		return gameManager.getCamera();
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

	public TargetCamera getTargetCamera()
	{
		return gameManager.getTargetCamera();
	}

	public Viewport getViewport()
	{
		return gameManager.getViewport();
	}

	public int getWidth()
	{
		return getGraphics().getWidth();
	}

	public boolean hasPauseState()
	{
		return getPauseState() != null;
	}

	public State getPauseState()
	{
		return pauseState;
	}

	@Override
	protected void input()
	{

	}

	public void remove(Entity entity)
	{
		renderable.remove(entity);
	}

	public void remove(GdxSprite sprite)
	{
		renderable.remove(sprite);
	}

	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch)
	{
		renderable.render(spriteBatch, modelBatch, getCamera());
		world.render(spriteBatch, modelBatch, getCamera());
	}

	public void resize(int width, int height)
	{

	}

	public void setPauseState(State pauseState)
	{
		this.pauseState = pauseState;
	}

	public void setCamera(Camera camera)
	{
		gameManager.setCamera(camera);
	}

	public void setViewport(Viewport viewport)
	{
		gameManager.setViewport(viewport);
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
		world.update(dt);
		renderable.update(dt);
	}
}
