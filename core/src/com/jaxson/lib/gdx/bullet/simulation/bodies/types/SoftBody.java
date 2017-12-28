package com.jaxson.lib.gdx.bullet.simulation.bodies.types;

import java.nio.ShortBuffer;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.softbody.btSoftBody;
import com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Material;
import com.badlogic.gdx.utils.BufferUtils;
import com.jaxson.lib.gdx.bullet.simulation.BulletWorld;

public abstract class SoftBody extends EntityBody<btSoftBody>
{
    private static btSoftBody getBody(Model model, BulletWorld world)
    {
        MeshPart meshPart = model.nodes.get(0).parts.get(0).meshPart;
        meshPart.mesh.scale(0.45f, 0.45f, 0.45f);
        ShortBuffer indexMap = BufferUtils.newShortBuffer(meshPart.size);
        int positionOffset
                = meshPart.mesh.getVertexAttribute(Usage.Position).offset;
        int normalOffset
                = meshPart.mesh.getVertexAttribute(Usage.Normal).offset;
        return new btSoftBody(world.worldInfo(),
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

    private ShortBuffer indexMap;
    private MeshPart meshPart;
    private int positionOffset;

    private int normalOffset;

    public SoftBody(Model model, BulletWorld world)
    {
        this(model, MASS, world);
    }

    public SoftBody(Model model, float mass, BulletWorld world)
    {
        this(new ModelInstance(model), mass, world);
    }

    public SoftBody(ModelInstance modelInstance, float mass, BulletWorld world)
    {
        super(modelInstance, getBody(modelInstance.model, world), mass);
        body().setMass(0, 0);
        meshPart = modelInstance.model.nodes.get(0).parts.get(0).meshPart;
        meshPart.mesh.scale(0.45f, 0.45f, 0.45f);
        indexMap = BufferUtils.newShortBuffer(meshPart.size);
        positionOffset
                = meshPart.mesh.getVertexAttribute(Usage.Position).offset;
        normalOffset = meshPart.mesh.getVertexAttribute(Usage.Normal).offset;
        Material material = body().appendMaterial();
        material.setKLST(0.2f);
        material.setFlags(0);
        body().generateBendingConstraints(2, material);
        body().setConfig_piterations(7);
        body().setConfig_kDF(0.2f);
        body().randomizeConstraints();
        body().setTotalMass(mass);
        body().translate(new Vector3(50f, 14f, 5f).scl(0.5f).scl(0.15f));
        // getBoundingBox();
        // transformToBody();
    }

    public SoftBody(String modelPath, BulletWorld world)
    {
        this(modelPath, MASS, world);
    }

    public SoftBody(String modelPath, float mass, BulletWorld world)
    {
        this(readModel(modelPath), mass, world);
    }

    @Override
    public void dispose()
    {
        super.dispose();
    }

    @Override
    public void update(float dt)
    {
        body().getVertices(
                meshPart.mesh.getVerticesBuffer(),
                meshPart.mesh.getVertexSize(), positionOffset, normalOffset,
                meshPart.mesh.getIndicesBuffer(),
                meshPart.offset, meshPart.size,
                indexMap, 0);
        bodyToTransform();
    }
}
