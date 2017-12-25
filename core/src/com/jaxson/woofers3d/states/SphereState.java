package com.jaxson.woofers3d.states;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.jaxson.lib.gdx.backend.Game;
import com.jaxson.lib.gdx.bullet.BulletState;
import com.jaxson.lib.gdx.bullet.simulation.bodies.Floor;
import com.jaxson.lib.gdx.bullet.simulation.bodies.RigidBox;
import com.jaxson.lib.gdx.bullet.simulation.bodies.RigidSphere;
import com.jaxson.lib.gdx.bullet.simulation.bodies.SoftBox;
import com.jaxson.lib.gdx.bullet.simulation.bodies.types.EntityBody;
import com.jaxson.lib.gdx.bullet.simulation.bodies.types.RigidBody;
import com.jaxson.lib.gdx.graphics.color.MyColor;
import com.jaxson.lib.gdx.graphics.color.RandomColor;
import com.jaxson.lib.gdx.graphics.g2d.entities.FPSCounter;
import com.jaxson.lib.gdx.graphics.g2d.entities.Text;
import com.jaxson.lib.gdx.graphics.views.TargetCamera;
import com.jaxson.lib.gdx.graphics.views.View;
import com.jaxson.lib.gdx.input.Inputs;
import com.jaxson.lib.gdx.input.Mouse;
import com.jaxson.lib.gdx.math.random.RandomVector3;
import com.jaxson.lib.math.random.RandomNumber;
import com.jaxson.lib.util.Optional;
import com.jaxson.woofers3d.entities.g3d.SpherePlayer;

public class SphereState extends BulletState
{
    private static final int BOX_AMOUNT = 25;
    private static final int TEST_AMOUNT = 5;
    private static final int SPHERE_AMOUNT = 25;
    private static final float IMPULSE_SPEED = 1.3f;
    private static final float MARKER_LENGTH = 35f;

    private static final float SCALE_TEST = 1f;

    private Floor floor;
    private Floor ramp;
    private RigidBox blocker;
    private RigidBox[] boxs;
    private RigidSphere[] spheres;
    private SoftBox softBox;
    private SpherePlayer player;
    private TargetCamera camera;
    private Text text;
    private float counter;
    private Mouse mouse;

    public SphereState(Game game)
    {
        super(game);
        setSubState(new PauseState(game));

        camera = new TargetCamera(width(), height());
        applyPhysics(camera);
        view().modelView().setCamera(camera);

        floor = new Floor();
        applyPhysics(floor);
        add(floor);

        player = new SpherePlayer(camera);
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
