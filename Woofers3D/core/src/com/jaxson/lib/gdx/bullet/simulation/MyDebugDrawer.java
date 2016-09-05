package com.jaxson.lib.gdx.bullet.simulation;

import com.badlogic.gdx.physics.bullet.DebugDrawer;
import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import com.jaxson.lib.gdx.graphics.views.View;

public class MyDebugDrawer
{
	public final static int NO_DEBUG = btIDebugDraw.DebugDrawModes.DBG_NoDebug;
	public final static int WIREFRAME = btIDebugDraw.DebugDrawModes.DBG_DrawWireframe;
	public final static int CONSTRAINTS = btIDebugDraw.DebugDrawModes.DBG_DrawConstraints;
	public final static int NORMALS = btIDebugDraw.DebugDrawModes.DBG_DrawNormals;
	public final static int TEXT = btIDebugDraw.DebugDrawModes.DBG_DrawText;
	public final static int CONTACT_POINTS = btIDebugDraw.DebugDrawModes.DBG_DrawContactPoints;
	public final static int FEATURES_TEXT = btIDebugDraw.DebugDrawModes.DBG_DrawFeaturesText;
	public final static int MIXED = WIREFRAME | FEATURES_TEXT | TEXT | CONTACT_POINTS;

	private DebugDrawer debugDrawer;
	private btCollisionWorld world;

	public MyDebugDrawer(btCollisionWorld world)
	{
		setWorld(world);
	}

	public void dispose()
	{
		removeDebugDrawer();
	}

	public int getDebugMode()
	{
		if (!hasDebugDrawer()) return NO_DEBUG;
		return debugDrawer.getDebugMode();
	}

	public void render(View view)
	{
		if (!hasDebugDrawer()) return;
		view.getModelView().apply();
		view.getModelBatch().flush();
		debugDrawer.begin(view.getModelView().getCamera());
		world.debugDrawWorld();
		debugDrawer.end();
	}

	public void setDebugMode(int mode)
	{
		if (mode == getDebugMode()) return;
		if (mode == NO_DEBUG && !hasDebugDrawer()) return;
		if (!hasDebugDrawer())
		{
			debugDrawer = new DebugDrawer();
			world.setDebugDrawer(debugDrawer);
		}
		debugDrawer.setDebugMode(mode);
	}

	public void setWorld(btCollisionWorld world)
	{
		this.world = world;
	}

	public void toggleDebugMode()
	{
		if (getDebugMode() == NO_DEBUG)
		{
			setDebugMode(MIXED);
		}
		else
		{
			setDebugMode(NO_DEBUG);
		}
	}

	private boolean hasDebugDrawer()
	{
		return debugDrawer != null;
	}

	private void removeDebugDrawer()
	{
		if (!hasDebugDrawer()) return;
		debugDrawer.dispose();
		debugDrawer = null;
	}
}
