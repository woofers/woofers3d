package com.jaxson.lib.gdx.bullet.simulation;

import com.badlogic.gdx.physics.bullet.DebugDrawer;
import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import com.badlogic.gdx.utils.Disposable;
import com.jaxson.lib.gdx.graphics.views.View;

public class BulletDebug implements Disposable
{
    public final static int NO_DEBUG = btIDebugDraw.DebugDrawModes.DBG_NoDebug;
    public final static int WIREFRAME
            = btIDebugDraw.DebugDrawModes.DBG_DrawWireframe;

    public final static int CONSTRAINTS
            = btIDebugDraw.DebugDrawModes.DBG_DrawConstraints;

    public final static int NORMALS
            = btIDebugDraw.DebugDrawModes.DBG_DrawNormals;

    public final static int TEXT = btIDebugDraw.DebugDrawModes.DBG_DrawText;
    public final static int CONTACT_POINTS
            = btIDebugDraw.DebugDrawModes.DBG_DrawContactPoints;

    public final static int FEATURES_TEXT
            = btIDebugDraw.DebugDrawModes.DBG_DrawFeaturesText;

    public final static int MIXED
            = WIREFRAME | FEATURES_TEXT | TEXT | CONTACT_POINTS;

    private DebugDrawer drawer;
    private btCollisionWorld world;

    protected BulletDebug(btCollisionWorld world)
    {
        this.world = world;
    }

    @Override
    public void dispose()
    {
        removeDrawer();
    }

    private boolean isActive()
    {
        return drawer != null;
    }

    public int mode()
    {
        if (!isActive()) return NO_DEBUG;
        return drawer.getDebugMode();
    }

    private void removeDrawer()
    {
        if (!isActive()) return;
        drawer.dispose();
        drawer = null;
    }

    public void render(View view)
    {
        if (!isActive()) return;
        view.modelView().apply();
        view.modelBatch().flush();
        drawer.begin(view.modelView().getCamera());
        world.debugDrawWorld();
        drawer.end();
    }

    public void setMode(int mode)
    {
        if (mode == mode()) return;
        if (mode == NO_DEBUG && !isActive()) return;
        if (!isActive())
        {
            this.drawer = new DebugDrawer();
            world.setDebugDrawer(drawer);
        }
        drawer.setDebugMode(mode);
    }

    public void toggle()
    {
        if (mode() == NO_DEBUG)
        {
            setMode(MIXED);
        }
        else
        {
            setMode(NO_DEBUG);
        }
    }
}
