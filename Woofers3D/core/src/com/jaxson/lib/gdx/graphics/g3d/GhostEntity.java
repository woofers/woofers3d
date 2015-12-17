package com.jaxson.lib.gdx.graphics.g3d;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public abstract class GhostEntity extends Entity
{
	public GhostEntity(String modelPath)
	{
		super(modelPath);
	}

	public GhostEntity(String modelPath, Vector3 location)
	{
		super(modelPath, location);
	}

	public GhostEntity(Model model)
	{
		super(model);
	}

	public GhostEntity(Model model, Vector3 location)
	{
		super(model, location);
	}
}
