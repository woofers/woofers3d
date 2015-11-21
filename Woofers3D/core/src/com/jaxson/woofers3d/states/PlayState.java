package com.jaxson.woofers3d.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.jaxson.woofers3d.states.GameStateManager;
import com.jaxson.woofers3d.states.State3D;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;

public class PlayState extends State3D
{
	protected Model box;
	protected ModelInstance modelInstance;

	public PlayState(GameStateManager gameStateManager)
	{
		super(gameStateManager);

        ModelBuilder modelBuilder = new ModelBuilder();
        Material material = new Material(ColorAttribute.createDiffuse(Color.ORANGE));
        box = modelBuilder.createBox(2f, 2f, 2f, material, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        modelInstance = new ModelInstance(box, 0, 0, 0);
	}

	@Override
	public void dispose()
	{
		box.dispose();
	}

	@Override
	protected void handleInput()
	{

	}

	@Override
	public void render(ModelBatch modelBatch)
	{
		camera.update();
        modelBatch.begin(camera);
        modelBatch.render(modelInstance, environment);
        modelBatch.end();
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
	}
}