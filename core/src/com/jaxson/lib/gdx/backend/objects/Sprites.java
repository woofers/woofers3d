package com.jaxson.lib.gdx.backend.objects;

import com.jaxson.lib.gdx.graphics.g2d.entities.types.Sprite;
import com.jaxson.lib.gdx.graphics.views.View;

public class Sprites extends ObjectsBase<Sprite>
{
    @Override
    public void render(View view)
    {
        if (isEmpty()) return;
        view.spriteBatch().setProjectionMatrix(
                view.spriteView().getCamera().combined);
        view.spriteView().apply();
        view.spriteBatch().begin();
        for (Sprite sprite: getObjects())
        {
            sprite.render(view);
        }
        view.spriteBatch().end();
    }
}
