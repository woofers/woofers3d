package com.jaxson.lib.gdx.backend.objects;

import com.jaxson.lib.gdx.graphics.g2d.Sprite;
import com.jaxson.lib.gdx.graphics.views.View;

public class HudSprites extends ObjectsBase<Sprite>
{
	public HudSprites()
	{
		super();
	}

	@Override
	public void render(View view)
	{
		if (isEmpty()) return;
		view.getSpriteBatch().setProjectionMatrix(view.getHudView()
				.getCamera().combined);
		view.getHudView().apply();
		view.getSpriteBatch().begin();
		for (Sprite sprite: getObjects())
		{
			sprite.render(view);
		}
		view.getSpriteBatch().end();
	}
}
