package com.jaxson.woofers3d.states;

import com.badlogic.gdx.math.Vector2;
import com.jaxson.lib.gdx.backend.Game;
import com.jaxson.lib.gdx.graphics.g2d.entities.types.SpriteActor;
import com.jaxson.lib.gdx.graphics.views.View;
import com.jaxson.lib.gdx.io.GdxFile;
import com.jaxson.lib.gdx.io.TextureFromFile;
import com.jaxson.lib.gdx.states.SubState;

public class PauseState extends SubState
{
    private static final String PAUSE_SCREEN_PATH = "pauseScreen.png";
    private static final float PAUSE_ALPHA = 3f / 4f;
    private static final float RES_SCALE = 720f / 1080f;

    private SpriteActor image;

    public PauseState(Game game)
    {
        super(game);
        image = new SpriteActor(
                new TextureFromFile(
                        new GdxFile(PAUSE_SCREEN_PATH)));

        image.moveTo(
                new Vector2(
                        image.location().x,
                        game().display().center().y
                                - image.height() / 2));

        image.setAlpha(PAUSE_ALPHA);
        image.scale(RES_SCALE);
        add(image);
    }

    @Override
    public void dispose()
    {
        super.dispose();
    }

    @Override
    protected void input(float dt)
    {

    }

    @Override
    public void render(View view)
    {
        super.render(view);
    }

    @Override
    public void update(float dt)
    {
        super.update(dt);
    }
}
