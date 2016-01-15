package com.jaxson.lib.gdx.bullet;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.extras.btBulletWorldImporter;

public class WorldImporter extends btBulletWorldImporter
{
	public WorldImporter(btDynamicsWorld world)
	{
		super(world);
	}

	@Override
	public btRigidBody createRigidBody(boolean isDynamic, float mass, Matrix4 startTransform, btCollisionShape shape, String bodyName)
	{
		//instance.transform.set(startTransform);
		return super.createRigidBody(isDynamic, mass, startTransform, shape, bodyName);
	}

	@Override
	public btCollisionObject createCollisionObject(Matrix4 startTransform, btCollisionShape shape, String bodyName)
	{
		return super.createCollisionObject(startTransform, shape, bodyName);
	}
}
