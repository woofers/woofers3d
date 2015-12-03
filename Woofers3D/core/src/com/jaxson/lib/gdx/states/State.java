package com.jaxson.lib.gdx.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.jaxson.lib.gdx.entities.Entity;
import com.jaxson.lib.gdx.graphics.MyEnvironment;
import com.jaxson.lib.gdx.sprites.Sprite;
import com.jaxson.lib.gdx.states.GameStateManager;
import com.jaxson.lib.gdx.util.MyInputProcessor;
import com.jaxson.lib.util.MyArrayList;

public abstract class State<C extends Camera>
{
	private C camera;
	private GameStateManager gameStateManager;
	private InputProcessor input;
	private MyArrayList<Entity> entities;
	private MyArrayList<Sprite> sprites;
	private Environment environment;

	public State(GameStateManager gameStateManager, C camera)
	{
		this.gameStateManager = gameStateManager;
		this.camera = camera;
		this.entities = new MyArrayList<Entity>();
		this.sprites = new MyArrayList<Sprite>();
		this.environment = new MyEnvironment();

		setInputProcessor(new MyInputProcessor());
	}

	public void add(Entity entity)
	{
		entities.add(entity);
	}

	public void add(Sprite sprite)
	{
		sprites.add(sprite);
	}

	public void dispose()
	{
		for (Entity entity: entities)
		{
			entity.dispose();
		}
		for (Sprite sprite: sprites)
		{
			sprite.dispose();
		}
	}

	public C getCamera()
	{
		return camera;
	}

	public int getHeight()
	{
		return Gdx.graphics.getHeight();
	}

	public int getWidth()
	{
		return Gdx.graphics.getWidth();
	}

	public abstract void input();

	public void remove(Entity entity)
	{
		entities.remove(entity);
	}

	public void remove(Sprite sprite)
	{
		sprites.remove(sprite);
	}

	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch)
	{
		render(modelBatch);
		render(spriteBatch);
	}

	public void render(ModelBatch modelBatch)
	{
		if (modelBatch == null || entities.isEmpty()) return;
		modelBatch.begin(camera);
		for (Entity entity: entities)
		{
			modelBatch.render(entity, environment);
		}
		modelBatch.end();
	}

	public void render(SpriteBatch spriteBatch)
	{
		if (spriteBatch == null || sprites.isEmpty()) return;
		Vector2 location;
		spriteBatch.begin();
		for (Sprite sprite: sprites)
		{
			location = sprite.getLocation();
			spriteBatch.draw(sprite, location.x, location.y);
		}
		spriteBatch.end();
	}

	public void setCamera(C camera)
	{
		this.camera = camera;
	}

	public void setInputProcessor(InputProcessor inputProcessor)
	{
		this.input = inputProcessor;
		Gdx.input.setInputProcessor(input);
	}

	public void update(float dt)
	{
		input();
		camera.update();
		for (Entity entity: entities)
		{
			entity.update(dt);
		}
		for (Sprite sprite: sprites)
		{
			sprite.update(dt);
		}
		MyInputProcessor.update(dt);
	}
}
