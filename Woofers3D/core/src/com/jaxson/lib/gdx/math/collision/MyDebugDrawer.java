package com.jaxson.lib.gdx.math.collision;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;
import com.badlogic.gdx.physics.bullet.DebugDrawer;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;

public class MyDebugDrawer
{
	public final static int NO_DEBUG    = btIDebugDraw.DebugDrawModes.DBG_NoDebug;
	public final static int WIREFRAME   = btIDebugDraw.DebugDrawModes.DBG_DrawWireframe;
	public final static int CONSTRAINTS = btIDebugDraw.DebugDrawModes.DBG_DrawConstraints;
	public final static int NORMALS     = btIDebugDraw.DebugDrawModes.DBG_DrawNormals;

	private DebugDrawer debugDrawer;
	private btCollisionWorld world;

	public MyDebugDrawer(btCollisionWorld world)
	{
		this.world = world;
	}

	public void dispose()
	{
		removeDebugDrawer();
	}

	public int getDebugMode()
	{
		if (debugDrawer == null) return NO_DEBUG;
		return debugDrawer.getDebugMode();
	}

	private boolean hasDebugDrawer()
	{
		return getDebugMode() != NO_DEBUG;
	}

	private void removeDebugDrawer()
	{
		if (debugDrawer == null) return;
		debugDrawer.dispose();
		debugDrawer = null;
	}

	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch, Camera camera)
	{
		if (modelBatch == null) return;
		if (camera == null) return;
		if (!hasDebugDrawer()) return;
		modelBatch.flush();
		debugDrawer.begin(camera);
		world.debugDrawWorld();
		debugDrawer.end();
	}

	public void setDebugMode(int mode)
	{
		if (hasDebugDrawer())
		{
			if (mode == NO_DEBUG)
			{
				removeDebugDrawer();
				return;
			}
		}
		else
		{
			debugDrawer = new DebugDrawer();
		}
		debugDrawer.setDebugMode(mode);
		world.setDebugDrawer(debugDrawer);
	}

	public void setWorld(btCollisionWorld world)
	{
		this.world = world;
	}
}