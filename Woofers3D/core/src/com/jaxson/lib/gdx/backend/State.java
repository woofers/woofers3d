package com.jaxson.lib.gdx.backend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jaxson.lib.gdx.bullet.PhysicsWorld;
import com.jaxson.lib.gdx.bullet.bodies.Floor;
import com.jaxson.lib.gdx.bullet.bodies.PlayerBody;
import com.jaxson.lib.gdx.bullet.bodies.RigidBody;
import com.jaxson.lib.gdx.bullet.bodies.SoftBody;
import com.jaxson.lib.gdx.graphics.cameras.TargetCamera;
import com.jaxson.lib.gdx.graphics.g2d.GdxSprite;
import com.jaxson.lib.gdx.graphics.g3d.Entity;
import com.jaxson.lib.gdx.graphics.g3d.MyEnvironment;
import com.jaxson.lib.gdx.states.renderer.MixedRenderer;
import com.jaxson.lib.gdx.util.GameObject;
import com.jaxson.lib.gdx.graphics.MyColor;

public abstract class State extends GameObject
{
	private GameManager gameManager;
	private MixedRenderer renderer;
	private PhysicsWorld world;
	private State pauseState;

	public State(GameManager gameManager)
	{
		this.gameManager = gameManager;
		this.renderer = new MixedRenderer();
		this.world = new PhysicsWorld();
		getEnvironment().setWorldSize(new Vector3(100f, 0f, 100f));
		getEnvironment().setShawdows(true);
	}

	public void add(Entity entity)
	{
		renderer.add(entity);
	}

	public void add(GdxSprite sprite)
	{
		renderer.add(sprite);
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
		renderer.dispose();
	}

	public Camera getCamera()
	{
		return gameManager.getCamera();
	}

	public MyEnvironment getEnvironment()
	{
		return renderer.getEnvironment();
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

	public State getPauseState()
	{
		return pauseState;
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

	@Override
	protected void input()
	{

	}

	public void remove(Entity entity)
	{
		renderer.remove(entity);
	}

	public void remove(GdxSprite sprite)
	{
		renderer.remove(sprite);
	}

	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch)
	{
		renderer.render(spriteBatch, modelBatch, getCamera());
		world.render(spriteBatch, modelBatch, getCamera());
	}

	public void resize(int width, int height)
	{

	}

	public void setCamera(Camera camera)
	{
		gameManager.setCamera(camera);
		if (camera instanceof TargetCamera) getTargetCamera().setWorld(world);
	}

	public void setPauseState(State pauseState)
	{
		this.pauseState = pauseState;
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
		renderer.update(dt);
	}
}
