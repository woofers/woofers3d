package com.jaxson.woofers3d.states;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.World;
import com.jaxson.lib.gdx.backend.Game;
import com.jaxson.lib.gdx.box2d.Box2DState;
import com.jaxson.lib.gdx.box2d.bodies.Floor;
import com.jaxson.lib.gdx.graphics.views.View;
import com.jaxson.woofers3d.entities.g2d.Player;
import com.jaxson.lib.gdx.graphics.color.MyColor;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class FlatState extends Box2DState
{
    private Camera camera;
    private World world;
    private Player player;
    private Floor floor;
    private Floor floor2;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    public FlatState(Game game)
    {
        super(game);
        setSubState(new PauseState(game));

        camera = view().spriteView().getCamera();

        player = new Player();
        add(player);
        applyPhysics(player);

        floor2 = new Floor(6f, 3.1f, new MyColor(237, 125, 7));
        add(floor2);
        applyPhysics(floor2);

        floor = new Floor(2f, 3f, new MyColor(237, 175, 7));
        floor.scale(new Vector2(0.1f, 2f));
        add(floor);
        applyPhysics(floor);

        map = new TmxMapLoader().load("levels/2d/sample.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
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
        mapRenderer.setView((OrthographicCamera)view.spriteView().getCamera());
        mapRenderer.render();
    }

    @Override
    public void update(float dt)
    {
        super.update(dt);
    }
}
