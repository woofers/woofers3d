package com.jaxson.lib.gdx.bullet.simulation;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.extras.btBulletWorldImporter;
import com.jaxson.lib.gdx.bullet.simulation.bodies.types.RigidBody;
import com.jaxson.lib.gdx.bullet.simulation.collision.types.Shape;
import com.jaxson.lib.gdx.io.GdxFile;
import com.jaxson.lib.util.MyArrayList;

public class WorldImporter
{
    private static class WorldImporterHelper extends btBulletWorldImporter
    {
        private Model model;
        private MyArrayList<RigidBody> entities;

        private WorldImporterHelper(btDynamicsWorld world, Model model)
        {
            super(world);
            this.model = model;
            this.entities = new MyArrayList<>();
        }

        private void add(RigidBody entity)
        {
            entities.add(entity);
        }

        @Override
        public btRigidBody createRigidBody(boolean isDynamic,
                float mass,
                Matrix4 startTransform,
                btCollisionShape shape,
                String bodyName)
        {
            String nodeName = bodyName.split("_", 2)[0] + "_model";
            ModelInstance instance
                    = new ModelInstance(model, nodeName, true, true);
            instance.userData = IMPORTED;
            instance.transform.set(startTransform);

            Shape bodyShape = new Shape(shape);
            Vector3 scale = bodyShape.scale();
            scale.set(scale.x, scale.z, scale.y);
            //instance.nodes.get(0).scale.set(scale);
            //instance.calculateTransforms();

            RigidBody body = new RigidBody(instance, bodyShape, mass);
            add(body);

            // Compensate for Z Up in Blender
            Vector3 location = body.location();
            body.moveTo(Vector3.Zero);
            body.transform().rotate(Vector3.X, -135f);
            location.rotate(Vector3.X, -90f);
            body.moveTo(location);


            return body.body();
        }

        public MyArrayList<RigidBody> entities()
        {
            return entities;
        }
    }

    private static final String WORLD_EXTENSION = "bullet";

    public static final int NOT_IMPORTED = 0;
    public static final int IMPORTED = 1;

    private WorldImporterHelper importer;

    public WorldImporter(GdxFile file, btDynamicsWorld world)
    {
        this(file, file.setExtension(WORLD_EXTENSION), world);
    }

    public WorldImporter(GdxFile model, GdxFile physics, btDynamicsWorld world)
    {
        this.importer = new WorldImporterHelper(world, model.readObject());
        importer.loadFile(physics.getFileHandle());
    }

    public void dispose()
    {
        importer.deleteAllData();
        importer.dispose();
    }

    public MyArrayList<RigidBody> entities()
    {
        return importer.entities();
    }
}
