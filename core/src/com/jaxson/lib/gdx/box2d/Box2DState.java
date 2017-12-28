package com.jaxson.lib.gdx.box2d;

import com.jaxson.lib.gdx.backend.Game;
import com.jaxson.lib.gdx.box2d.bodies.types.SpriteBody;
import com.jaxson.lib.gdx.box2d.simulation.Box2DWorld;
import com.jaxson.lib.gdx.graphics.views.View;
import com.jaxson.lib.gdx.states.State;

public abstract class Box2DState extends State
{
    private Box2DWorld world;

    public Box2DState(Game game)
    {
        super(game);
        this.world = new Box2DWorld();
    }

    public void applyPhysics(SpriteBody sprite)
    {
        world.add(sprite);
    }

    @Override
    public void dispose()
    {
        world.dispose();
        super.dispose();
    }

    public Box2DWorld physicsWorld()
    {
        return world;
    }

    public void removePhysics(SpriteBody sprite)
    {
        world.remove(sprite);
    }

    @Override
    public void render(View view)
    {
        super.render(view);
        world.render(view);
    }

    @Override
    public void update(float dt)
    {
        super.update(dt);
        world.update(dt);
    }
}
