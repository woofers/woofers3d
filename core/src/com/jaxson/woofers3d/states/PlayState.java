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
import com.jaxson.woofers3d.entities.g3d.Player;
import com.jaxson.lib.gdx.io.GdxFile;

public class PlayState extends BulletState
{
    private static final int BOX_AMOUNT = 25;
    private static final int TEST_AMOUNT = 5;
    private static final int SPHERE_AMOUNT = 25;
    private static final float IMPULSE_SPEED = 1.3f;
    private static final float MARKER_LENGTH = 35f;

    private static final float SCALE_TEST = 1f;

    private Floor floor;
    private Floor ramp;
    private RigidBody imported;
    private RigidBox blocker;
    private RigidBox[] boxs;
    private RigidSphere[] spheres;
    private SoftBox softBox;
    private Player player;
    private TargetCamera camera;
    private Text text;
    private float counter;
    private Mouse mouse;

    public PlayState(Game game)
    {
        super(game);
        setSubState(new PauseState(game));

        camera = new TargetCamera(width(), height());
        applyPhysics(camera);
        view().modelView().setCamera(camera);

        floor = new Floor();
        applyPhysics(floor);
        add(floor);

        final float IMPORT_SCALE = 0.2f;

        for (RigidBody object: load(new GdxFile("entities/cube/TestCube.g3db")))
        {
            object.scale(IMPORT_SCALE);
            object.moveTo(object.location().scl(IMPORT_SCALE));
        }
/*
        ramp = new Floor(2f, 1f, new MyColor(250, 250, 250));
        ramp.rotate(new Vector3(0f, 0f, 23f));
        ramp.translateABS(new Vector3(1f, 0.4f, 5f));
        applyPhysics(ramp);
        add(ramp);

        blocker = new Floor(1f, 1f, new MyColor(250, 250, 250));
        blocker.translate(new Vector3(3.25f, 0.64f, 5f));
        applyPhysics(blocker);
        add(blocker);

        RandomNumber mass = new RandomNumber(0.135f, 0.18f);
        boxs = new RigidBox[BOX_AMOUNT];
        for (int i = 0; i < BOX_AMOUNT; i ++)
        {
            boxs[i] = new RigidBox(
                    new RandomColor(new MyColor(255, 95, 0),
                            new MyColor(255, 165, 50)));
            boxs[i].setSize(
                    new RandomVector3(0.15f, 0.6f, 0.15f, 0.3f, 0.15f, 0.6f)
                            .scl(SCALE_TEST));
            boxs[i].moveTo(new RandomVector3(0.9f, 2.205f));
            boxs[i].setMass(mass.floatValue());
            applyPhysics(boxs[i]);
            add(boxs[i]);
        }

        if (game().isDesktop())
        {
            spheres = new RigidSphere[SPHERE_AMOUNT];
            for (int i = 0; i < SPHERE_AMOUNT; i ++)
            {
                spheres[i] = new RigidSphere(new RandomColor());
                spheres[i].moveTo(new RandomVector3(0.9f, 2.205f));
                spheres[i]
                        .setSize(new Vector3(0.3f, 0.3f, 0.3f).scl(SCALE_TEST));
                spheres[i].setMass(mass.floatValue());
                applyPhysics(spheres[i]);
                add(spheres[i]);
            }
        }

        softBox = new SoftBox(physicsWorld());
        applyPhysics(softBox);
        add(softBox);
*/
        player = new Player(camera);
        applyPhysics(player);
        add(player);

        addHud(new FPSCounter(game()));

        text = new Text("");
        text.moveTo(new Vector2(20, 38));
        addHud(text);

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
        if (Inputs.touchScreen().justTouched())
        {
            Ray ray = player.forwardRay();
            if (Inputs.touchScreen().exists())
            {
                ray = camera.getPickRay(mouse.x(), mouse.y());
            }
            Optional<EntityBody> body = physicsWorld().rayTrace(ray);
            if (body.exists())
            {
                if (body.unwrap() instanceof RigidBody)
                {
                    RigidBody rigidBody = (RigidBody) body.unwrap();
                    rigidBody.applyCentralImpulse(ray, IMPULSE_SPEED);
                }
            }
        }
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
        counter += dt;
        while (counter >= 0.05f)
        {
            text.setText(player.toString());
            counter = 0f;
        }
    }
}
