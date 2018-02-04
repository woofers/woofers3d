package com.jaxson.woofers3d.states;

import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.backend.Game;
import com.jaxson.lib.gdx.bullet.BulletState;
import com.jaxson.lib.gdx.bullet.simulation.bodies.Floor;
import com.jaxson.lib.gdx.graphics.g2d.entities.FPSCounter;
import com.jaxson.lib.gdx.graphics.views.TargetCamera;
import com.jaxson.lib.gdx.graphics.views.View;
import com.jaxson.lib.gdx.input.Inputs;
import com.jaxson.lib.gdx.input.Mouse;
import com.jaxson.woofers3d.entities.g3d.SpherePlayer;
import com.jaxson.lib.gdx.graphics.color.MyColor;

public class SphereState extends BulletState
{
    private static final int BOX_AMOUNT = 25;
    private static final int TEST_AMOUNT = 5;
    private static final int SPHERE_AMOUNT = 25;
    private static final float IMPULSE_SPEED = 1.3f;
    private static final float MARKER_LENGTH = 35f;

    private static final float SCALE_TEST = 1f;

    private Floor floor;
    private SpherePlayer player;
    private TargetCamera camera;
    private Mouse mouse;

    public SphereState(Game game)
    {
        super(game);
        setSubState(new PauseState(game));

        game().display().setClearColor(new MyColor(244, 164, 96));

        camera = new TargetCamera(width(), height());
        applyPhysics(camera);
        view().modelView().setCamera(camera);

        floor = new Floor(new MyColor(73, 49, 28));
        applyPhysics(floor);
        add(floor);

        player = new SpherePlayer(camera);
        player.moveTo(new Vector3(0f, 100f, 0f));
        applyPhysics(player);
        add(player);

        addHud(new FPSCounter(game()));

        mouse = Inputs.mouse();
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
