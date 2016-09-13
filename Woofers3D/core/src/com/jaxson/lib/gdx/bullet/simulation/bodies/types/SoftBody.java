package com.jaxson.lib.gdx.bullet.simulation.bodies.types;

import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.softbody.btSoftBody;
import com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Material;
import com.badlogic.gdx.utils.BufferUtils;
import com.jaxson.lib.gdx.bullet.simulation.PhysicsWorld;
import java.nio.ShortBuffer;

public abstract class SoftBody extends EntityBody<btSoftBody>
{
	private ShortBuffer indexMap;
	private MeshPart meshPart;
	private int positionOffset;
	private int normalOffset;

	public SoftBody(Model model, float mass, PhysicsWorld world)
	{
		super(model, getBody(model, world), mass);
		getBody().setMass(0, 0);
		meshPart = model.nodes.get(0).parts.get(0).meshPart;
		meshPart.mesh.scale(3, 3, 3);
		indexMap = BufferUtils.newShortBuffer(meshPart.size);
		positionOffset =
				meshPart.mesh.getVertexAttribute(Usage.Position).offset;
		normalOffset = meshPart.mesh.getVertexAttribute(Usage.Normal).offset;
		Material material = getBody().appendMaterial();
		material.setKLST(0.2f);
		material.setFlags(0);
		getBody().generateBendingConstraints(2, material);
		getBody().setConfig_piterations(7);
		getBody().setConfig_kDF(0.2f);
		getBody().randomizeConstraints();
		getBody().setTotalMass(mass);
		getBody().translate(new Vector3(50f, 14f, 5f).scl(0.5f));
		// getBoundingBox();
		// transformToBody();
	}

	public SoftBody(Model model, PhysicsWorld world)
	{
		this(model, MASS, world);
	}

	public SoftBody(String modelPath, float mass, PhysicsWorld world)
	{
		this(readModel(modelPath), mass, world);
	}

	public SoftBody(String modelPath, PhysicsWorld world)
	{
		this(modelPath, MASS, world);
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	@Override
	public void update(float dt)
	{
		getBody().getVertices(meshPart.mesh.getVerticesBuffer(), meshPart.mesh
				.getVertexSize(), positionOffset, normalOffset, meshPart.mesh
						.getIndicesBuffer(),
				meshPart.offset, meshPart.size,
				indexMap, 0);
		bodyToTransform();
	}

	private static btSoftBody getBody(Model model, PhysicsWorld world)
	{
		MeshPart meshPart = model.nodes.get(0).parts.get(0).meshPart;
		meshPart.mesh.scale(3, 3, 3);
		ShortBuffer indexMap = BufferUtils.newShortBuffer(meshPart.size);
		int positionOffset =
				meshPart.mesh.getVertexAttribute(Usage.Position).offset;
		int normalOffset =
				meshPart.mesh.getVertexAttribute(Usage.Normal).offset;
		return new btSoftBody(world.getWorldInfo(),
				meshPart.mesh.getVerticesBuffer(),
				meshPart.mesh.getVertexSize(),
				positionOffset,
				normalOffset,
				meshPart.mesh.getIndicesBuffer(),
				meshPart.offset,
				meshPart.size,
				indexMap,
				0);
	}
}
