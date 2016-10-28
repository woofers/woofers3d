package com.jaxson.lib.gdx.bullet.simulation.collision.types;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.physics.bullet.collision.btConvexHullShape;
import com.badlogic.gdx.physics.bullet.collision.btShapeHull;
import java.nio.FloatBuffer;

public class ConvexHullShape extends ConvexShape
{
	public ConvexHullShape(FloatBuffer points, int numPoints, int stride)
	{
		this(create(points, numPoints, stride));
	}

	public ConvexHullShape(Model model)
	{
		this(create(model));
	}

	public ConvexHullShape(btConvexHullShape shape)
	{
		super(shape);
	}

	public static btConvexHullShape create(Model model)
	{
		Mesh mesh = model.meshes.get(0);
		return create(mesh.getVerticesBuffer(), mesh.getNumVertices(), mesh.getVertexSize());
	}

	public static btConvexHullShape create(FloatBuffer points, int numPoints, int stride)
	{
		btConvexHullShape shape = new btConvexHullShape(points, numPoints, stride);
		btShapeHull hull = new btShapeHull(shape);
		hull.buildHull(shape.getMargin());
		btConvexHullShape result = new btConvexHullShape(hull);
		shape.dispose();
		hull.dispose();
		return result;
	}
}
