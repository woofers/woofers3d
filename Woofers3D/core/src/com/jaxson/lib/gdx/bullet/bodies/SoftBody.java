package com.jaxson.lib.gdx.bullet.bodies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.softbody.btSoftBody;
import com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Material;
import com.badlogic.gdx.utils.BufferUtils;
import com.jaxson.lib.gdx.bullet.PhysicsWorld;
import java.nio.ShortBuffer;

public abstract class SoftBody extends EntityBody<btSoftBody>
{
	private ShortBuffer indexMap;
	private MeshPart rootMesh;
	private int positionOffset;
	private int normalOffset;

	public SoftBody(Model model, float mass, PhysicsWorld world)
	{
		super(model, mass);
		rootMesh = model.nodes.get(0).parts.get(0).meshPart;
		rootMesh.mesh.scale(6, 6, 6);
		indexMap = BufferUtils.newShortBuffer(rootMesh.size);
		positionOffset = rootMesh.mesh.getVertexAttribute(Usage.Position).offset;
		normalOffset = rootMesh.mesh.getVertexAttribute(Usage.Normal).offset;
		setBody(new btSoftBody(world.getWorldInfo(), rootMesh.mesh.getVerticesBuffer(), rootMesh.mesh.getVertexSize(),
				positionOffset, normalOffset, rootMesh.mesh.getIndicesBuffer(), rootMesh.offset, rootMesh.size,
				indexMap, 0));
		Material material = getBody().appendMaterial();
		material.setKLST(0.2f);
		material.setFlags(0);
		getBody().setMass(0, 0);
		getBody().generateBendingConstraints(2, material);
		getBody().setConfig_piterations(7);
		getBody().setConfig_kDF(0.2f);
		getBody().randomizeConstraints();
		getBody().setTotalMass(mass);
		getBody().translate(new Vector3(50f, 25f, 5f));
	}

	public SoftBody(Model model, PhysicsWorld world)
	{
		this(model, DEFAULT_MASS, world);
	}

	public SoftBody(String modelPath, float mass, PhysicsWorld world)
	{
		this(new ObjLoader().loadModel(Gdx.files.internal(modelPath)), mass, world);
	}

	public SoftBody(String modelPath, PhysicsWorld world)
	{
		this(modelPath, DEFAULT_MASS, world);
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	@Override
	public void update(float dt)
	{
		getBody().getVertices(rootMesh.mesh.getVerticesBuffer(), rootMesh.mesh.getVertexSize(), positionOffset,
				normalOffset, rootMesh.mesh.getIndicesBuffer(), rootMesh.offset, rootMesh.size, indexMap, 0);
		bodyToTransform();
	}
}
