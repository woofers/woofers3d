package com.jaxson.lib.gdx.backend.renderer;

import com.jaxson.lib.gdx.graphics.g2d.Sprite;
import com.jaxson.lib.gdx.graphics.g3d.entities.types.Entity;
import com.jaxson.lib.gdx.graphics.g3d.environment.MyEnvironment;
import com.jaxson.lib.gdx.graphics.views.View;
import com.jaxson.lib.gdx.util.GameObject;

public class MixedRenderer extends GameObject
{
	private ModelRenderer modelRenderer;
	private SpriteRenderer spriteRenderer;
	private HudRenderer hudRenderer;

	public MixedRenderer(View view)
	{
		this.modelRenderer = new ModelRenderer();
		this.spriteRenderer = new SpriteRenderer();
		this.hudRenderer = new HudRenderer();
	}

	public void add(Entity model)
	{
		modelRenderer.add(model);
	}

	public void add(Sprite sprite)
	{
		spriteRenderer.add(sprite);
	}

	public void addHud(Sprite sprite)
	{
		hudRenderer.add(sprite);
	}

	@Override
	public void dispose()
	{
		modelRenderer.dispose();
		spriteRenderer.dispose();
		hudRenderer.dispose();
	}

	public MyEnvironment getEnvironment()
	{
		return modelRenderer.getEnvironment();
	}

	public boolean isEmpty()
	{
		return spriteRenderer.isEmpty() && modelRenderer.isEmpty() && hudRenderer.isEmpty();
	}

	public void remove(Entity model)
	{
		modelRenderer.remove(model);
	}

	public void remove(Sprite sprite)
	{
		spriteRenderer.add(sprite);
	}

	public void removeHud(Sprite sprite)
	{
		hudRenderer.add(sprite);
	}

	@Override
	public void render(View view)
	{
		modelRenderer.render(view);
		spriteRenderer.render(view);
		hudRenderer.render(view);
	}

	@Override
	public void resize(int width, int height)
	{
		modelRenderer.resize(width, height);
		spriteRenderer.resize(width, height);
		hudRenderer.resize(width, height);
	}

	@Override
	public void update(float dt)
	{
		modelRenderer.update(dt);
		spriteRenderer.update(dt);
		hudRenderer.update(dt);
	}
}
